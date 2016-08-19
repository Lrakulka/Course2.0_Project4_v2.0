package com.course.service;


import com.course.model.User;

import java.util.List;

/**
 * Created by fg on 7/27/2016.
 * Business logic for user
 */

public interface UserService {
    /**
     * Find a user by its email
     * @param email of the user
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Init a user roles
     * @param userEmail of the user
     * @return the user with initialized the list of roles
     */
    User initByEmail(String userEmail);

    /**
     * Return all users in the database
     * @return the list of users
     */
    List<User> getAll();

    /**
     * Return all user with role client
     * @return the list of clients
     */
    List<User> getAllClients();

    /**
     * Find all clients which have not deleted bills
     * @return the list of users
     */
    List<User> getAllClientsWithBills();
}
