package ar.edu.itba.interfaces.service;

import ar.edu.itba.model.Formation;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.utils.Point;

import java.util.List;
import java.util.Map;

public interface FormationService {

    Formation create(Map<Player, Point> starters, List<Player> substitutes, int pressure, int attitude, Player captain, Player freeKickTaker, Player penaltyTaker);

    Formation findById(long id);

    Formation save(Formation formation);

    boolean isValid(Formation formation);

    Map<Player, Point> createStarters(Player gk, Player lb, Player lcb, Player cb, Player rcb, Player rb, Player cdm, Player lm, Player lcm, Player rcm, Player rm, Player cam, Player lw, Player lf, Player st, Player rf, Player rw);

    List<Player> createSubstitutes(Player sub1, Player sub2, Player sub3, Player sub4, Player sub5, Player sub6, Player sub7);

    }
