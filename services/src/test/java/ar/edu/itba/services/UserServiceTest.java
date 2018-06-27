package ar.edu.itba.services;

import ar.edu.itba.interfaces.dao.*;
import ar.edu.itba.interfaces.service.*;
import ar.edu.itba.model.Team;
import ar.edu.itba.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfiguration.class, UserServiceTest.UserServiceConfig.class})
public class UserServiceTest {

    @Configuration
    static class UserServiceConfig {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public LeagueService leagueService() {
            return mock(LeagueService.class);
        }

        @Bean
        public UserDao userDao() {
            return mock(UserDao.class);
        }

        @Bean
        public TeamService teamService(){
            return new TeamServiceImpl();
        }

        @Bean
        public AiService aiService(){
            return new AiServiceImpl();
        }

        @Bean
        public FormationService formationService(){
            return new FormationServiceImpl();
        }

        @Bean
        public EconomyService economyService(){
            return new EconomyServiceImpl();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return mock(PasswordEncoder.class);
        }

    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;
    private Team team;

    @Before
    public void setUp() {
        user = new User(1,null,null,null,null,null);
        team = mock(Team.class);
    }

    @Test
    public void setTeam() {
        userService.setTeam(user, team);

        assertTrue(user.getTeam().equals(team));
    }

}
