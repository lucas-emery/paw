package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.*;
import ar.edu.itba.interfaces.service.FixtureService;
import ar.edu.itba.interfaces.service.LeagueService;
import ar.edu.itba.interfaces.service.UserService;
import ar.edu.itba.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LeagueDao leagueDao;
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private ReceiptDao receiptDao;
    @Autowired
    private TeamDao teamDao;
    @Autowired
    private LeagueService leagueService;
    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User create(String username, String password, String mail, Date currentDay) {
        User us = userDao.create(username, passwordEncoder.encode(password), mail, currentDay);
        fillFixture(leagueDao.findById(us.getId()), currentDay);
        return us;
    }

    @Override
    public void setTeam(User user, Team team) {
        user.setTeam(team);

        userDao.save(user);
    }

    @Override
    public String getCurrentDay(User user) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM YYY");

        return format.format(user.getCurrentDay());
    }


    @Override
    public void advanceDate(User user) {
        Calendar cal = Calendar.getInstance();
        Date currentDate = user.getCurrentDay();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, 7);
        Date newDate = cal.getTime();
        user.setCurrentDay(newDate);
        Team team = teamDao.findByUserId(user.getId());
        if(newDate.getMonth() > currentDate.getMonth()) {
            subtractPlayerSalaries(team);
        }
        if(matchDao.findByLeagueIdAndFromDate(team.getLeagueId(), newDate).isEmpty()) {
            League league = leagueDao.findById(team.getLeagueId());
            int higherPoints = -1;
            Team higherTeam = null;
            for (Map.Entry<Team, Integer> entry : leagueService.getTeamPoints(league, currentDate).entrySet())
            {
                if(entry.getValue() > higherPoints) {
                    higherPoints = entry.getValue();
                    higherTeam = entry.getKey();
                }
            }
            int amount = league.getPrize();
            if(higherTeam != null) {
                if (higherTeam.getId() == team.getId()) {
                    receiptDao.create(team, amount, Receipt.Type.TOURNAMENTPRIZE);
                    team.addMoney(amount);
                }
            }

        }
        userDao.save(user);
    }

    private void fillFixture(League league, Date newDate){

        Map<Date,List<Match>> map = fixtureService.generateFixture(league, newDate);
        for(Map.Entry entry : map.entrySet()){
            Date day = (Date) entry.getKey();
            for(Match match : (List<Match>) entry.getValue()){
                matchDao.create(league,match.getHome(),match.getAway(),day);
            }
        }
        league.setFixture(map);
    }

    private void subtractPlayerSalaries(Team team) {
        int amount = team.getSalaries();
        receiptDao.create(team, amount, Receipt.Type.PLAYERSSALARIES);
        team.addMoney(-amount);
    }
}