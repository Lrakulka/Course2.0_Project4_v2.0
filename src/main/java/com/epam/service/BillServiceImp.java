package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fg on 7/27/2016.
 */
@Service
@Transactional
public class BillServiceImp implements BillService {

    @Autowired
    private BillRepository billRepository;

    private static final String DELETE = "delete";
    private static final String UN_DELETE = "undelete";
    private static final String BLOCK = "block";
    private static final String UN_BLOCK = "unblock";
    @Override
    public void doAction(String actionAndBillId) {
        int billId = Integer.valueOf(actionAndBillId.substring(0, actionAndBillId.indexOf("+")));
        actionAndBillId = actionAndBillId.substring(actionAndBillId.indexOf("+") + 1, actionAndBillId.length());
        switch (actionAndBillId) {
            case DELETE : deleteBill(billId);
                    break;
            case UN_DELETE : restoreBill(billId);
                    break;
            case BLOCK : blockBill(billId);
                    break;
            case UN_BLOCK : unBlockBill(billId);
        }
    }

    @Override
    public void blockBill(int billId) {
        Bill bill = billRepository.findById(billId);
        bill.setActive(false);
        billRepository.update(bill);
    }

    @Override
    public void unBlockBill(int billId) {
        Bill bill = billRepository.findById(billId);
        bill.setActive(true);
        billRepository.update(bill);
    }

    @Override
    public void restoreBill(int billId) {
        Bill bill = billRepository.findById(billId);
        bill.setDeleted(false);
        billRepository.update(bill);
    }

    @Override
    public void deleteBill(int billId) {
        Bill bill = billRepository.findById(billId);
        bill.setDeleted(true);
        billRepository.update(bill);
    }

    @Override
    public List<Bill> getAllClientBills(User user) {
        Iterator<Bill> iterator = user.getBills().iterator();
        iterator.forEachRemaining(bill -> {
            if (bill.getDeleted()) {
                iterator.remove();
            } else {
                Iterator<Card> cardIterator = bill.getCards().iterator();
                cardIterator.forEachRemaining(card -> {
                    if (card.getDeleted()) {
                        cardIterator.remove();
                    }
                });
            }
        });
        return new ArrayList<>(user.getBills());
    }

    @Override
    public boolean fillBill(User user, int billId, Double aDouble) {
        // TODO
        return false;
    }

    @Override
    public Bill getClientBill(User user, Integer billId) {
        // TODO
        return null;
    }

    @Override
    public boolean checkPassword(String password) {
        // TODO
        return false;
    }

    @Override
    public void makePayment(Bill clientBill, Card exceptCard, Double payment) {
        // TODO
    }
}
