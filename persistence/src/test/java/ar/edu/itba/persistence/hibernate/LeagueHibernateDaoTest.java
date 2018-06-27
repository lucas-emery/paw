package ar.edu.itba.persistence.hibernate;

import ar.edu.itba.model.League;
import ar.edu.itba.model.Match;
import ar.edu.itba.model.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Sql("classpath:schema.sql")
@Transactional
public class LeagueHibernateDaoTest {

    private final String NAME = "League";
    private final int PRIZE = 1234;
    private final Map<Date, List<Match>> FIXTURE = new HashMap<>();

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private LeagueHibernateDao leagueDao;
    @Autowired
    private UserHibernateDao userDao;

    private User user;

    @Before
    public void setUp() {
        em.createQuery("DELETE FROM User").executeUpdate();
        userDao.create("a","","", new Date());
        userDao.create("b","","", new Date());
        user = userDao.create("c","","", new Date());
    }

    @Test
    public void testCreate() {
        final League league = leagueDao.create(NAME, FIXTURE, PRIZE, user);
        assertNotNull(league);
        assertEquals(NAME, league.getName());
        assertEquals(FIXTURE, league.getFixture());
        assertEquals(PRIZE, league.getPrize());
    }

    @Test
    public void testFindById() {
        final long id = leagueDao.create(NAME, FIXTURE, PRIZE, user).getId();
        League league = leagueDao.findById(id);
        assertNotNull(league);
        assertEquals(NAME, league.getName());
        assertEquals(PRIZE, league.getPrize());
        assertEquals(id, league.getId());
    }

    @Test
    public void testFindAllByUserId() {
        final long id = leagueDao.create(NAME, FIXTURE, PRIZE, user).getId();
        League league = leagueDao.findAllByUserId(user.getId()).get(0);
        assertNotNull(league);
        assertEquals(NAME, league.getName());
        assertEquals(PRIZE, league.getPrize());
        assertEquals(id, league.getId());
    }
}