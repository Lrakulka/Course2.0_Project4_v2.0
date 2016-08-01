package com.epam.repository;

import com.epam.model.User;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 */
public interface UserRepository {
    void add(final User category);

    List<User> findAll();

    User findById(final int userId);

    User findByEmail(final String email);

    List<User> getAllClients();
}
