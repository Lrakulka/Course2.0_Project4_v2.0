package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.repository.BillRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by fg on 7/27/2016.
 * Implementation of BillService
 */
@Service
@Transactional
public class BillServiceImp implements BillService {
    private static final Logger LOGGER = Logger.getLogger(BillServiceImp.class);

    private BillRepository billRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public BillServiceImp(BillRepository billRepository, PasswordEncoder passwordEncoder) {
        this.billRepository = billRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void doAction(String actionAndBillId) {
        LOGGER.warn("actionAndBillId=" + actionAndBillId);
        Integer billId = Integer.valueOf(actionAndBillId.substring(0,
                actionAndBillId.indexOf("+")));
        actionAndBillId = actionAndBillId.substring(actionAndBillId.indexOf("+") + 1,
                actionAndBillId.length());
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
    public void blockBill(Integer billId) {
        Bill bill = billRepository.findById(billId);
        if (bill != null) {
            bill.setActive(false);
            bill.getCards().forEach(card -> card.setActive(false));
            billRepository.update(bill);
        }
    }

    @Override
    public void unBlockBill(Integer billId) {
        Bill bill = billRepository.findById(billId);
        if (bill != null) {
            LOGGER.warn("unBlockBill failed");
            bill.setActive(true);
            billRepository.update(bill);
        }
    }

    @Override
    public void restoreBill(Integer billId) {
        Bill bill = billRepository.findById(billId);
        if (bill != null) {
            LOGGER.warn("restoreBill failed");
            bill.setDeleted(false);
            bill.getCards().forEach(card -> card.setDeleted(false));
            billRepository.update(bill);
        }
    }

    @Override
    public void deleteBill(Integer billId) {
        Bill bill = billRepository.findById(billId);
        if (bill != null) {
            LOGGER.warn("deleteBill failed");
            bill.setDeleted(true);
            bill.getCards().forEach(card -> card.setDeleted(true));
            billRepository.update(bill);
        }
    }

    @Override
    public List<Bill> getAllClientBills(User user) {
        LOGGER.debug("All client bill");
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
    public boolean fillBill(Integer billId, Double money) {
        LOGGER.debug("Fill bill");
        Bill bill = billRepository.findById(billId);
        if (bill != null && bill.getActive() && !bill.getDeleted()) {
            bill.setScore(money + bill.getScore());
            billRepository.update(bill);
            return true;
        }
        LOGGER.warn("Fill bill failed");
        return false;
    }

    @Override
    public Bill getClientBill(User user, Integer billId) {
        LOGGER.debug("client bill");
        Bill bill = billRepository.findById(billId);
        if ((bill != null) && (bill.getUser().equals(user))) {
            return bill;
        }
        return null;
    }

    @Override
    public boolean makePayment(Integer clientBillId, Integer exceptBillId, Double payment) {
        LOGGER.debug("make Payment");
        Bill exceptBill = billRepository.findById(exceptBillId);
        Bill clientBill = billRepository.findById(clientBillId);
        if ((exceptBill != null) && (clientBill != null)
                && (clientBill.getScore() >= payment) && (payment > 0)
                && exceptBill.getActive() && clientBill.getActive()
                && !exceptBill.getDeleted() && !exceptBill.getDeleted()) {
            clientBill.setScore(clientBill.getScore() - Math.abs(payment));
            exceptBill.setScore(exceptBill.getScore() + Math.abs(payment));
            billRepository.update(clientBill);
            billRepository.update(exceptBill);
            return true;
        }
        return false;
    }

    @Override
    public Bill checkOwnerBill(String ownerEmail, Integer billId) {
        LOGGER.debug("check owner of the bill");
        Bill bill = billRepository.findById(billId);
        if (bill != null && bill.getUser().getActive() &&
                bill.getUser().getEmail().equals(ownerEmail)) {
            return bill;
        }
        LOGGER.warn("bill is not belong to owner");
        return null;
    }

    @Override
    public boolean checkCardPass(Bill clientBill, Integer cardId, String password) {
        LOGGER.debug("check card password");
        Optional<Card> cardOptional = clientBill.getCards().stream().filter(c -> c.getId()
                    .equals(cardId)).findFirst();
        return cardOptional.isPresent() && cardOptional.get()
                .getPassword().equals(passwordEncoder.encode(password));
    }
}
