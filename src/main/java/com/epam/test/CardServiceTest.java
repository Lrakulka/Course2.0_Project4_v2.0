package com.epam.test;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.service.CardService;
import com.epam.service.UserService;
import com.epam.test.configuration.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.epam.test.configuration.ExpectedData.CARD1;
import static com.epam.test.configuration.ExpectedData.USER_MASHA;
import static junit.framework.TestCase.*;

/**
 * Created by fg on 8/5/2016.
 * Tests for Card Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CardServiceTest {
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;

    @Test 
    public void blockCard(){
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.blockCard(card.getId());
        assertFalse(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getActive());
    }

    @Test 
    public void unBlockCard(){
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.unBlockCard(card.getId());
        assertTrue(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getActive());
    }

    @Test
    public void restoreCard(){
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.restoreCard(card.getId());
        assertFalse(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getDeleted());
    }

    @Test
    public void deleteCard(){
        Card card = (Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0];
        cardService.deleteCard(card.getId());
        assertTrue(((Card) ((Bill) userService.findByEmail(USER_MASHA.getEmail())
                .getBills().toArray()[0]).getCards().toArray()[0]).getDeleted());
    }

    @Test
    public void findByName(){
        assertNotNull(cardService.findByName(CARD1.getName()));
    }
}
