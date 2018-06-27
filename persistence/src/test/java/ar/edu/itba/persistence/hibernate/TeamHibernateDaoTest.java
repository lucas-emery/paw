package ar.edu.itba.persistence.hibernate;

import ar.edu.itba.interfaces.service.TeamService;
import ar.edu.itba.model.League;
import ar.edu.itba.model.Team;
import ar.edu.itba.model.User;
import ar.edu.itba.persistence.hibernate.*;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Sql("classpath:schema.sql")
@Transactional
public class TeamHibernateDaoTest {

    private final String NAME = "Boca";
    private final int FANCOUNT = 12331424;
    private final int FANTRUST = 99;
    private final int BOARDTRUST = 1231;
    private final int MONEY = 928522342;
    private final int NO_ID = 0;
    private final int SQL_NULL_INT = 0;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private LeagueHibernateDao leagueDao;
    @Autowired
    private UserHibernateDao userDao;
    @Autowired
    private PlayerHibernateDao playerDao;
    @Autowired
    private TeamHibernateDao teamDao;
    @Autowired
    private StadiumHibernateDao stadiumDao;

    private League league;
    private User user;

    @Before
    public void setUp() {
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM Team").executeUpdate();
        user = userDao.create("c","","", new Date());
        league = leagueDao.create("", 0, user);
    }

    @Test
    public void testCreate() {
        final Team team = teamDao.create(NAME, league, null, null, null, null, FANCOUNT, FANTRUST, BOARDTRUST, MONEY);
        assertNotNull(team);
        assertEquals(NAME, team.getName());
        assertEquals(league, team.getLeague());
        assertEquals(SQL_NULL_INT, team.getFormationId());
        assertEquals(FANCOUNT, team.getFanCount());
        assertEquals(FANTRUST, team.getFanTrust());
        assertEquals(BOARDTRUST, team.getBoardTrust());
        assertEquals(MONEY, team.getMoney());
    }

    @Test
    public void testFindById() {
        final long id = teamDao.create(NAME, league, null, null, null, null, FANCOUNT, FANTRUST, BOARDTRUST, MONEY).getId();
        Team team = teamDao.findById(id);
        assertNotNull(team);
        assertEquals(NAME, team.getName());
        assertEquals(league.getId(), team.getLeagueId());
        assertEquals(SQL_NULL_INT, team.getFormationId());
        assertEquals(FANCOUNT, team.getFanCount());
        assertEquals(FANTRUST, team.getFanTrust());
        assertEquals(BOARDTRUST, team.getBoardTrust());
        assertEquals(MONEY, team.getMoney());
        assertEquals(id, team.getId());
    }

    @Test
    public void testFindAllByLeagueId() {
        final long id = teamDao.create(NAME, league, null, null, null, null, FANCOUNT, FANTRUST, BOARDTRUST, MONEY).getId();
        Team team = teamDao.findAllByLeagueId(league.getId()).get(0);
        assertNotNull(team);
        assertEquals(NAME, team.getName());
        assertEquals(league.getId(), team.getLeagueId());
        assertEquals(SQL_NULL_INT, team.getFormationId());
        assertEquals(FANCOUNT, team.getFanCount());
        assertEquals(FANTRUST, team.getFanTrust());
        assertEquals(BOARDTRUST, team.getBoardTrust());
        assertEquals(MONEY, team.getMoney());
        assertEquals(id, team.getId());
    }

    @Test
    public void testFindByUserId() {
        final Team expectedTeam = teamDao.create(NAME, league, null, null, null, null, FANCOUNT, FANTRUST, BOARDTRUST, MONEY);
        user.setTeam(expectedTeam);
        userDao.save(user);
        Team team = teamDao.findByUserId(user.getId());
        assertNotNull(team);
        assertEquals(NAME, team.getName());
        assertEquals(league.getId(), team.getLeagueId());
        assertEquals(SQL_NULL_INT, team.getFormationId());
        assertEquals(FANCOUNT, team.getFanCount());
        assertEquals(FANTRUST, team.getFanTrust());
        assertEquals(BOARDTRUST, team.getBoardTrust());
        assertEquals(MONEY, team.getMoney());
        assertEquals(expectedTeam.getId(), team.getId());
    }
}