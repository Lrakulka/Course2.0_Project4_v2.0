package com.epam.repository;

import com.epam.model.User;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 * Work with users in the database
 */
public interface UserRepository {
    /**
     * Add a user to the database
     * @param user which need to update
     */
    void add(final User user);

    /**
     * Find all users
     * @return the list of users
     */
    List<User> findAll();

    /**
     * Find a user by id
     * @param userId value of the user id
     * @return
     */
    User findById(final int userId);

    /**
     * Find a user by email
     * @param email value of the user
     * @return the user
     */
    User findByEmail(final String email);

    /**
     * Get all clients from the database
     * @return the list of users which are clients
     */
    List<User> getAllClients();
}
