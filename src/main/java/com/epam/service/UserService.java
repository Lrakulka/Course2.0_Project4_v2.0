package com.epam.service;


import com.epam.model.User;

import java.util.List;

/**
 * Created by fg on 7/27/2016.
 */

public interface UserService {
    User findByEmail(String email);

    User initByEmail(String userEmail);

    List<User> getAll();

    List<User> getAllClients();

    List<User> getAllClientsWithBills();
}
