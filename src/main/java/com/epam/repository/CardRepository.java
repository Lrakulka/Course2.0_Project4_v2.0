package com.epam.repository;

import com.epam.model.Card;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 */
public interface CardRepository {
    void add(final Card card);

    List<Card> findAll();

    Card findById(final int cardId);

    void update(Card card);

    Card findByName(String name);
}
