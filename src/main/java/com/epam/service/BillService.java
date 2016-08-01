package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.User;

import java.util.List;

/**
 * Created by fg on 7/27/2016.
 */
public interface BillService {

    void blockBill(int billId);

    void doAction(String actionAndBillId);

    void unBlockBill(int billId);

    void restoreBill(int billId);

    void deleteBill(int billId);

    List<Bill> getAllClientBills(User user);
}
