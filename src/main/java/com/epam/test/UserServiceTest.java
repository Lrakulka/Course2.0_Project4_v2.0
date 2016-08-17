package com.epam.test;

import com.epam.model.User;
import com.epam.service.UserService;
import com.epam.test.configuration.TestConfig;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.epam.test.configuration.ExpectedData.USER_LIST;
import static com.epam.test.configuration.ExpectedData.USER_SASHA;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by fg on 8/4/2016.
 * Tests for User Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class UserServiceTest {

    private UserService userService;

    @Autowired
    public void setUserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void findByEmail() {
        User user = userService.findByEmail(USER_SASHA.getEmail());
        assertTrue(user.getFirstName().equals(USER_SASHA.getFirstName()));
        assertTrue(user.getLastName().equals(USER_SASHA.getLastName()));
    }

    @Test
    public void initByEmail() {
        User user = userService.initByEmail(USER_SASHA.getEmail());
        assertTrue(Hibernate.isInitialized(user.getUserRoles()));
    }

    @Test
    public void getAll() {
        assertTrue(userService.getAll().size() == USER_LIST.size());
    }

    @Test
    public void getAllClients() {
        assertTrue(userService.getAllClients().size() == USER_LIST.size());
    }

    @Test
    public void getAllClientsWithBills() {
        assertTrue(userService.getAllClientsWithBills().size() == USER_LIST.size());
    }
}