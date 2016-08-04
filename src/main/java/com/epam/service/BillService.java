package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.Card;
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

    boolean fillBill(String userEmail, Integer billId, Double money);

    Bill getClientBill(User user, Integer billId);

    boolean checkPassword(Bill clientBill, String nativeCardId, String password);

    void makePayment(Bill clientBill, Card exceptCard, Double payment);
}
