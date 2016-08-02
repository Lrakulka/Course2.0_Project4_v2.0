package com.epam.service;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.repository.BillRepository;
import com.epam.repository.CardRepository;
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
public class CardServiceImp implements CardService {
    @Autowired
    private CardRepository cardRepository;

    private static final String DELETE = "delete";
    private static final String UN_DELETE = "undelete";
    private static final String BLOCK = "block";
    private static final String UN_BLOCK = "unblock";
    @Override
    public void doAction(String actionAndCardId) {
        int cardId = Integer.valueOf(actionAndCardId.substring(0, actionAndCardId.indexOf("+")));
        actionAndCardId = actionAndCardId.substring(actionAndCardId.indexOf("+") + 1, actionAndCardId.length());
        switch (actionAndCardId) {
            case DELETE : deleteCard(cardId);
                break;
            case UN_DELETE : restoreCard(cardId);
                break;
            case BLOCK : blockCard(cardId);
                break;
            case UN_BLOCK : unBlockCard(cardId);
        }
    }

    @Override
    public void blockCard(int billId) {
        Card card = cardRepository.findById(billId);
        card.setActive(false);
        cardRepository.update(card);
    }

    @Override
    public void unBlockCard(int billId) {
        Card card = cardRepository.findById(billId);
        card.setActive(true);
        cardRepository.update(card);
    }

    @Override
    public void restoreCard(int billId) {
        Card card = cardRepository.findById(billId);
        card.setDeleted(false);
        cardRepository.update(card);
    }

    @Override
    public void deleteCard(int billId) {
        Card card = cardRepository.findById(billId);
        card.setDeleted(true);
        cardRepository.update(card);
    }
}
