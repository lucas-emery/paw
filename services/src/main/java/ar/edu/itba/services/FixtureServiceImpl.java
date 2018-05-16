package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.TeamDao;
import ar.edu.itba.interfaces.service.FixtureService;
import ar.edu.itba.model.League;
import ar.edu.itba.model.Match;
import ar.edu.itba.model.Team;
import jdk.internal.util.xml.impl.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class FixtureServiceImpl implements FixtureService {

    private class Pair {
        private final Team home, away;

        private Pair(Team home, Team away) {
            this.home = home;
            this.away = away;
        }

        private boolean containsOne(Pair pair){
            return home.equals(pair.home) || away.equals(pair.home) || home.equals(pair.away) || away.equals(pair.away);
        }

        private Match toMatch(League league, Date date){
            return new Match(0,home,away,league,date, 0,0,0,0,null,false,null);
        }

        @Override
        public boolean equals(Object o) {
            if( o == null || !o.getClass().equals(Pair.class)){
                return false;
            }
            Pair p = (Pair) o;
            return p.home.equals(home) && p.away.equals(away);
        }

    }

    private Map<Date, List<Match>> assignDate(League league, Set<Pair> set, Date from){
        Map<Pair, Date> map = new HashMap<>();
        for(Pair pair : set){
            Date toSet = (Date) from.clone();
            Map<Pair, Date> aux = new HashMap<>();
            aux.putAll(map);
            for(Map.Entry entry : aux.entrySet()){
                if(pair.containsOne((Pair) entry.getKey())){
                    toSet = nextWeek(toSet);
                }
            }
            map.put(pair, toSet);
        }

        System.out.println(map.size());

        Map<Date, List<Match>> ret = new HashMap<>();
        for(Pair pair : set){
            Date aux = map.get(pair);
            if(ret.get(aux) == null)
                ret.put(aux, new ArrayList<>());
            ret.get(aux).add(pair.toMatch(league, aux));
        }

        return ret;
    }
    private Set<Pair> arrange(List<Team> teams){
        Set<Pair> ret = new HashSet<>();
        for(Team t1 : teams){
            for(Team t2 : teams){
                if(!t1.equals(t2))
                    ret.add(new Pair(t1, t2));
            }
        }
        return ret;
    }

    @Autowired
    private TeamDao teamDao;

    @Override
    public Map<Date, List<Match>> generateFixture(League league, Date from){
        List<Team> teams = teamDao.findAllByLeagueId(league.getId());
        Map<Date, List<Match>> map = new HashMap<>();

        return assignDate(league, arrange(teams), from);

    }

    private Date nextWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }
}
