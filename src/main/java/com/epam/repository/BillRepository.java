package com.epam.repository;

import com.epam.model.Bill;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 */
public interface BillRepository {

    void add(final Bill bill);

    List<Bill> findAll();

    Bill findById(final int billId);

    Bill findByName(final String name);
}
