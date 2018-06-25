package ar.edu.itba.interfaces.service;

import ar.edu.itba.model.*;

import java.util.List;

public interface TeamService {

    Team create(String name, League league, Stadium stadium, Formation formation, List<Player> players, List<Player> youthAcademy, int fanCount, int fanTrust, int boardTrust, int money);

    Team findById(long id);

    List<Team> findByLeague(League league);

    List<Team> findByLeagueAndFetchPlayers(League league);

    int getSalaries(Team team);

    int getTicketsSold(Team team);

    Team findByUserId(long id);

    Team findByUserIdAndFetchPlayers(long id);

    Team findByUserIdAndFetchPlayersAndFinance(long id);

    Team findByIdAndFetchPlayersAndFinance(long id);

    Team findByUserIdAndFetchPlayersAndFormation(long id);

    boolean isPlayerForSale(Team team, Player player);

    void setPlayers(Team team);

    void setFinance(Team team);

    void setFormation(Team team);

}
