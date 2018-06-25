package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.PlayerDao;
import ar.edu.itba.interfaces.dao.TeamDao;
import ar.edu.itba.interfaces.service.*;
import ar.edu.itba.model.DTOs.PlayerDTO;
import ar.edu.itba.model.League;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.Team;
import ar.edu.itba.model.User;
import ar.edu.itba.model.utils.PlayerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    @Autowired
    private EconomyService economyService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private PlayerDao playerDao;

    @Override
    public void performTransfer(User user, String transfer) {
        long playerId = Long.valueOf(transfer.split("=")[0]);
        Player player = playerService.findById(playerId);
        transferPlayer(player.getTeam(), user.getTeam(), player);

    }

    @Override
    public void transferPlayer(Team from, Team to, Player player) {
        from = teamService.findByIdAndFetchPlayersAndFinance(from.getId());
        to = teamService.findByIdAndFetchPlayersAndFinance(to.getId());
        player.setTeam(to);
        from.getPlayers().remove(player);
        to.getPlayers().add(player);
        playerDao.save(player);
        economyService.submitTransfer(from, to, player.getValue());
        teamDao.save(from);
        teamDao.save(to);
    }

    @Override
    public List<PlayerDTO> playersByCriteria(User user, Predicate<Player> criteria) {
        List<League> leagues = leagueService.findByUser(user);
        List<Player> players = new ArrayList<>();
        Team userTeam = teamService.findByUserId(user.getId());
        for(League league : leagues){
            List<Team> teams = teamService.findByLeagueAndFetchPlayers(league);
            for(Team team : teams){
                if(!team.equals(userTeam))
                    players.addAll(team.getPlayers().stream().filter(criteria).collect(Collectors.toList()));
            }
        }

        List<PlayerDTO> ret = new ArrayList<>();
        for(Player player : players){
            ret.add(new PlayerDTO(player));
        }

        return ret;
    }

    @Override
    public Predicate<Player> criteria(String filters) {
        Predicate ret = o -> true;
        String array[] = filters.split("&");
        for (int i = 0; i < array.length; i+=2) {
            if(!array[i].split("=")[1].equals("Any")){
                PlayerFilter playerFilter = new PlayerFilter(array[i].split("=")[1], array[i+1].split("=")[1]);
                ret = ret.and(playerFilter.toPredicate());
            }
        }
        return ret;
    }
}
