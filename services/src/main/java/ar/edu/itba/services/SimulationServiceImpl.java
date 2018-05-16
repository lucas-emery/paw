package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.SimulationService;
import ar.edu.itba.model.*;
import ar.edu.itba.model.utils.MatchStatus;
import ar.edu.itba.model.utils.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimulationServiceImpl implements SimulationService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationServiceImpl.class);

    private Map<Long,SimFixture> table;

    public SimulationServiceImpl(){
        table = new HashMap<>();
    }

    @Override
    public void simulateFixture(Long userId, List<Match> matches) {
        table.put(userId, new SimFixture().simulateFixture(matches));
    }

    @Override
    public Map<Long, MatchStatus> getStatus(Long userId) {
        if(!table.containsKey(userId))
            return null;
        return table.get(userId).getStatus();
    }

    @Override
    public void start(Long userId){
        table.get(userId).start();
    }

    @Override
    public List<Match> getMatches(Long userId) {
        if(!table.containsKey(userId))
            return null;
        List<Match> ret = table.get(userId).getMatches();
        table.remove(userId);
        return ret;
    }

    public enum NodeAtt {
        DEF, PASS, FIN, POSS
    }

    public enum MyTeam {
        HOME, AWAY
    }

    private interface SimulationArc {
        public double getWeight();

        public SimFixture.SimulationNode getNeighbor();
    }

    private class SimFixture {
        private static final int pitchSize = 4;

        private final Object lock = new Object();
        private final Random rand;
        private List<Match> matches;
        private Thread vivaldi;

        private Set<MatchThread> playingMatches;

        private MyTeam otherTeam(MyTeam team) {
            return team.equals(MyTeam.AWAY) ? MyTeam.HOME : MyTeam.AWAY;
        }


        public SimFixture() {
            this.playingMatches = new HashSet<>();
            rand = new Random(System.nanoTime());
        }

        public SimFixture simulateFixture(List<Match> matches) {
            this.matches = matches;
            for (Match match : matches) {
                playingMatches.add(new MatchThread(match));
            }
            return this;
        }

        synchronized public Map<Long, MatchStatus> getStatus() {
            Map<Long, MatchStatus> ret = new HashMap<>();

            for (MatchThread mThread : playingMatches) {
                mThread.persistedMatchStatus.getEvents().addAll(mThread.matchStatus.getEvents());
                ret.put(mThread.match.getId(), mThread.matchStatus.cloneAndFlush().filterEvents());
            }

            return ret;
        }

        public void start() {
            vivaldi = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (MatchThread matchThread : playingMatches) {
                        matchThread.start();
                    }

                    int minute = 0;

                    while (minute < 90) {
                        minute++;

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    }
                }
            });
            vivaldi.start();
        }

        synchronized public List<Match> getMatches() {
            return matches;
        }

        private class MatchThread extends Thread {
            private final Match match;
            private final MatchStatus matchStatus, persistedMatchStatus;

            private MatchThread(Match match) {
                super();
                this.matchStatus = new MatchStatus(0, 0,0, new ArrayList<Event>());
                this.persistedMatchStatus = new MatchStatus(0,0,0,new ArrayList<>());
                this.match = match;
            }

            @Override
            public void run() {
                simulate();
            }

            synchronized private void finish() {
                matchStatus.setMinute(90);
                persistedMatchStatus.setMinute(90);

                for (Event event : persistedMatchStatus.getEvents()) {
                    match.addEvent(event);
                }

                persistedMatchStatus.setHomeScore(matchStatus.getHomeScore());
                persistedMatchStatus.setAwayScore(matchStatus.getAwayScore());

                match.addAwayScore(persistedMatchStatus.getAwayScore());
                match.addHomeScore(persistedMatchStatus.getHomeScore());


                match.finish();

            }

            private void simulate() {
                Formation home = match.getHome().getFormation(), away = match.getAway().getFormation();
                Grid matchGrid = new Grid(home, away);
                SimulationNode currentState = matchGrid.kickOff(MyTeam.HOME), lastState = null;

                int minute = 0;
                while (minute < 90) {
                    if (minute == 45)
                        currentState = matchGrid.kickOff(MyTeam.AWAY);

                    matchStatus.setMinute(minute);

                    currentState = currentState.dispute(matchStatus);

                    if (currentState.equals(lastState)) {
                        currentState = currentState.advance(matchStatus);
                    } else {
                        lastState = currentState;
                    }

                    minute++;

                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                finish();
            }

            public boolean equals(Object o) {
                if (o == null)
                    return false;
                if (o == this)
                    return true;
                if (!o.getClass().equals(MatchThread.class))
                    return false;
                MatchThread aux = (MatchThread) o;
                return aux.match.equals(match);
            }
        }

        private class GridNode {
            private final Point position;
            private SimulationNode home, away;
            private Map<MyTeam, Map<NodeAtt, Integer>> attributes;

            private GridNode(Point position) {
                this.position = position;
                attributes = new HashMap<>();
                attributes.put(MyTeam.HOME, new HashMap<NodeAtt, Integer>());
                attributes.put(MyTeam.AWAY, new HashMap<NodeAtt, Integer>());
                for (NodeAtt nodeAtt : NodeAtt.values()) {
                    attributes.get(MyTeam.AWAY).put(nodeAtt, 0);
                    attributes.get(MyTeam.HOME).put(nodeAtt, 0);
                }
            }

            public SimulationNode getSNode(MyTeam team) {
                return team.equals(MyTeam.AWAY) ? away : home;
            }

            private void setNode(MyTeam team, SimulationNode sNode) {
                if (team.equals(MyTeam.AWAY))
                    away = sNode;
                else
                    home = sNode;
            }

            private void setAtt(MyTeam team, NodeAtt att, int value) {
                attributes.get(team).put(att, value);
            }

            private Integer getAtt(MyTeam team, NodeAtt att) {
                return attributes.get(team).get(att);
            }

            private void accumAtt(MyTeam team, NodeAtt att, int value) {
                setAtt(team, att, getAtt(team, att) + value);
            }

            private void influence(MyTeam team, Player player, int count) {
                Map<Player, Integer> map = (team.equals(MyTeam.AWAY) ? away : home).influenceMap;
                map.put(player, 100 / count);
                accumAtt(team, NodeAtt.DEF, player.getDefending() / count);
                accumAtt(team, NodeAtt.PASS, player.getPassing() / count);
                accumAtt(team, NodeAtt.FIN, player.getPassing() / count);
                accumAtt(team, NodeAtt.POSS, player.getSkillLevel() / count);
            }

            public String toString() {
                return "X: " + position.getX() + "\tY: " + position.getY() + "\t" + attributes.toString();
            }
        }

        private class Grid {
            private GridNode matrix[][];
            private Player homeGK, awayGK;

            public Grid(Formation home, Formation away) {

                for (Map.Entry entry : home.getStarters().entrySet()) {
                    Point point = ((Point) entry.getValue());
                    if (point.getY() == 4 && point.getX() == 0)
                        homeGK = (Player) entry.getKey();
                }

                for (Map.Entry entry : away.getStarters().entrySet()) {
                    Point point = ((Point) entry.getValue());
                    if (point.getY() == 4 && point.getX() == 0)
                        awayGK = (Player) entry.getKey();
                }

                matrix = new GridNode[pitchSize][pitchSize];
                for (int x = 0; x < pitchSize; x++) {
                    for (int y = 0; y < pitchSize; y++) {
                        matrix[x][y] = new GridNode(new Point(x, y));
                        SimulationNode sNodeHome = new SimulationNode(matrix[x][y], MyTeam.HOME, homeGK, this);
                        SimulationNode sNodeAway = new SimulationNode(matrix[x][y], MyTeam.AWAY, awayGK, this);
                        matrix[x][y].setNode(MyTeam.HOME, sNodeHome);
                        matrix[x][y].setNode(MyTeam.AWAY, sNodeAway);
                    }
                }

                setFormation(MyTeam.HOME, home);
                setFormation(MyTeam.AWAY, away);

                configureNeighbors(home.getAttitude(), away.getAttitude());
            }

            private SimulationNode kickOff(MyTeam team) {
                double check = rand.nextDouble();
                if (team.equals(MyTeam.AWAY)) {
                    return check < 0.5 ? matrix[2][1].away : matrix[2][2].away;
                }
                return check < 0.5 ? matrix[1][1].home : matrix[1][2].home;
            }

            private SimulationNode goalKick(MyTeam team) {
                double check = rand.nextDouble();
                if (team.equals(MyTeam.AWAY)) {
                    return check < 0.5 ? matrix[3][1].away : matrix[3][2].away;
                }
                return check < 0.5 ? matrix[0][1].home : matrix[0][2].home;

            }

            private void setFormation(MyTeam team, Formation formation) {
                for (Map.Entry entry : formation.getStarters().entrySet()) {
                    playerInfluence(team, (Point) entry.getValue(), (Player) entry.getKey());
                }
            }

            private void nodeNeighbors(MyTeam team, SimulationNode node, int attitude) {
                int x = node.node.position.getX(), y = node.node.position.getY();
                Collection<SimulationNode> neighbors = new HashSet<>();
                int accum = 1;

                for (int xx = 0; xx < pitchSize; xx++) {
                    for (int yy = 0; yy < pitchSize; yy++) {
                        int auxX;
                        if (team.equals(MyTeam.HOME)) {
                            auxX = xx + 1;
                            if (auxX < pitchSize && auxX > x && !matrix[auxX][yy].home.influenceMap.isEmpty()) {
                                neighbors.add(matrix[auxX][yy].home);
                                accum += node.node.getAtt(team, NodeAtt.PASS) * attitude / Point.manhattanSq(new Point(x, y), new Point(auxX, yy));
                            }
                        } else {
                            auxX = pitchSize - xx - 1;
                            if (auxX >= 0 && auxX < x && !matrix[auxX][yy].away.influenceMap.isEmpty()) {
                                neighbors.add(matrix[auxX][yy].away);
                                accum += node.node.getAtt(team, NodeAtt.PASS) * attitude / Point.manhattanSq(new Point(x, y), new Point(auxX, yy));
                            }
                        }
                    }
                }

                for (final SimulationNode sNode : neighbors) {
                    double distance = Point.manhattanSq(sNode.node.position, new Point(x, y));
                    int passing = matrix[x][y].getAtt(team, NodeAtt.PASS);
                    final double normalizedWeight = passing * 100 * attitude / (accum * distance);
                    node.neighbors.add(new SimulationArc() {
                        @Override
                        public double getWeight() {
                            return normalizedWeight;
                        }

                        @Override
                        public SimulationNode getNeighbor() {
                            return sNode;
                        }
                    });
                }

            }

            public void configureNeighbors(int homeAttitude, int awayAttitude) {
                for (int x = 0; x < pitchSize; x++) {
                    for (int y = 0; y < pitchSize; y++) {
                        nodeNeighbors(MyTeam.HOME, matrix[x][y].home, homeAttitude);
                        nodeNeighbors(MyTeam.AWAY, matrix[x][y].away, awayAttitude);
                    }
                }
            }

            private void playerInfluence(MyTeam team, Point position, Player player) {
                int x = position.getX(), y = position.getY(), count = 0;

                if (team == MyTeam.AWAY) {
                    x = pitchSize * 2 - x;
                    y = pitchSize * 2 - y;
                }

                if (x == 0 || x == 8 || y == 0 || y == 8)
                    return;

                boolean splitX = (x % 2 == 0), splitY = (y % 2 == 0);
                Collection<GridNode> nodesToInfluence = new HashSet<>();

                x = x / 2;
                y = y / 2;

                if (splitX) {
                    if (splitY) {
                        nodesToInfluence.add(matrix[x - 1][y]);
                        nodesToInfluence.add(matrix[x][y - 1]);
                        nodesToInfluence.add(matrix[x - 1][y - 1]);
                        nodesToInfluence.add(matrix[x][y]);
                        count = 4;
                    } else {
                        nodesToInfluence.add(matrix[x - 1][y]);
                        nodesToInfluence.add(matrix[x][y]);
                        count = 2;
                    }
                } else {
                    if (splitY) {
                        nodesToInfluence.add(matrix[x][y - 1]);
                        nodesToInfluence.add(matrix[x][y]);
                        count = 2;
                    } else {
                        nodesToInfluence.add(matrix[x][y]);
                        count = 1;
                    }
                }

                for (GridNode node : nodesToInfluence) {
                    node.influence(team, player, count);
                }

            }
        }

        private class SimulationNode {
            private final Player opGK;
            private final Grid grid;
            private final GridNode node;
            private final MyTeam possession;
            private Map<Player, Integer> influenceMap;
            private final Collection<SimulationArc> neighbors;

            private SimulationNode(GridNode node, MyTeam possession, Player opGK, Grid grid) {
                this.opGK = opGK;
                this.node = node;
                this.possession = possession;
                this.grid = grid;
                this.influenceMap = new HashMap<>();
                this.neighbors = new HashSet<>();
            }

            synchronized private SimulationNode dispute(MatchStatus matchStatus) {
                int opponentDef = node.getAtt(otherTeam(possession), NodeAtt.DEF);
                int myPoss = node.getAtt(possession, NodeAtt.POSS);

                int sum = myPoss + opponentDef + 1;
                double nMyPoss = (double) myPoss / sum;

                boolean taken = rand.nextDouble() < nMyPoss;

                if (taken) {
                    matchStatus.getEvents().add(new Event(0, node.getSNode(otherTeam(possession)).whoDidIt(), Event.Type.TACKLE, matchStatus.getMinute()));
                }

                return node.getSNode(taken ? otherTeam(possession) : possession);
            }

            synchronized private SimulationNode shot(MatchStatus matchStatus) {
                double check = rand.nextDouble();
                int shot = node.getAtt(possession, NodeAtt.FIN) / (distanceToGoal() + 1);

                int sum = shot + opGK.getGoalKeeping() + node.getAtt(otherTeam(possession), NodeAtt.DEF);
                double norm = (double) shot / sum;
                boolean goal = check < norm;

                if (goal) {

                    if (possession.equals(MyTeam.AWAY))
                        matchStatus.setAwayScore(matchStatus.getAwayScore() + 1);
                    else
                        matchStatus.setHomeScore(matchStatus.getHomeScore() + 1);

                    matchStatus.getEvents().add(new Event(0, whoDidIt(), Event.Type.SCORE, matchStatus.getMinute()));
                    return grid.kickOff(otherTeam(possession));
                }

                matchStatus.getEvents().add(new Event(0, opGK, Event.Type.SAVE, matchStatus.getMinute()));
                return grid.goalKick(otherTeam(possession));
            }

            private int distanceToGoal() {
                return Point.manhattanSq(node.position, possession.equals(MyTeam.AWAY) ? new Point(0, 2) : new Point(3, 2));
            }

            synchronized private SimulationNode advance(MatchStatus matchStatus) {
                double chanceToShoot = distanceToGoal() == 0 ? 1.0 : 1 / (distanceToGoal() * 2);
                boolean shooting = rand.nextDouble() < chanceToShoot;

                if (distanceToGoal() < 2 || shooting)
                    return shot(matchStatus);

                double random = rand.nextDouble();
                double accum = 0;
                SimulationNode last = null;
                for (SimulationArc arc : neighbors) {
                    accum += arc.getWeight();
                    if (random < accum)
                        return arc.getNeighbor();
                    last = arc.getNeighbor();
                }

                matchStatus.getEvents().add(new Event(0, whoDidIt(), Event.Type.PASS, matchStatus.getMinute()));

                return last;
            }

            private Player whoDidIt() {
                double action = rand.nextDouble();
                int accum = 0;
                Player guilty = null;

                for (Map.Entry entry : influenceMap.entrySet()) {
                    accum += (int) entry.getValue();
                    if (action < accum) {
                        guilty = (Player) entry.getKey();
                    }
                }
                return guilty;
            }

            public String toString() {
                return node.toString() + "\t" + possession;
            }

        }

    }
}
