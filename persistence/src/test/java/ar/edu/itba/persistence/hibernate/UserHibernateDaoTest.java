package ar.edu.itba.persistence.hibernate;

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
public class UserHibernateDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final String MAIL = "Mail";
    private static final Date CURRENTDAY = new Date(2000,8,15);

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserHibernateDao userDao;
    @Before
    public void setUp() {
        em.createQuery("DELETE FROM User").executeUpdate();
    }

    @Test
    public void testCreate() {
        final User user = userDao.create(USERNAME, PASSWORD, MAIL, CURRENTDAY);
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(MAIL, user.getMail());
        assertEquals(CURRENTDAY, user.getCurrentDay());
    }

    @Test
    public void testFindByUsername() {
        final long id = userDao.create(USERNAME, PASSWORD, MAIL, CURRENTDAY).getId();
        final User user = userDao.findByUsername(USERNAME);
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(MAIL, user.getMail());
        assertEquals(CURRENTDAY, user.getCurrentDay());
        assertEquals(id, user.getId());
    }

    @Test
    public void testFindById() {
        final long id = userDao.create(USERNAME, PASSWORD, MAIL, CURRENTDAY).getId();
        final User user = userDao.findById(id);
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(MAIL, user.getMail());
        assertEquals(CURRENTDAY, user.getCurrentDay());
        assertEquals(id, user.getId());
    }
}