package ar.edu.itba.model;

import ar.edu.itba.model.utils.Point;

import java.util.*;

public class Formation {

    public static final Collection<Point> availablePositions = new HashSet<>();

    public static void init(){
        //availablePositions.add(new Point());
    }

    public enum PlaysAs {
        STARTER, SUBSTITUTE
    }

    private long id, captainId, freeKickTakerId, penaltyTakerId;
    private Player captain, freeKickTaker, penaltyTaker;
    private Map<Player, Point> starters;
    private Map<Long, Point> startersIds;
    private List<Player> substitutes;
    private List<Long> substitutesIds;
    private int pressure, attitude;

    public Formation(long id, Player captain, Player freeKickTaker, Player penaltyTaker, Map<Player, Point> starters, List<Player> substitutes, int pressure, int attitude) {
        this.id = id;
        this.captain = captain;
        this.freeKickTaker = freeKickTaker;
        this.penaltyTaker = penaltyTaker;
        this.starters = starters;
        this.substitutes = substitutes;
        this.pressure = pressure;
        this.attitude = attitude;
    }

    public Formation(long id, long captainId, long freeKickTakerId, long penaltyTakerId, Map<Long, Point> startersIds, List<Long> substitutesIds, int pressure, int attitude) {
        this.id = id;
        this.captainId = captainId;
        this.freeKickTakerId = freeKickTakerId;
        this.penaltyTakerId = penaltyTakerId;
        this.startersIds = startersIds;
        this.substitutesIds = substitutesIds;
        this.pressure = pressure;
        this.attitude = attitude;
    }

    public long getCaptainId() {
        if(captain != null)
            return captain.getId();
        return captainId;
    }

    public long getFreeKickTakerId() {
        if(freeKickTaker != null)
            return freeKickTaker.getId();
        return freeKickTakerId;
    }

    public long getPenaltyTakerId() {
        if(penaltyTaker != null)
            return penaltyTaker.getId();
        return penaltyTakerId;
    }

    public Map<Long, Point> getStartersIds() {
        if(starters != null) {
            Map<Long, Point> ids = new HashMap<>();
            for (Map.Entry<Player, Point> entry : starters.entrySet()) {
                ids.put(entry.getKey().getId(), entry.getValue());
            }
            return ids;
        }
        return startersIds;
    }

    public List<Long> getSubstitutesIds() {
        if(substitutes != null){
            List<Long> ids = new LinkedList<>();
            for (Player p : substitutes)
                ids.add(p.getId());
            return ids;
        }
        return substitutesIds;
    }

    public void movePlayer(final Player p1, final Point position){
        if(availablePositions.contains(position))
            starters.put(p1,position);
    }

    public void replacePlayer(final Player p1, final Player p2){
        Point position = starters.get(p1);
        starters.remove(p1);
        starters.put(p2,position);
    }

    //Solo sirve para 442 por ahora
    public String getPlayerPosition(long playerId) {

        if(startersIds.containsKey(playerId)) {
            switch (startersIds.get(playerId).getY()) {
                case 0://Arquero
                    return "GK";
                case 1://Defensor
                    switch (startersIds.get(playerId).getX()) {
                        case 0:
                            return "LB";
                        case 1:
                            return "LCB";
                        case 2:
                            return "RCB";
                        case 3:
                            return "RB";
                    }
                case 2://Volante
                    switch (startersIds.get(playerId).getX()) {
                        case 0:
                            return "LM";
                        case 1:
                            return "LCM";
                        case 2:
                            return "RCM";
                        case 3:
                            return "RM";
                    }
                case 3://Delantero
                    switch (startersIds.get(playerId).getX()) {
                        case 0:
                            return "LF";
                        case 1:
                            return "RF";
                    }
            }
        }
        else if(substitutesIds.contains(playerId)) {
            return "SUB";
        }
        return "RES";
    }

    public Map<Player, Point> getStarters() {
        return starters;
    }

    public List<Player> getSubstitutes() {
        return substitutes;
    }

    public void setSubstitutes(List<Player> substitutes) {
        this.substitutes = substitutes;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getAttitude() {
        return attitude;
    }

    public void setAttitude(int attitude) {
        this.attitude = attitude;
    }

    public Player getCaptain() {
        return captain;
    }

    public void setCaptain(Player captain) {
        this.captain = captain;
    }

    public Player getFreeKickTaker() {
        return freeKickTaker;
    }

    public void setFreeKickTaker(Player freeKickTaker) {
        this.freeKickTaker = freeKickTaker;
    }

    public Player getPenaltyTaker() {
        return penaltyTaker;
    }

    public void setPenaltyTaker(Player penaltyTaker) {
        this.penaltyTaker = penaltyTaker;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
