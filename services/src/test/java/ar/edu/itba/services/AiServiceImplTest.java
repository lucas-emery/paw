package ar.edu.itba.services;

import ar.edu.itba.interfaces.service.AiService;
import ar.edu.itba.interfaces.service.FormationService;
import ar.edu.itba.model.Formation;
import ar.edu.itba.model.Player;
import ar.edu.itba.model.utils.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AiServiceImplTest {
    private List<Player> players;

    @Autowired
    private AiService aiService;

    @Configuration
    static class AiServiceConfig {

        @Bean
        public FormationService formationService() {
            FormationService formationService = mock(FormationService.class);

            when(formationService.create(any(Map.class), any(List.class), anyInt(), anyInt(), any(Player.class), any(Player.class), any(Player.class))).then((Answer<Formation>) invocationOnMock -> {
                Formation ret = mock(Formation.class);
                when(ret.getCaptain()).thenReturn((Player) invocationOnMock.getArguments()[4]);
                when(ret.getFreeKickTaker()).thenReturn((Player) invocationOnMock.getArguments()[5]);
                when(ret.getPenaltyTaker()).thenReturn((Player) invocationOnMock.getArguments()[6]);
                when(ret.getStarters()).thenReturn((Map<Player, Point>) invocationOnMock.getArguments()[0]);
                when(ret.getSubstitutes()).thenReturn((List<Player>) invocationOnMock.getArguments()[1]);
                when(ret.getAttitude()).thenReturn((Integer) invocationOnMock.getArguments()[3]);
                when(ret.getPressure()).thenReturn((Integer) invocationOnMock.getArguments()[2]);
                return ret;
            });

            return formationService;
        }

        @Bean
        public AiService aiService(){
            return new AiServiceImpl();
        }
    }

    @Before
    public void setUp() throws Exception {
        players = new ArrayList<>();

        for (int i = 0; i < 22; i++) {
            Player aux = mock(Player.class);
            when(aux.getAge()).thenReturn(20);
            when(aux.getId()).thenReturn(Long.valueOf(i));
            when(aux.getPassing()).thenReturn(50);
            when(aux.getDefending()).thenReturn(50);
            when(aux.getFitness()).thenReturn(80);
            when(aux.getGoalKeeping()).thenReturn(20);
            when(aux.getFinishing()).thenReturn(20);

            if(i == 0) {
                when(aux.getAge()).thenReturn(21);
                when(aux.getGoalKeeping()).thenReturn(80);
            } else if( i > 0 && i < 5){
                when(aux.getDefending()).thenReturn(80 + i);
            } else if( i >= 5 && i < 9 ) {
                when(aux.getPassing()).thenReturn(80 + i);
            } else if (i >= 9 && i < 11){
                when(aux.getFinishing()).thenReturn(80 + i);
            } else if (i>=11){
                when(aux.getFitness()).thenReturn(64);
            }


            players.add(aux);
        }
    }

    @Test
    public void getFormation() {
        Formation formation = aiService.getFormation(players);
        assertEquals(formation.getCaptain().getId(),0);
        assertEquals( formation.getFreeKickTaker().getId(), 8);
        assertEquals(formation.getPenaltyTaker().getId(),10);
        assertEquals(formation.getStarters().size(), 11);
        assertEquals(formation.getSubstitutes().size(), 7);
    }
}