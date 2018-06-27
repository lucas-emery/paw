package ar.edu.itba.persistence.hibernate;

import ar.edu.itba.model.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Sql("classpath:schema.sql")
@Transactional
public class PlayerStatsHibernateDaoTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private LeagueHibernateDao leagueDao;
    @Autowired
    private UserHibernateDao userDao;
    @Autowired
    private PlayerHibernateDao playerDao;
    @Autowired
    private MatchHibernateDao matchDao;
    @Autowired
    private TeamHibernateDao teamDao;
    @Autowired
    private PlayerStatsHibernateDao playerStatsDao;

    private Player player;
    private Match match;
    private Team team;
    private League league;
    private User user;

    @Before
    public void setUp() {
        em.createQuery("DELETE FROM User").executeUpdate();
        user = userDao.create("c","","", new Date());
        league = leagueDao.create("", 0, user);
        team = teamDao.create("", league, null, null, null, null, 0,0,0,0);
        player = playerDao.create("a", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        match = matchDao.create(league, team, team, new Date());
    }

    @Test
    public void testCreate() {
        final PlayerStats playerStats = playerStatsDao.create(player, match);
        assertNotNull(playerStats);
        assertEquals(player, playerStats.getPlayer());
        assertEquals(match, playerStats.getMatch());
    }

    @Test
    public void testFindByMatchId() {
        final long id = playerStatsDao.create(player, match).getId();
        PlayerStats playerStats = playerStatsDao.findByMatchId(match.getId()).get(0);
        assertNotNull(playerStats);
        assertEquals(player.getId(), playerStats.getPlayerId());
        assertEquals(match.getId(), playerStats.getMatchId());
        assertEquals(id, playerStats.getId());
    }

    @Test
    public void testSave() {
        final long id = playerStatsDao.create(player, match).getId();
        PlayerStats playerStats = playerStatsDao.findByMatchId(match.getId()).get(0);
        assertNotNull(playerStats);


    }
}