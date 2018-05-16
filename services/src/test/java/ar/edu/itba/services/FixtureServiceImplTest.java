package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.TeamDao;
import ar.edu.itba.interfaces.service.FixtureService;
import ar.edu.itba.model.League;
import ar.edu.itba.model.Match;
import ar.edu.itba.model.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static junit.framework.TestCase.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FixtureServiceImplTest {


    @Configuration
    static class FixtureServiceConfig {
        @Bean
        public FixtureService fixtureService(){
            return new FixtureServiceImpl();
        }

        @Bean
        public TeamDao teamDao() {
            return mock(TeamDao.class);
        }

    }

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private FixtureService fixtureService;

    private League league;
    private Date from;
    private List<Team> teams;
    private final long id = 1;

    @Before
    public void setUp() throws Exception {
        from = new Date();

        teams = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            teams.add(mock(Team.class));
        }

        league = mock(League.class);
        when(league.getId()).thenReturn(id);

        when(teamDao.findAllByLeagueId(id)).thenReturn(teams);
    }

    @Test
    public void generateFixtureTest() {
        Map<Date,List<Match>> map = fixtureService.generateFixture(league,from);
        assertTrue(map.size() == 20);

        int count = 0;
        Team t1 = teams.get(0);
        for(Map.Entry entry : map.entrySet()){
            for(Match match : (List<Match>) entry.getValue()){
                if(match.getAway().equals(t1) || match.getHome().equals(t1))
                    count++;
            }
        }

        System.out.println(count);
        assertTrue(count == 38);
    }
}