package ar.edu.itba.persistence.hibernate;

import ar.edu.itba.model.League;
import ar.edu.itba.model.Player;
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
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Sql("classpath:schema.sql")
@Transactional
public class PlayerHibernateDaoTest {
    private final String NAME = "Pepe";
    private final int AGE = 22;
    private final int FITNESS = 123;
    private final int VALUE = 435;
    private final int POTENTIAL = 23;
    private final int SKILLLEVEL = 32;
    private final int GOALKEEPING = 87;
    private final int DEFENDING = 56;
    private final int PASSING = 98;
    private final int FINISHING = 67;
    private final int SALARY = 18903;
    private final Date CONTRACT_EXPIRATION = new Date(2000, 8, 15);
    private final boolean YOUTH = true;

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
        final Player player = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING,
                DEFENDING, PASSING, FITNESS, SALARY, CONTRACT_EXPIRATION, YOUTH);
        assertNotNull(player);
        assertEquals(NAME, player.getName());
        assertEquals(team, player.getTeam());
        assertEquals(AGE, player.getAge());
        assertEquals(VALUE, player.getValue());
        assertEquals(POTENTIAL, player.getPotential());
        assertEquals(SKILLLEVEL, player.getSkillLevel());
        assertEquals(GOALKEEPING, player.getGoalKeeping());
        assertEquals(FINISHING, player.getFinishing());
        assertEquals(DEFENDING, player.getDefending());
        assertEquals(PASSING, player.getPassing());
        assertEquals(FITNESS, player.getFitness());
        assertEquals(SALARY, player.getSalary());
        assertEquals(CONTRACT_EXPIRATION, player.getContractExpiration());
        assertEquals(YOUTH, player.isYouth());
    }

    @Test
    public void testFindById() {
        final long id = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING,
                DEFENDING, PASSING, FITNESS, SALARY, CONTRACT_EXPIRATION, YOUTH).getId();
        Player player = playerDao.findById(id);
        assertNotNull(player);
        assertEquals(NAME, player.getName());
        assertEquals(team.getId(), player.getTeamId());
        assertEquals(AGE, player.getAge());
        assertEquals(VALUE, player.getValue());
        assertEquals(POTENTIAL, player.getPotential());
        assertEquals(SKILLLEVEL, player.getSkillLevel());
        assertEquals(GOALKEEPING, player.getGoalKeeping());
        assertEquals(FINISHING, player.getFinishing());
        assertEquals(DEFENDING, player.getDefending());
        assertEquals(PASSING, player.getPassing());
        assertEquals(FITNESS, player.getFitness());
        assertEquals(SALARY, player.getSalary());
        assertEquals(CONTRACT_EXPIRATION, player.getContractExpiration());
        assertEquals(YOUTH, player.isYouth());
        assertEquals(id, player.getId());
    }

    @Test
    public void testFindAllByIdIn() {
        final long id = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING,
                DEFENDING, PASSING, FITNESS, SALARY, CONTRACT_EXPIRATION, YOUTH).getId();
        List<Long> ids = new LinkedList<>();
        ids.add(id);
        ids.add(7L);
        ids.add(54L);
        Player player = playerDao.findAllByIdIn(ids).get(0);
        assertNotNull(player);
        assertEquals(NAME, player.getName());
        assertEquals(team.getId(), player.getTeamId());
        assertEquals(AGE, player.getAge());
        assertEquals(VALUE, player.getValue());
        assertEquals(POTENTIAL, player.getPotential());
        assertEquals(SKILLLEVEL, player.getSkillLevel());
        assertEquals(GOALKEEPING, player.getGoalKeeping());
        assertEquals(FINISHING, player.getFinishing());
        assertEquals(DEFENDING, player.getDefending());
        assertEquals(PASSING, player.getPassing());
        assertEquals(FITNESS, player.getFitness());
        assertEquals(SALARY, player.getSalary());
        assertEquals(CONTRACT_EXPIRATION, player.getContractExpiration());
        assertEquals(YOUTH, player.isYouth());
        assertEquals(id, player.getId());
    }

    @Test
    public void testFindAdultsByTeamId() {
        final long id = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING,
                DEFENDING, PASSING, FITNESS, SALARY, CONTRACT_EXPIRATION, false).getId();
        Player player = playerDao.findAdultsByTeamId(team.getId()).get(0);
        assertNotNull(player);
        assertEquals(NAME, player.getName());
        assertEquals(team.getId(), player.getTeamId());
        assertEquals(AGE, player.getAge());
        assertEquals(VALUE, player.getValue());
        assertEquals(POTENTIAL, player.getPotential());
        assertEquals(SKILLLEVEL, player.getSkillLevel());
        assertEquals(GOALKEEPING, player.getGoalKeeping());
        assertEquals(FINISHING, player.getFinishing());
        assertEquals(DEFENDING, player.getDefending());
        assertEquals(PASSING, player.getPassing());
        assertEquals(FITNESS, player.getFitness());
        assertEquals(SALARY, player.getSalary());
        assertEquals(CONTRACT_EXPIRATION, player.getContractExpiration());
        assertEquals(false, player.isYouth());
        assertEquals(id, player.getId());
    }

    @Test
    public void testFindYouthByTeamId() {
        final long id = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING,
                DEFENDING, PASSING, FITNESS, SALARY, CONTRACT_EXPIRATION, true).getId();
        Player player = playerDao.findYouthByTeamId(team.getId()).get(0);
        assertNotNull(player);
        assertEquals(NAME, player.getName());
        assertEquals(team.getId(), player.getTeamId());
        assertEquals(AGE, player.getAge());
        assertEquals(VALUE, player.getValue());
        assertEquals(POTENTIAL, player.getPotential());
        assertEquals(SKILLLEVEL, player.getSkillLevel());
        assertEquals(GOALKEEPING, player.getGoalKeeping());
        assertEquals(FINISHING, player.getFinishing());
        assertEquals(DEFENDING, player.getDefending());
        assertEquals(PASSING, player.getPassing());
        assertEquals(FITNESS, player.getFitness());
        assertEquals(SALARY, player.getSalary());
        assertEquals(CONTRACT_EXPIRATION, player.getContractExpiration());
        assertEquals(true, player.isYouth());
        assertEquals(id, player.getId());
    }

    @Test
    public void testDelete() {
        final Player player1 = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING,
                DEFENDING, PASSING, FITNESS, SALARY, CONTRACT_EXPIRATION, YOUTH);
        final Player player2 = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING, DEFENDING, PASSING,
                FITNESS, SALARY, CONTRACT_EXPIRATION, YOUTH);

        assertNotNull(player1);
        assertNotNull(player2);

        playerDao.delete(player1);

        assertNotNull(playerDao.findById(player2.getId()));
    }

    @Test
    public void testSave() {
        final long id = playerDao.create(NAME, team, AGE, VALUE, POTENTIAL, SKILLLEVEL, GOALKEEPING, FINISHING,
                DEFENDING, PASSING, FITNESS, SALARY, CONTRACT_EXPIRATION, YOUTH).getId();
        Player player = playerDao.findById(id);
        assertNotNull(player);

        Team team2 = teamDao.create("", league, null, null, null, null, 0,0,0,0);
        final int AGE2 = 322;
        final int FITNESS2 = 4123;
        final int VALUE2 = 4635;
        final int POTENTIAL2 = 723;
        final int SKILLLEVEL2 = 832;
        final int GOALKEEPING2 = 897;
        final int DEFENDING2 = 576;
        final int PASSING2 = 908;
        final int FINISHING2 = 677;
        final int SALARY2 = 1893403;
        final Date CONTRACT_EXPIRATION2 = new Date(2015, 8, 15);
        final boolean YOUTH2 = false;

        player.setTeam(team2);
        player.setAge(AGE2);
        player.setFitness(FITNESS2);
        player.setValue(VALUE2);
        player.setPotential(POTENTIAL2);
        player.setSkillLevel(SKILLLEVEL2);
        player.setGoalKeeping(GOALKEEPING2);
        player.setDefending(DEFENDING2);
        player.setPassing(PASSING2);
        player.setFinishing(FINISHING2);
        player.setSalary(SALARY2);
        player.setContractExpiration(CONTRACT_EXPIRATION2);
        player.setYouth(YOUTH2);

        playerDao.save(player);
        player = playerDao.findById(id);

        assertEquals(NAME, player.getName());
        assertEquals(team2.getId(), player.getTeamId());
        assertEquals(AGE2, player.getAge());
        assertEquals(VALUE2, player.getValue());
        assertEquals(POTENTIAL2, player.getPotential());
        assertEquals(SKILLLEVEL2, player.getSkillLevel());
        assertEquals(GOALKEEPING2, player.getGoalKeeping());
        assertEquals(FINISHING2, player.getFinishing());
        assertEquals(DEFENDING2, player.getDefending());
        assertEquals(PASSING2, player.getPassing());
        assertEquals(FITNESS2, player.getFitness());
        assertEquals(SALARY2, player.getSalary());
        assertEquals(CONTRACT_EXPIRATION2, player.getContractExpiration());
        assertEquals(YOUTH2, player.isYouth());
    }
}