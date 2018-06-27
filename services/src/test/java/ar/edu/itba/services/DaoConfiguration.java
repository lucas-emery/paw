package ar.edu.itba.services;


import ar.edu.itba.interfaces.dao.*;
import ar.edu.itba.model.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class DaoConfiguration {

    @Bean
    public MatchDao matchDao() {
        MatchDao matchDao = mock(MatchDao.class);
        when(matchDao.create(any(League.class), any(Team.class), any(Team.class), any(Date.class))).then((Answer<Match>) invocationOnMock -> {
            Match match = mock(Match.class);
            when(match.getHome()).thenReturn((Team) invocationOnMock.getArguments()[1]);
            when(match.getAway()).thenReturn((Team) invocationOnMock.getArguments()[2]);
            when(match.getLeague()).thenReturn((League) invocationOnMock.getArguments()[0]);
            when(match.getDay()).thenReturn((Date) invocationOnMock.getArguments()[3]);
            return match;
        });
        return matchDao;
    }

    @Bean
    public TeamDao teamDao(){
        TeamDao teamDao = mock(TeamDao.class);
        when(teamDao.create(anyString(), any(League.class), any(Stadium.class), any(Formation.class), any(List.class), any(List.class), anyInt(), anyInt(), anyInt(), anyInt())).then((Answer<Team>) invocationOnMock -> {
            Team team = mock(Team.class);
            when(team.getPlayers()).thenReturn((List<Player>) invocationOnMock.getArguments()[4]);
            when(team.getName()).thenReturn((String) invocationOnMock.getArguments()[0]);
            when(team.getLeague()).thenReturn((League) invocationOnMock.getArguments()[1]);
            when(team.getFormation()).thenReturn((Formation) invocationOnMock.getArguments()[3]);
            when(team.getStadium()).thenReturn((Stadium) invocationOnMock.getArguments()[2]);
            when(team.getYouthAcademy()).thenReturn((List<Player>) invocationOnMock.getArguments()[5]);
            when(team.getFanCount()).thenReturn((Integer) invocationOnMock.getArguments()[6]);
            when(team.getFanTrust()).thenReturn((Integer) invocationOnMock.getArguments()[7]);
            when(team.getBoardTrust()).thenReturn((Integer) invocationOnMock.getArguments()[8]);
            when(team.getMoney()).thenReturn((Integer) invocationOnMock.getArguments()[9]);
            return team;
        });
        return teamDao;
    }

    @Bean
    public LeagueDao leagueDao(){
        LeagueDao leagueDao = mock(LeagueDao.class);
        when(leagueDao.create(anyString(), anyInt(), any(User.class))).then(new Answer<League>() {
            @Override
            public League answer(InvocationOnMock invocationOnMock) throws Throwable {
                League league = mock(League.class);
                when(league.getName()).then((Answer<?>) invocationOnMock.getArguments()[0]);
                when(league.getPrize()).then((Answer<?>) invocationOnMock.getArguments()[1]);
                when(league.getUser()).then((Answer<?>) invocationOnMock.getArguments()[2]);
                return league;
            }
        });
        return leagueDao;
    }

    @Bean
    public EventDao eventDao(){
        EventDao eventDao = mock(EventDao.class);
        when(eventDao.create(any(Match.class), any(Player.class), any(Player.class), any(Event.Type.class), anyInt())).then(new Answer<Event>() {
            @Override
            public Event answer(InvocationOnMock invocationOnMock) throws Throwable {
                Event event = mock(Event.class);
                when(event.getType()).thenReturn((Event.Type) invocationOnMock.getArguments()[3]);
                when(event.getMatch()).thenReturn((Match) invocationOnMock.getArguments()[0]);
                when(event.getP1()).thenReturn((Player) invocationOnMock.getArguments()[1]);
                when(event.getP2()).thenReturn((Player) invocationOnMock.getArguments()[2]);
                return event;
            }
        });
        return eventDao;
    }

    @Bean
    public PlayerDao playerDao(){
        PlayerDao playerDao = mock(PlayerDao.class);
        return playerDao;
    }

    @Bean
    public ReceiptDao receiptDao(){
        ReceiptDao receiptDao = mock(ReceiptDao.class);
        when(receiptDao.create(any(Team.class), anyInt(), any(Receipt.Type.class))).then((Answer<Receipt>) invocationOnMock -> {
            Receipt receipt = mock(Receipt.class);
            when(receipt.getAmount()).thenReturn((Integer) invocationOnMock.getArguments()[1]);
            when(receipt.getTeam()).thenReturn((Team) invocationOnMock.getArguments()[0]);
            when(receipt.getType()).thenReturn((Receipt.Type) invocationOnMock.getArguments()[2]);
            return receipt;
        });
        return receiptDao;
    }

    @Bean
    public FormationDao formationDao(){
        FormationDao formationDao = mock(FormationDao.class);
        return formationDao;
    }
}
