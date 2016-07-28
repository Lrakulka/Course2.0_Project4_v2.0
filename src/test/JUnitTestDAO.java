/**
 * 
 */
package test;

import static org.junit.Assert.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.homework.dao.ActorDAO;
import com.homework.dao.ActorDAOImp;
import com.homework.dao.AdminDAO;
import com.homework.dao.AdminDAOImp;
import com.homework.dao.ClientDAO;
import com.homework.dao.ClientDAOImp;
import com.homework.entities.Actor;
import com.homework.entities.Card;
/**
 * @author asd
 *
 * Tests for DAO of project.
 */
public class JUnitTestDAO {
    private static final double DELTA = 1e-15;
    private static final int CLIENTS_COUNT = 4;
    private static final double PAYMENTS = 200d;
    private static AdminDAO adminDAO;
    private static ClientDAO clientDAO;
    private static ActorDAO actorDAO;
    private static ClassPathXmlApplicationContext context;
    private static SessionFactory factory;
    private static List<Actor> actors;
    
    @BeforeClass
    public static void initTests() {
	context = new ClassPathXmlApplicationContext("test/springTest.xml");
	factory = (SessionFactory) context.getBean("hibernate4AnnotatedSessionFactory");
        adminDAO = context.getBean(AdminDAOImp.class);
        clientDAO = context.getBean(ClientDAOImp.class);
        actorDAO = context.getBean(ActorDAOImp.class);
        actors = adminDAO.getClients();
    }
    
    @AfterClass
    public static void closeTests() {
        context.close();    
    }
    // XXX
    /* Hibernate has problem with creation foreign keys from init file
     * That is why I update entities explicitly.
     * 
     * Method reinit database data for each test.
     */
    @Before
    public void init() {
	Session session = factory.openSession();
        Transaction tx2 = session.beginTransaction();
        actors.forEach(a -> {
            session.update(a);
            a.getCards().stream().forEach(c -> {
                    session.update(c);
                    session.update(c.getBill());
                    });
        	}
        );
	tx2.commit();
	session.close();
    }
    
    @Test
    public void getAllUsers() {
	assertEquals(adminDAO.getClients().size(), CLIENTS_COUNT);
    }
    
    @Test
    public void releaseBills() {
	adminDAO.getClients().stream().forEach(a -> a.getCards().
		forEach(c -> adminDAO.releaseBill(c.getBill().getId())));
	adminDAO.getClients().stream().forEach(a -> a.getCards().
		forEach(c -> assertFalse(c.getBill().getIsBlocked())));
    }
    
    @Test
    public void getUser() {
	List <Actor> actors = adminDAO.getClients();
	actors.stream().forEach(a -> {
	    assertTrue(actors.contains(clientDAO.getUser(a.getName(), a.getPass())));
	});
    }
    
    @Test
    public void blockCards() {
	adminDAO.getClients().stream().forEach(a -> { a.getCards().stream().
				forEach(c -> clientDAO.blockCard(a, c.getId()));
		});
	adminDAO.getClients().stream().forEach(a -> a.getCards().
		forEach(c -> assertTrue(c.getBill().getIsBlocked())));
    }
    
    @Test
    public void makePayments() {
	double sum1 = 0, sum2 = 0;
	List<Actor> actors = adminDAO.getClients();
	adminDAO.getClients().stream().forEach(a -> { a.getCards().stream().
				forEach(c -> {
				    clientDAO.makePayment(a, c.getId(), PAYMENTS);
				    });
        	});
	for (Actor a : actors) {
	    for (Card c : a.getCards()) {
		if (!c.getBill().getIsBlocked()) {
		    sum1 += c.getBill().getScore() - PAYMENTS;
		}
	    }
	}
	for (Actor a : adminDAO.getClients()) {
	    for (Card c : a.getCards()) {
		if (!c.getBill().getIsBlocked()) {
		    sum2 += c.getBill().getScore();
		}
	    }
	}
        assertEquals(sum1, sum2, DELTA);
    }
    
    @Test
    public void fillCards() {
	double sum1 = 0, sum2 = 0;
	List<Actor> actors = adminDAO.getClients();
	adminDAO.getClients().stream().forEach(a -> { a.getCards().stream().
				forEach(c -> {
				    clientDAO.fillCard(a, c.getId(), PAYMENTS);
				    });
        	});
	for (Actor a : actors) {
	    for (Card c : a.getCards()) {
		if (!c.getBill().getIsBlocked()) {
		    sum1 += c.getBill().getScore() + PAYMENTS;
		}
	    }
	}
	for (Actor a : adminDAO.getClients()) {
	    for (Card c : a.getCards()) {
		if (!c.getBill().getIsBlocked()) {
		    sum2 += c.getBill().getScore();
		}
	    }
	}
        assertEquals(sum1, sum2, DELTA);
    }
    
    @Test
    public void checkCardOwnerUsers() {
	adminDAO.getClients().stream().forEach(a -> { a.getCards().stream().
		forEach(c -> {
		    assertNotNull(clientDAO.check(a, c.getId()));
		    });
	});
	adminDAO.getClients().stream().forEach(a -> { a.getCards().stream().
		forEach(c -> {
		    assertNull(clientDAO.check(a, c.getId() + Integer.MIN_VALUE));
		    });
	});
    }
    
    @Test
    public void findByActorName() {
	adminDAO.getClients().stream().forEach(a -> {
	    assertTrue(a.equals(actorDAO.findByActorName(a.getName())));
	});
    }
}