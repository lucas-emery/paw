package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.*;
import ar.edu.itba.model.Formation;
import ar.edu.itba.model.League;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.Team;
import ar.edu.itba.model.utils.Point;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AiServiceImpl implements AiService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private FormationService formationService;

    @Override
    public Formation getFormation(List<Player> players) {

        Map<Player,Point> starters = new HashMap<>();
        List<Player> tired = new ArrayList<>();
        Player captain, fkTaker, penaltyTaker;

        for(Player player : players){
            if(player.getFitness() < 65)
                tired.add(player);
        }

        players.removeAll(tired);

        tired.sort(Comparator.comparingInt(Player::getFitness));

        players.sort(Comparator.comparingInt(Player::getAge));
        captain = useLast(players,false);

        players.sort(Comparator.comparingInt(Player::getFinishing));
        penaltyTaker = useLast(players, false);

        players.sort(Comparator.comparingInt(Player::getPassing));
        fkTaker = useLast(players, false);

        players.sort(Comparator.comparingInt(Player::getGoalKeeping));
        starters.put(useLast(players, true), new Point(0,4));

        players.sort(Comparator.comparingInt(Player::getDefending));
        for (int i = 0; i < 4; i++) {
            starters.put(useLast(players, true), new Point(1, 1 + 2*i));
        }

        players.sort(Comparator.comparingInt(Player::getPassing));
        starters.put(useLast(players,true), new Point(5, 4));
        starters.put(useLast(players,true), new Point(4, 1));
        starters.put(useLast(players,true), new Point(4, 7));
        starters.put(useLast(players,true), new Point(3, 4));

        players.sort(Comparator.comparingInt(Player::getFinishing));
        starters.put(useLast(players,true), new Point(7,3));
        starters.put(useLast(players,true), new Point(7, 5));

        return formationService.create(starters, tired,50,50, captain,fkTaker,penaltyTaker);
    }

    @Override
    public void handleLeagueTransfers(League league) {
        List<Team> teams = teamService.findByLeagueAndFetchPlayers(league);
        for(Team team : teams){
            handleTeamTransfers(team, teams);
        }
    }

    private void handleTeamTransfers(Team team, List<Team> teams){
        int avgLevel = overall(team);
        int overalls[] = new int[Player.Position.values().length];
        int i = 0, worst = Integer.MAX_VALUE, worstIndex = 0;
        for(Player.Position position : Player.Position.values()){
            overalls[i] = overallInPosition(team, position);
            if(overalls[i] < worst){
                worst = overalls[i];
                worstIndex = i;
            }
        }

        Player.Position toChange = Player.Position.values()[worstIndex];
        Player bestPlayer = bestPlayerForSale(teams, toChange, team.getMoney(), worst);
        if(bestPlayer != null)
            transferService.transferPlayer(team, bestPlayer.getTeam(), bestPlayer);
    }

    private Player bestPlayerForSale(List<Team> teams, Player.Position position, int money, int avgInPosition){
        List<Player> playersInPosition = new ArrayList<>();
        for(Team team : teams){
            playersInPosition.addAll(team.getPlayers().stream().filter(player -> player.getPosition() == position && teamService.isPlayerForSale(team,player)).collect(Collectors.toList()));
        }
        Player best = null;
        float bestFactor = 0;
        for(Player player : playersInPosition){
            float factor = playerFactor(player, position, money, avgInPosition);
            if(factor > bestFactor){
                best = player;
                bestFactor = factor;
            }
        }
        return best;
    }

    private float playerFactor(Player player, Player.Position position, int moneyAvailable, int avgInPosition){
        if(player.getValue() > moneyAvailable/2 || moneyAvailable - player.getValue() < 1e4)
            return 0;

        int attrInPos = 0;
        switch (position){
            case GK: attrInPos = player.getGoalKeeping();
            break;
            case DEF: attrInPos = player.getDefending();
            break;
            case MID: attrInPos = player.getPassing();
            break;
            case FW: attrInPos = player.getFinishing();
        }

        if(attrInPos < avgInPosition)
            return 0;

        float ret = 1;

        ret *= player.getPotential();
        ret /= player.getAge()*player.getAge();
        ret *= (avgInPosition - attrInPos);
        ret /= player.getValue();
        ret *= (moneyAvailable - player.getValue());
        return ret;
    }

    private int overall(Team team){
        int accum = 0;
        for(Player.Position position : Player.Position.values()){
            accum += overallInPosition(team, position);
        }
        return accum/Player.Position.values().length;
    }

    private int overallInPosition(Team team, Player.Position field){
        List<Player> players = team.getPlayers();
        int accum = 0, count = 0;

        for(Player player : players){
            if(player.getPosition() == field) {
                count++;
                switch (field) {
                    case GK:
                        accum += player.getGoalKeeping();
                        break;
                    case DEF:
                        accum += player.getDefending();
                        break;
                    case MID:
                        accum += player.getPassing();
                        break;
                    case FW:
                        accum += player.getFinishing();
                }
            }
        }

        return accum/count;
    }

    private Player useLast(List<Player> list, boolean remove){
        Player ret = list.get(list.size() - 1);
        if(remove)
            list.remove(list.size() - 1);
        return ret;
    }
}
