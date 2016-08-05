package com.epam.test.configuration;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.model.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fg on 8/5/2016.
 * Data for testing
 */
public class ExpectedData {
    public static final UserRole USER_ROLE_ADMIN = new UserRole() {
        {
            setRole("ROLE_ADMIN");
        }
    };

    public static final UserRole USER_ROLE_CLIENT = new UserRole() {
        {
            setRole("ROLE_CLIENT");
        }
    };

    public static final User USER_SASHA = new User(){
        {
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
            setActive(true);
            setFirstName("Саша");
            setLastName("Картошкін");
            setEmail("sasha@gmail.com");
        }
    };

    public static final User USER_MASHA = new User(){
        {
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
            setActive(true);
            setFirstName("Маша");
            setLastName("Машкевіч");
            setEmail("masha@gmail.com");
        }
    };

    public static final User USER_SASHA2 = new User(){
        {
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
            setActive(true);
            setFirstName("Саша");
            setLastName("Сашкевіч");
            setEmail("sasha2@gmail.com");
        }
    };

    public static final User USER_MASHA2 = new User(){
        {
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
            setActive(true);
            setFirstName("Masha");
            setLastName("Mashevich");
            setEmail("masha2@gmail.com");
        }
    };

    public static final List<User> USER_LIST = new ArrayList<User>() {
        {
            add(USER_SASHA);
            add(USER_MASHA);
            add(USER_SASHA2);
            add(USER_MASHA2);
        }
    };

    public static final Bill BILL1 = new Bill() {
        {
            setScore(200d);
            setActive(true);
            setName("bill1");
        }
    };

    public static final Bill BILL2 = new Bill() {
        {
            setScore(500d);
            setActive(true);
            setName("bill2");
        }
    };

    public static final Bill BILL3 = new Bill() {
        {
            setScore(700d);
            setActive(true);
            setName("bill3");
        }
    };

    public static final Bill BILL4 = new Bill() {
        {
            setScore(400d);
            setActive(true);
            setName("bill4");
        }
    };

    public static final Bill BILL5 = new Bill() {
        {
            setScore(800d);
            setActive(true);
            setName("bill5");
        }
    };

    public static final Card CARD1 = new Card() {
        {
            setActive(true);
            setName("card1");
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
        }
    };

    public static final Card CARD2 = new Card() {
        {
            setActive(true);
            setName("card2");
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
        }
    };

    public static final Card CARD3 = new Card() {
        {
            setActive(true);
            setName("card3");
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
        }
    };

    public static final Card CARD4 = new Card() {
        {
            setActive(true);
            setName("card4");
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
        }
    };

    public static final Card CARD5 = new Card() {
        {
            setActive(true);
            setName("card5");
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
        }
    };

    public static final Card CARD6 = new Card() {
        {
            setActive(true);
            setName("card6");
            setPassword("$2a$10$9.QmNEcFb/bf2lTthsN.TOHEfJrRRHQQdpHJ9SM9r6RTh7AMO3SRK");
        }
    };
}
