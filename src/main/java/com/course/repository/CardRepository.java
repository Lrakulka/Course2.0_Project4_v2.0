package com.course.repository;

import com.course.model.Card;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 * Work with cards in the database
 */
public interface CardRepository {
    /**
     * Add a card to the database
     * @param card which need to add
     */
    void add(final Card card);

    /**
     * Find all cards
     * @return the list of cards
     */
    List<Card> findAll();

    /**
     * Find a card by its id
     * @param cardId value of the card id
     * @return the card
     */
    Card findById(final int cardId);

    /**
     * Update a card to the database
     * @param card which need to update
     */
    void update(Card card);

    /**
     * Find a card by its name
     * @param name of the card
     * @return the card
     */
    Card findByName(String name);
}
