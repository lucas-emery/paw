package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.AiService;
import ar.edu.itba.interfaces.service.FormationService;
import ar.edu.itba.model.Formation;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.utils.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiServiceImpl implements AiService {

    @Autowired
    private FormationService formationService;

    @Override
    public Formation getFormation(List<Player> players) {

        if(players.size() < 18)
            return null;

        Map<Player,Point> starters = new HashMap<>();
        List<Player> tired = new ArrayList<>();
        Player captain, fkTaker, penaltyTaker;

        for(Player player : players){
            if(player.getFitness() < 65)
                tired.add(player);
        }

        players.removeAll(tired);


        if(players.size() < 11){
            tired.sort((player, t1) -> t1.getFitness() - player.getFitness());
            players.addAll(tired.subList(0, 11 - players.size()));
        }

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
        starters.put(useLast(players,true), new Point(4, 3));
        starters.put(useLast(players,true), new Point(4, 1));
        starters.put(useLast(players,true), new Point(4, 7));
        starters.put(useLast(players,true), new Point(4, 5));

        players.sort(Comparator.comparingInt(Player::getFinishing));
        starters.put(useLast(players,true), new Point(7,3));
        starters.put(useLast(players,true), new Point(7, 5));

        players.addAll(tired);

        players.sort((player, t1) -> t1.getFitness() - player.getFitness());

        return formationService.create(starters, players.subList(0, 7),0,0, captain,fkTaker,penaltyTaker);
    }

    private Player useLast(List<Player> list, boolean remove){
        Player ret = list.get(list.size() - 1);
        if(remove)
            list.remove(list.size() - 1);
        return ret;
    }
}
