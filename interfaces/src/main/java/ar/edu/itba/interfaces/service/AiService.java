package ar.edu.itba.interfaces.service;

import ar.edu.itba.model.Formation;
import ar.edu.itba.model.League;
import ar.edu.itba.model.Player;

import java.util.List;

public interface AiService {
    Formation getFormation(List<Player> players);

    void handleLeagueTransfers(League league);
}
