package com.epam.service;

import com.epam.model.Card;
import com.epam.model.User;

/**
 * Created by fg on 7/27/2016.
 */
public interface CardService {
    String DELETE = "delete";
    String UN_DELETE = "undelete";
    String BLOCK = "block";
    String UN_BLOCK = "unblock";

    /**
     * Make next action with a card.
     * @param actionAndCardId :
     * @see #DELETE - delete the card
     * @see #UN_DELETE - restore the card
     * @see #BLOCK - block the card
     * @see #UN_BLOCK - activate the card
     */
    void doAction(String actionAndCardId, User owner);

    /**
     * Block card by its id
     * @param cardId value of id
     */
    void blockCard(int cardId);

    /**
     * Activate a card
     * @param cardId value of card id
     */
    void unBlockCard(int cardId);

    /**
     * Restore a card
     * @param cardId value of card id
     */
    void restoreCard(int cardId);

    /**
     * Delete a card
     * @param cardId value of card id
     */
    void deleteCard(int cardId);

    /**
     * Find a card by its name
     * @param name of the card
     * @return the card
     */
    Card findByName(String name);
}
