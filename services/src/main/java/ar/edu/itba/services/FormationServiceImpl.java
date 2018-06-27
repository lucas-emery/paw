package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.FormationDao;
import ar.edu.itba.interfaces.service.FormationService;
import ar.edu.itba.model.Formation;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.utils.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class FormationServiceImpl implements FormationService {

    @Autowired
    private FormationDao formationDao;

    @Override
    @Transactional
    public Formation create(Map<Player, Point> starters, List<Player> substitutes, int pressure, int attitude, Player captain, Player freeKickTaker, Player penaltyTaker) {
        return formationDao.create(starters, substitutes, pressure, attitude, captain, freeKickTaker, penaltyTaker);
    }

    @Override
    public Formation findById(long id) {
        return formationDao.findById(id);
    }

    @Override
    public Formation save(Formation formation) {
        return formationDao.save(formation);
    }

    @Override
    public boolean isValid(Formation formation) {
        if(formation.getStarters().size() != 11)
            return false;
        if(formation.getSubstitutes().size() != 7)
            return false;
        for(Map.Entry<Player, Point> entry : formation.getStarters().entrySet()){
            for(Player player : formation.getSubstitutes()){
                if(player.equals(entry.getKey()))
                    return false;
            }
        }
        for(Player s : formation.getSubstitutes()){
            int found = 0;
            for(Player aux : formation.getSubstitutes()){
                if(s.equals(aux))
                    found++;
                if(found > 1)
                    return false;
            }
        }
        Set<Player> starters = formation.getStarters().keySet();
        if(!starters.contains(formation.getCaptain()))
            return false;
        if(!starters.contains(formation.getFreeKickTaker()))
            return false;
        if(!starters.contains(formation.getPenaltyTaker()))
            return false;

        return formation.getCaptain() != null && formation.getFreeKickTaker() != null && formation.getPenaltyTaker() != null;
    }

    @Override
    public Map<Player, Point> createStarters(Player gk, Player lb, Player lcb, Player cb, Player rcb, Player rb, Player cdm, Player lm, Player lcm, Player rcm, Player rm, Player cam, Player lw, Player lf, Player st, Player rf, Player rw){
        Map<Player, Point> positions = new HashMap<>();
        if(gk != null)
            positions.put(gk, new Point(0,4));
        if(lb != null)
            positions.put(lb, new Point(1, 7));
        if(lcb != null)
            positions.put(lcb, new Point(1, 5));
        if(cb != null)
            positions.put(cb, new Point(1, 4));
        if(rcb != null)
            positions.put(rcb, new Point(1, 3));
        if(rb != null)
            positions.put(rb, new Point(1, 1));
        if(cdm != null)
            positions.put(cdm, new Point(3, 4));
        if(lm != null)
            positions.put(lm, new Point(4, 7));
        if(lcm != null)
            positions.put(lcm, new Point(4, 5));
        if(rcm != null)
            positions.put(rcm, new Point(4, 3));
        if(rm != null)
            positions.put(rm, new Point(4, 1));
        if(cam != null)
            positions.put(cam, new Point(5, 4));
        if(lw != null)
            positions.put(lw, new Point(7, 7));
        if(lf != null)
            positions.put(lf, new Point(7, 5));
        if(st != null)
            positions.put(st, new Point(7, 4));
        if(rf != null)
            positions.put(rf, new Point(7, 3));
        if(rw != null)
            positions.put(rw, new Point(7, 1));

        return positions;
    }

    @Override
    public List<Player> createSubstitutes(Player sub1, Player sub2, Player sub3, Player sub4, Player sub5, Player sub6, Player sub7){
        List<Player> substitutes = new ArrayList<>();
        substitutes.add(sub1);
        substitutes.add(sub2);
        substitutes.add(sub3);
        substitutes.add(sub4);
        substitutes.add(sub5);
        substitutes.add(sub6);
        substitutes.add(sub7);

        return substitutes;
    }
}
