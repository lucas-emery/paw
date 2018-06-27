package ar.edu.itba.persistence.hibernate;

import ar.edu.itba.model.League;
import ar.edu.itba.model.Receipt;
import ar.edu.itba.model.Team;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Sql("classpath:schema.sql")
@Transactional
public class ReceiptHibernateDaoTest {
    private final int AMOUNT = 41231;
    private final Receipt.Type TYPE = Receipt.Type.MATCHINCOME;

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
    private ReceiptHibernateDao receiptDao;

    private Team team;
    private League league;
    private User user;

    @Before
    public void setUp() {
        em.createQuery("DELETE FROM User").executeUpdate();
        user = userDao.create("c","","", new Date());
        league = leagueDao.create("", 0, user);
        team = teamDao.create("", league, null, null, null, null, 0,0,0,0);
    }

    @Test
    public void testCreate() {
        final Receipt receipt = receiptDao.create(team, AMOUNT, TYPE);
        assertNotNull(receipt);
        assertEquals(AMOUNT, receipt.getAmount());
        assertEquals(TYPE, receipt.getType());
    }

    @Test
    public void testFindAllByTeamId() {
        final long id = receiptDao.create(team, AMOUNT, TYPE).getId();
        Receipt receipt = receiptDao.findAllByTeamId(team.getId()).get(0);
        assertNotNull(receipt);
        assertEquals(AMOUNT, receipt.getAmount());
        assertEquals(TYPE, receipt.getType());
        assertEquals(id, receipt.getId());
    }
}