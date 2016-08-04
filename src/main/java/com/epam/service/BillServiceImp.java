package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by fg on 7/27/2016.
 */
@Service
@Transactional
public class BillServiceImp implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (bill != null) {
            bill.setActive(false);
            billRepository.update(bill);
        }
    }

    @Override
    public void unBlockBill(int billId) {
        Bill bill = billRepository.findById(billId);
        if (bill != null) {
            bill.setActive(true);
            billRepository.update(bill);
        }
    }

    @Override
    public void restoreBill(int billId) {
        Bill bill = billRepository.findById(billId);
        if (bill != null) {
            bill.setDeleted(false);
            billRepository.update(bill);
        }
    }

    @Override
    public void deleteBill(int billId) {
        Bill bill = billRepository.findById(billId);
        if (bill != null) {
            bill.setDeleted(true);
            billRepository.update(bill);
        }
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
        List<Bill> list = new ArrayList<>(user.getBills());
        list.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        return list;
    }

    @Override
    public boolean fillBill(String userEmail, Integer billId, Double money) {
        Bill bill = billRepository.findById(billId);
        if (bill != null && bill.getUser().getEmail().equals(userEmail)) {
            bill.setScore(money + bill.getScore());
            billRepository.update(bill);
            return true;
        }
        return false;
    }

    @Override
    public Bill getClientBill(User user, Integer billId) {
        Bill bill = billRepository.findById(billId);
        if ((bill != null) && (bill.getUser().equals(user))) {
            return bill;
        }
        return null;
    }

    @Override
    public boolean checkPassword(Bill clientBill, String cardId, String password) {
        Card card;
        try {
            card = clientBill.getCards().stream().filter(c -> c.getId()
                    .equals(cardId)).findFirst().get();
        } catch (NoSuchElementException e) {
            return false;
        }
        return card.getPassword().equals(passwordEncoder.encode(password));
    }

    @Override
    public void makePayment(Bill clientBill, Card exceptCard, Double payment) {
        Bill exceptBill = exceptCard.getBill();
        clientBill.setScore(clientBill.getScore() - Math.abs(payment));
        exceptBill.setScore(exceptBill.getScore() + Math.abs(payment));
        billRepository.update(clientBill);
        billRepository.update(exceptBill);
    }
}
