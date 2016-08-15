package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;

import java.util.List;

/**
 * Created by fg on 7/27/2016.
 * Business logic for bill
 */
public interface BillService {
    String DELETE = "delete";
    String UN_DELETE = "undelete";
    String BLOCK = "block";
    String UN_BLOCK = "unblock";

    /**
     * Block bill by its id
     * @param billId value of id
     */
    void blockBill(int billId);

    /**
     * Make next action with a bill.
     * @param actionAndBillId :
     * @see #DELETE - delete the bill
     * @see #UN_DELETE - restore the bill
     * @see #BLOCK - block the bill
     * @see #UN_BLOCK - activate the bill
     */
    void doAction(String actionAndBillId);

    /**
     * Activate a bill
     * @param billId value of bill id
     */
    void unBlockBill(int billId);

    /**
     * Restore a bill
     * @param billId value of bill id
     */
    void restoreBill(int billId);

    /**
     * Delete a bill
     * @param billId value of bill id
     */
    void deleteBill(int billId);

    /**
     * Return all bills of the user
     * @param user which bills needed
     * @return the list of users
     */
    List<Bill> getAllClientBills(User user);

    /**
     * Fill a bill with money
     * @param userEmail - email of the bill owner
     * @param billId - id of the bill
     * @param money which need to add to the bill
     * @return true if operation successful, false if not
     */
    boolean fillBill(String userEmail, Integer billId, Double money);

    /**
     * Return a bill of the user
     * @param user - owner of the bill
     * @param billId - id of bill
     * @return return the bill or null if the user doesn't have the bill
     */
    Bill getClientBill(User user, Integer billId);

    /**
     * Check a password from the bill
     * @param clientBill - bill
     * @param nativeCardId - id of the card which belong to the bill
     * @param password - the card password
     * @return
     */
    boolean checkPassword(Bill clientBill, Integer nativeCardId, String password);

    /**
     * Sent money to from one bill to another
     * @param clientBill - the bill from which the money go
     * @param exceptCard - the card of bill which except money
     * @param payment - money which need to send
     */
    void makePayment(Bill clientBill, Card exceptCard, Double payment);

    void blockBill(Bill bill);
}
