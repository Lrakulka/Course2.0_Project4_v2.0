package com.epam.service;

import com.epam.model.User;
import com.epam.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by fg on 7/27/2016.
 */
@Service
@Transactional
public class UserServiceImp implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
