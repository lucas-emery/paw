package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.LeagueDao;
import ar.edu.itba.interfaces.dao.MatchDao;
import ar.edu.itba.interfaces.dao.TeamDao;
import ar.edu.itba.interfaces.service.LeagueService;
import ar.edu.itba.interfaces.service.MatchService;
import ar.edu.itba.model.League;
import ar.edu.itba.model.Match;
import ar.edu.itba.model.Team;
import ar.edu.itba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LeagueServiceImpl implements LeagueService {


    @Autowired
    private MatchService matchService;

    @Autowired
    private LeagueDao leagueDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private TeamDao teamDao;


    @Override
    public List<League> findByUser(User user) {
        List<League> leagues = leagueDao.findAllByUserId(user.getId());

        return leagues;
    }

    @Override
    public void fillFixture(User user, League league){
        Map<Date,List<Match>> fixture = new HashMap<>();

        List<Match> toPlay = matchDao.findByLeagueIdAndFromDate(league.getId(),user.getCurrentDay());

        for(Match match : toPlay){
            if(!fixture.containsKey(match.getDay())){
                fixture.put(match.getDay(), new ArrayList<>());
            }
            fixture.get(match.getDay()).add(match);
        }

        league.setFixture(fixture);
    }

    @Override
    public Map<Team, Integer> getTeamPoints(League league, Date currentDate) {

        Map<Team, Integer> map = new HashMap<>();

        List<Match> matches = matchDao.findByLeagueIdAndBeforeDate(league.getId(), currentDate);

        matchService.setTeams(matches);



        for (Team team : teamDao.findAllByLeagueId(league.getId())) {
            map.put(team, 0);
        }

        if(!matches.isEmpty()) {

            for (Match match : matches) {

                Team home = match.getHome();
                Team away = match.getAway();

                Integer homePoints = map.get(home);
                Integer awayPoints = map.get(away);

                if(homePoints == null)
                    homePoints = 0;

                if(awayPoints == null)
                    awayPoints = 0;

                homePoints += match.getHomePoints();
                awayPoints += match.getAwayPoints();

                map.put(home, homePoints);
                map.put(away, awayPoints);
            }

        }

        return map;
    }

    @Override
    public Map<String, Integer> getTeamPointsName(League league, Date currentDate) {

        Map<String, Integer> map = new HashMap<>();

        List<Match> matches = matchDao.findByLeagueIdAndBeforeDate(league.getId(), currentDate);

        matchService.setTeams(matches);



        for (Team team : teamDao.findAllByLeagueId(league.getId())) {
            map.put(team.getName(), 0);
        }

        if(!matches.isEmpty()) {

            for (Match match : matches) {

                Team home = match.getHome();
                Team away = match.getAway();

                Integer homePoints = map.get(home);
                Integer awayPoints = map.get(away);

                if(homePoints == null)
                    homePoints = 0;

                if(awayPoints == null)
                    awayPoints = 0;

                homePoints += match.getHomePoints();
                awayPoints += match.getAwayPoints();

                map.put(home.getName(), homePoints);
                map.put(away.getName(), awayPoints);
            }

        }

        return map;
    }

    @Override
    public List<Match> findMatchesForDate(League league, Date date) {
        if(league != null) {
            List<Match> matches =  matchDao.findByLeagueIdAndDate(league.getId(), date);
            matchService.setTeamsAndFormations(matches);
            return matches;
        }
        return null;
    }
}
