package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.PlayerDao;
import ar.edu.itba.interfaces.dao.ReceiptDao;
import ar.edu.itba.interfaces.dao.TeamDao;
import ar.edu.itba.interfaces.service.AiService;
import ar.edu.itba.interfaces.service.TeamService;
import ar.edu.itba.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private AiService aiService;
    @Autowired
    private ReceiptDao receiptDao;

    @Override
    public Team create(String name, League league, Stadium stadium, Formation formation, List<Player> players, List<Player> youthAcademy, int fanCount, int fanTrust, int boardTrust, int money) {
        return teamDao.create(name, league, stadium, formation, players, youthAcademy, fanCount, fanTrust, boardTrust, money);
    }

    @Override
    public Team findById(long id) {
        return teamDao.findById(id);
    }

    @Override
    public List<Team> findByLeague(League league) {
        return teamDao.findAllByLeagueId(league.getId());
    }

    @Override
    public int getSalaries(Team team) {
        int salaries = 0;

        for (Player player: team.getPlayers()) {
            salaries += player.getSalary();
        }

        return salaries;
    }

    @Override
    public int getTicketsSold(Team team) {

        int ticketsSold = 0;

        if(team.getFinance() != null) {
            for (Receipt receipt : team.getFinance()) {

                if (receipt.getType() == Receipt.Type.MATCHINCOME)
                    ticketsSold += receipt.getAmount();
            }
        }

        return ticketsSold;
    }

    @Override
    public List<Team> findByLeagueAndFetchPlayers(League league){
        List<Team> teams = findByLeague(league);
        for(Team team : teams){
            setPlayers(team);
        }
        return teams;
    }

    @Override
    public Team findByUserId(long id) {
        return teamDao.findByUserId(id);
    }

    @Override
    public Team findByUserIdAndFetchPlayers(long id) {
        Team team = findByUserId(id);
        setPlayers(team);
        return team;
    }

    @Override
    public Team findByUserIdAndFetchPlayersAndFormation(long id) {
        Team team = findByUserId(id);
        setPlayers(team);
        if(team.getFormation() == null){
            team.setFormation(aiService.getFormation(team.getPlayers()));
        }
        setFormation(team);
        return team;
    }

    @Override
    public Team findByUserIdAndFetchPlayersAndFinance(long id) {
        Team team = findByUserId(id);
        setPlayers(team);
        setFinance(team);
        return team;
    }

    @Override
    public Team findByIdAndFetchPlayersAndFinance(long id) {
        Team team = findById(id);
        setPlayers(team);
        setFinance(team);
        return team;
    }

    @Override
    public boolean isPlayerForSale(Team team, Player player) {
        team = findByIdAndFetchPlayersAndFinance(team.getId());
        if(team.getPlayers().size() < 21)
            return false;
        return true;
    }

    @Override
    public void setPlayers(Team team) {
        if(team != null){
            team.getPlayers().size();
        }
    }

    @Override
    public void setFinance(Team team) {
        if(team != null){
            team.getFinance().size();
        }
    }

    @Override
    public void setFormation(Team team) {
        Formation formation = team.getFormation();
        if(formation != null) {
            formation.getStarters().size();
            formation.getSubstitutes().size();
        }
    }


}
