package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.AiService;
import ar.edu.itba.interfaces.service.FormationService;
import ar.edu.itba.interfaces.service.TeamService;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.Receipt;
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
import java.util.List;

<<<<<<< HEAD
import static junit.framework.TestCase.assertTrue;
=======
import static junit.framework.TestCase.assertEquals;
>>>>>>> master
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoConfiguration.class, TeamServiceTest.TeamServiceConfig.class})
public class TeamServiceTest {

    @Configuration
    static class TeamServiceConfig {

        @Bean
        public TeamService teamService() {
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
    }

    @Autowired
    private TeamService teamService;

    private Player player1, player2, player3;
    private Team team;
    private Receipt receipt1, receipt2, receipt3;

    @Before
    public void setUp() {
        List<Player> players = new ArrayList<>();
        List<Receipt> receipts = new ArrayList<>();
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        player3 = mock(Player.class);
        receipt1 = mock(Receipt.class);
        receipt2 = mock(Receipt.class);
        receipt3 = mock(Receipt.class);
        team = mock(Team.class);

        players.add(player1);
        players.add(player2);
        players.add(player3);

        receipts.add(receipt1);
        receipts.add(receipt2);
        receipts.add(receipt3);

        when(player1.getSalary()).thenReturn(10);
        when(player2.getSalary()).thenReturn(20);
        when(player3.getSalary()).thenReturn(30);

        when(receipt1.getType()).thenReturn(Receipt.Type.MATCHINCOME);
        when(receipt2.getType()).thenReturn(Receipt.Type.MATCHINCOME);
        when(receipt3.getType()).thenReturn(Receipt.Type.BOUGHTPLAYER);

        when(receipt1.getAmount()).thenReturn(10);
        when(receipt2.getAmount()).thenReturn(10);
        when(receipt3.getAmount()).thenReturn(10);

        when(team.getPlayers()).thenReturn(players);
        when(team.getFinance()).thenReturn(receipts);

    }

    @Test
<<<<<<< HEAD
    public void ticketsSold() {
        int amount = teamService.getTicketsSold(team);

        assertTrue(amount == 20);
    }

    @Test
=======
>>>>>>> master
    public void salaries() {
        int amount = teamService.getSalaries(team);

        assertTrue(amount == 60);
    }

}
