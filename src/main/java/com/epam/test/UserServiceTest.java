package com.epam.test;

import com.epam.configuration.DatabaseConfiguration;
import com.epam.configuration.SpringSecurityConfig;
import com.epam.service.UserService;
import com.epam.test.configuration.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User service test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, DatabaseConfiguration.class, SpringSecurityConfig.class})
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void findByEmail(String email) {

    }

    @Test
    void initByEmail(String userEmail) {

    }

    @Test
    void getAll() {

    }

    @Test
    void getAllClients() {

    }

    @Test
    void getAllClientsWithBills() {

    }
}