package com.epam.service;

/**
 * Created by fg on 7/27/2016.
 */
public interface BillService {

    void blockBill(int billId);

    void doAction(String actionAndBillId);

    void unBlockBill(int billId);

    void restoreBill(int billId);

    void deleteBill(int billId);
}
