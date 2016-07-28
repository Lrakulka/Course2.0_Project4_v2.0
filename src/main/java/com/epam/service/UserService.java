package com.epam.service;


import com.epam.model.User;

/**
 * Created by fg on 7/27/2016.
 */

public interface UserService {
    User findByEmail(String email);
}
