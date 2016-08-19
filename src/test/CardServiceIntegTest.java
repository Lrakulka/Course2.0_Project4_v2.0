import com.course.model.Bill;
import com.course.model.Card;
import com.course.service.CardService;
import com.course.service.UserService;
import configuration.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static configuration.ExpectedData.CARD1;
import static configuration.ExpectedData.USER_MASHA;
import static junit.framework.TestCase.*;

/**
 * Created by fg on 8/5/2016.
 * Tests for Card Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CardServiceIntegTest implements CardServiceTest {

    private CardService cardService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    @Test
    @Override
    public void blockCard() {
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.blockCard(card.getId());
        assertFalse(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getActive());
    }

    @Test
    @Override
    public void unBlockCard() {
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.unBlockCard(card.getId());
        assertTrue(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getActive());
    }

    @Test
    @Override
    public void restoreCard() {
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.restoreCard(card.getId());
        assertFalse(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getDeleted());
    }

    @Test
    @Override
    public void deleteCard() {
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.deleteCard(card.getId());
        assertTrue(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getDeleted());
    }

    @Test
    @Override
    public void findByName() {
        assertNotNull(cardService.findByName(CARD1.getName()));
    }
}
