package com.epam.service;

import com.epam.model.Card;
import com.epam.model.User;
import com.epam.repository.CardRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by fg on 7/27/2016.
 * Implementation of card Business logic
 */
@Service
@Transactional
public class CardServiceImp implements CardService {
    private static final Logger LOGGER = Logger.getLogger(CardServiceImp.class);

    private CardRepository cardRepository;

    @Autowired
    public CardServiceImp(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card checkOwner(User owner, Integer cardId) {
        Card card = cardRepository.findById(cardId);
        if (card != null && card.getBill().getUser().equals(owner)) {
            return card;
        }
        return null;
    }

    @Override
    public void doAction(String actionAndCardId, User owner) {
        LOGGER.debug("actionAndCardId=" + actionAndCardId);
        Integer cardId = Integer.valueOf(actionAndCardId.substring(0,
                actionAndCardId.indexOf("+")));
        actionAndCardId = actionAndCardId.substring(actionAndCardId
                .indexOf("+") + 1, actionAndCardId.length());
        if (checkOwner(owner, cardId) != null) {
            switch (actionAndCardId) {
                case DELETE:
                    deleteCard(cardId);
                    break;
                case UN_DELETE:
                    restoreCard(cardId);
                    break;
                case BLOCK:
                    blockCard(cardId);
                    break;
                case UN_BLOCK:
                    unBlockCard(cardId);
            }
        } else {
            LOGGER.warn("actionAndCardId=" + actionAndCardId
                    + " user is not a owner of card");
        }
    }

    @Override
    public void blockCard(Integer cardId) {
        LOGGER.debug("block Card");
        Card card = cardRepository.findById(cardId);
        card.setActive(false);
        cardRepository.update(card);
    }

    @Override
    public void unBlockCard(Integer cardId) {
        LOGGER.debug("unBlock Card");
        Card card = cardRepository.findById(cardId);
        card.setActive(true);
        cardRepository.update(card);
    }

    @Override
    public void restoreCard(Integer cardId) {
        LOGGER.debug("restore Card");
        Card card = cardRepository.findById(cardId);
        card.setDeleted(false);
        cardRepository.update(card);
    }

    @Override
    public void deleteCard(Integer cardId) {
        LOGGER.debug("delete Card");
        Card card = cardRepository.findById(cardId);
        card.setDeleted(true);
        cardRepository.update(card);
    }

    @Override
    public Card findByName(String name) {
        LOGGER.debug("get Card by name");
        return cardRepository.findByName(name);
    }
}
