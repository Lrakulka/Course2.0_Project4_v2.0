import com.course.model.Bill;
import com.course.model.Card;
import com.course.model.User;
import com.course.service.BillService;
import com.course.service.UserService;
import configuration.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static configuration.ExpectedData.USER_SASHA;
import static configuration.ExpectedData.USER_SASHA2;
import static junit.framework.TestCase.*;

/**
 * Created by fg on 8/5/2016.
 * Tests for Bill Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BillServiceIntegTest  {

    private BillService billService;
    private UserService userService;

    @Autowired
    void setBillService(BillService billService) {
        this.billService = billService;
    }

    @Autowired
    void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void blockBill() {
        Bill bill = (Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1];
        billService.blockBill(bill.getId());
        assertFalse(((Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1]).getActive());
    }

    @Test
    public void unBlockBill() {
        Bill bill = (Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1];
        billService.unBlockBill(bill.getId());
        assertTrue(((Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1]).getActive());
    }

    @Test
    public void restoreBill() {
        Bill bill = (Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1];
        billService.restoreBill(bill.getId());
        assertFalse(((Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1]).getDeleted());
    }

    @Test
    public void deleteBill() {
        Bill bill = (Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1];
        billService.deleteBill(bill.getId());
        assertTrue(((Bill) userService.findByEmail(USER_SASHA.getEmail())
                .getBills().toArray()[1]).getDeleted());
    }

    @Test
    public void getAllClientBills() {
        List<Bill> bills = billService.getAllClientBills(userService
                .findByEmail(USER_SASHA2.getEmail()));
        assertTrue(bills.size() == 1);
    }

    @Test
    public void fillBill() {
        Bill bill = (Bill) userService.findByEmail(USER_SASHA2.getEmail())
                .getBills().toArray()[0];
        double score = bill.getScore() + 100;
        billService.fillBill(bill.getId(), 100d);
        assertTrue(((Bill) userService.findByEmail(USER_SASHA2.getEmail())
                .getBills().toArray()[0]).getScore().equals(score));
    }

    @Test
    public void getClientBill() {
        User user = userService.findByEmail(USER_SASHA.getEmail());
        Bill bill = billService.getClientBill(user, ((Bill) user.getBills()
                .toArray()[0]).getId());
        assertNotNull(bill);
    }

    @Test
    public void makePayment() {
        Bill bill = (Bill) userService.findByEmail(USER_SASHA2.getEmail())
                .getBills().toArray()[0];
        Double p = bill.getScore();
        billService.makePayment(bill.getId(), ((Card) bill.getCards()
                .toArray()[0]).getBill().getId(), 1d);
        assertTrue(((Bill) userService.findByEmail(USER_SASHA2.getEmail())
                .getBills().toArray()[0]).getScore().equals(p));
    }
}
