package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.User;
import com.epam.repository.UserRepository;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by fg on 7/27/2016.
 */
@Service
@Transactional
public class UserServiceImp implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        LOGGER.debug("get user by email");
        return userRepository.findByEmail(email);
    }

    @Override
    public User initByEmail(String userEmail) {
        LOGGER.debug("init user roles");
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            Hibernate.initialize(user.getUserRoles());
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        LOGGER.debug("get all users");
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllClients() {
        LOGGER.debug("get all clients");
        return userRepository.getAllClients();
    }

    @Override
    public List<User> getAllClientsWithBills() {
        LOGGER.debug("get All Clients With Bills");
        List<User> users = getAllClients();
        users.forEach(user -> {
            Iterator<Bill> iterator = user.getBills().iterator();
            iterator.forEachRemaining(bill -> {
                if (bill.getDeleted()) {
                    iterator.remove();
                }
            });
        });
        return users;
    }
}
