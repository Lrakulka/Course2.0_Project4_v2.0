package com.course.repository;

import com.course.model.Bill;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 * Work with bills in the database
 */
public interface BillRepository {

    /**
     * Add a bill to the database
     * @param bill which need to add
     */
    void add(final Bill bill);

    /**
     * Find all bills
     * @return the list of bills
     */
    List<Bill> findAll();

    /**
     * Find a bill by its id
     * @param billId id of the bill
     * @return the bill
     */
    Bill findById(final int billId);

    /**
     * Find a bill by its name
     * @param name of the bill
     * @return the bill
     */
    Bill findByName(final String name);

    /**
     * Update a bill to the database
     * @param bill which need to update
     */
    void update(Bill bill);
}
