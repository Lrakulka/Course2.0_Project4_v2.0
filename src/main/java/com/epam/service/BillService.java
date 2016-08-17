package com.epam.service;

import com.epam.model.Bill;
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
     * Make next action with a bill.
     * @param actionAndBillId :
     * @see #DELETE - delete the bill
     * @see #UN_DELETE - restore the bill
     * @see #BLOCK - block the bill
     * @see #UN_BLOCK - activate the bill
     */
    void doAction(String actionAndBillId);

    /**
     * Block bill by its id
     * @param billId id of the bill
     */
    void blockBill(Integer billId);

    /**
     * Activate a bill
     * @param billId value of bill id
     */
    void unBlockBill(Integer billId);

    /**
     * Restore a bill
     * @param billId value of bill id
     */
    void restoreBill(Integer billId);

    /**
     * Delete a bill
     * @param billId value of bill id
     */
    void deleteBill(Integer billId);

    /**
     * Return all bills of the user
     * @param user which bills needed
     * @return the list of users
     */
    List<Bill> getAllClientBills(User user);

    /**
     * Fill a bill with money
     * @param billId - id of the bill
     * @param money which need to add to the bill
     * @return true if operation successful, false if not
     */
    boolean fillBill(Integer billId, Double money);

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
     * @return true id check passed, false if not
     */
    boolean checkCardPass(Bill clientBill, Integer nativeCardId, String password);

    /**
     * Sent money to from one bill to another
     * @param clientBillId - id of the bill from which the money go
     * @param exceptBillId - if of the bill which except money
     * @param payment - money which need to send
     * return
     */
    boolean makePayment(Integer clientBillId, Integer exceptBillId, Double payment);

    /**
     * Check if the bill is the owner property
     * @param ownerEmail email of the owner
     * @param billId id of the bill
     * @return the bill if the bill id property of owner
     */
    Bill checkOwnerBill(String ownerEmail, Integer billId);
}
