package ar.edu.itba.interfaces.dao;

import ar.edu.itba.model.Formation;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.utils.Point;

import java.util.List;
import java.util.Map;

public interface FormationDao {

    Formation create(Map<Player, Point> starters, List<Player> substitutes, int pressure, int attitude, Player captain, Player freeKickTaker, Player penaltyTaker);
    Formation create(Map<Long, Point> starters, List<Long> substitutes, int pressure, int attitude, long captain, long freeKickTaker, long penaltyTaker);

    Formation save(Formation formation);

    Formation findById(long id);

}
