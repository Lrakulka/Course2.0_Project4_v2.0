package com.epam.service;

import com.epam.model.Card;
import com.epam.model.User;

/**
 * Created by fg on 7/27/2016.
 * Business logic for card
 */
public interface CardService {
    String DELETE = "delete";
    String UN_DELETE = "undelete";
    String BLOCK = "block";
    String UN_BLOCK = "unblock";

    /**
     * Check owner of card
     * @param owner - user which must be the owner of the card
     * @param cardId - id of card
     * @return the card if user is owner or null if not
     */
    Card checkOwner(User owner, Integer cardId);

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
    void blockCard(Integer cardId);

    /**
     * Activate a card
     * @param cardId value of card id
     */
    void unBlockCard(Integer cardId);

    /**
     * Restore a card
     * @param cardId value of card id
     */
    void restoreCard(Integer cardId);

    /**
     * Delete a card
     * @param cardId value of card id
     */
    void deleteCard(Integer cardId);

    /**
     * Find a card by its name
     * @param name of the card
     * @return the card
     */
    Card findByName(String name);
}
