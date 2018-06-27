package ar.edu.itba.persistence.hibernate;

import ar.edu.itba.model.*;
import ar.edu.itba.model.utils.Point;
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

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfiguration.class)
@Sql("classpath:schema.sql")
@Transactional
public class FormationHibernateDaoTest {
    private final int PRESSURE = 4;
    private final int ATTITUDE = 7;

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
    private FormationHibernateDao formationDao;

    private Map<Player, Point> starters;
    private Map<Long, Point> startersIds;
    private List<Player> substitutes;
    private List<Long> substitutesIds;
    private Player starter, substitute, captain, freeKick, penalty;
    private Team team;
    private League league;
    private User user;

    @Before
    public void setUp() {
        em.createQuery("DELETE FROM User").executeUpdate();
        user = userDao.create("c","","", new Date());
        league = leagueDao.create("", 0, user);
        team = teamDao.create("", league, null, null, null, null, 0,0,0,0);
        starter = playerDao.create("a", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        substitute = playerDao.create("b", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        captain = playerDao.create("c", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        freeKick = playerDao.create("d", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        penalty = playerDao.create("e", team, 0,0,0,0,0,0,0,0,0,0, new Date(), false);
        starters = new HashMap<>();
        starters.put(starter, new Point(1,2));
        startersIds = new HashMap<>();
        startersIds.put(starter.getId(), new Point(1,2));
        substitutes = new LinkedList<>();
        substitutes.add(substitute);
        substitutesIds = new LinkedList<>();
        substitutesIds.add(substitute.getId());
    }

    @Test
    public void testCreate() {
        final Formation formation = formationDao.create(starters, substitutes, PRESSURE, ATTITUDE, captain, freeKick, penalty);
        assertNotNull(formation);
        assertEquals(starters, formation.getStarters());
        assertEquals(substitutes, formation.getSubstitutes());
        assertEquals(PRESSURE, formation.getPressure());
        assertEquals(ATTITUDE, formation.getAttitude());
        assertEquals(captain, formation.getCaptain());
        assertEquals(freeKick, formation.getFreeKickTaker());
        assertEquals(penalty, formation.getPenaltyTaker());
    }

    @Test
    public void testCreateById() {
        final Formation formation = formationDao.create(startersIds, substitutesIds, PRESSURE, ATTITUDE, captain.getId(), freeKick.getId(), penalty.getId());
        assertNotNull(formation);
        assertEquals(startersIds.size(), formation.getStartersIds().size());
        assertEquals(startersIds.keySet().iterator().next(), formation.getStartersIds().keySet().iterator().next());
        assertEquals(startersIds.get(starter.getId()).getX(), formation.getStartersIds().get(starter.getId()).getX());
        assertEquals(startersIds.get(starter.getId()).getY(), formation.getStartersIds().get(starter.getId()).getY());
        assertEquals(substitutesIds.get(0), formation.getSubstitutesIds().get(0));
        assertEquals(PRESSURE, formation.getPressure());
        assertEquals(ATTITUDE, formation.getAttitude());
        assertEquals(captain.getId(), formation.getCaptainId());
        assertEquals(freeKick.getId(), formation.getFreeKickTakerId());
        assertEquals(penalty.getId(), formation.getPenaltyTakerId());
    }

    @Test
    public void testFindById() {
        final long id = formationDao.create(starters, substitutes, PRESSURE, ATTITUDE, captain, freeKick, penalty).getId();
        Formation formation = formationDao.findById(id);
        assertNotNull(formation);
        assertEquals(startersIds.size(), formation.getStartersIds().size());
        assertEquals(startersIds.keySet().iterator().next(), formation.getStartersIds().keySet().iterator().next());
        assertEquals(startersIds.get(starter.getId()).getX(), formation.getStartersIds().get(starter.getId()).getX());
        assertEquals(startersIds.get(starter.getId()).getY(), formation.getStartersIds().get(starter.getId()).getY());
        assertEquals(substitutesIds.get(0), formation.getSubstitutesIds().get(0));
        assertEquals(PRESSURE, formation.getPressure());
        assertEquals(ATTITUDE, formation.getAttitude());
        assertEquals(captain.getId(), formation.getCaptainId());
        assertEquals(freeKick.getId(), formation.getFreeKickTakerId());
        assertEquals(penalty.getId(), formation.getPenaltyTakerId());
        assertEquals(id, formation.getId());
    }

    @Test
    public void testSave() {
        final long id = formationDao.create(starters, substitutes, PRESSURE, ATTITUDE, captain, freeKick, penalty).getId();
        Formation formation = formationDao.findById(id);
        assertNotNull(formation);

        Player starter2 = playerDao.create("", team, 0,0,0,0,0,0,0,0,0,0,new Date(), false);
        Player substitute2 = playerDao.create("", team, 0,0,0,0,0,0,0,0,0,0,new Date(), false);
        Player captain2 = playerDao.create("", team, 0,0,0,0,0,0,0,0,0,0,new Date(), false);
        Player freeKick2 = playerDao.create("", team, 0,0,0,0,0,0,0,0,0,0,new Date(), false);
        Player penalty2 = playerDao.create("", team, 0,0,0,0,0,0,0,0,0,0,new Date(), false);
        Point starter2Point = new Point(8, 9);
        int newPressure = PRESSURE + 234;
        int newAttitude = ATTITUDE + 462;
        starters.put(starter2, starter2Point);
        startersIds.put(starter2.getId(), starter2Point);
        substitutes.add(substitute2);
        substitutesIds.add(substitute2.getId());

        formation.setStarters(starters);
        formation.setSubstitutes(substitutes);
        formation.setPressure(newPressure);
        formation.setAttitude(newAttitude);
        formation.setCaptain(captain2);
        formation.setFreeKickTaker(freeKick2);
        formation.setPenaltyTaker(penalty2);

        formationDao.save(formation);

        formation = formationDao.findById(id);

        assertEquals(startersIds.size(), formation.getStartersIds().size());
        assertEquals(startersIds.get(starter.getId()).getX(), formation.getStartersIds().get(starter.getId()).getX());
        assertEquals(startersIds.get(starter.getId()).getY(), formation.getStartersIds().get(starter.getId()).getY());
        assertEquals(startersIds.get(starter2.getId()).getX(), formation.getStartersIds().get(starter2.getId()).getX());
        assertEquals(startersIds.get(starter2.getId()).getY(), formation.getStartersIds().get(starter2.getId()).getY());
        assertTrue(formation.getSubstitutesIds().containsAll(substitutesIds));
        assertEquals(newPressure, formation.getPressure());
        assertEquals(newAttitude, formation.getAttitude());
        assertEquals(captain2.getId(), formation.getCaptainId());
        assertEquals(freeKick2.getId(), formation.getFreeKickTakerId());
        assertEquals(penalty2.getId(), formation.getPenaltyTakerId());
    }

}