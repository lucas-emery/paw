package ar.edu.itba.persistence.hibernate;

import ar.edu.itba.model.*;
import ar.edu.itba.persistence.jdbc.TestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Sql("classpath:schema.sql")
@Transactional
public class EventHibernateDaoTest {
    private final Event.Type TYPE = Event.Type.HOMESCORE;
    private final int MINUTE = 1234;

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
    private EventHibernateDao eventDao;

    private Player player1;
    private Player player2;
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
        player1 = playerDao.create("a", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        player2 = playerDao.create("b", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        match = matchDao.create(league, team, team, new Date());
    }

    @Test
    public void testCreate() {
        final Event event = eventDao.create(match, player1, player2, TYPE, MINUTE);
        assertNotNull(event);
        assertEquals(player1, event.getP1());
        assertEquals(player2, event.getP2());
        assertEquals(TYPE, event.getType());
        assertEquals(MINUTE, event.getMinute());
    }

    @Test
    public void testFindAllByUserId() {
        final long id = eventDao.create(match, player1, player2, TYPE, MINUTE).getId();
        Event event = eventDao.findByMatchId(match.getId()).get(0);
        assertNotNull(event);
        assertEquals(player1.getId(), event.getP1Id());
        assertEquals(player2.getId(), event.getP2Id());
        assertEquals(TYPE, event.getType());
        assertEquals(MINUTE, event.getMinute());
        assertEquals(id, event.getId());
    }
}