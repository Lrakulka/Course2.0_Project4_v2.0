package com.epam.repository;

import com.epam.model.Card;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 */
@Repository
public class CardRepositoryImp extends AbstractRepository<Card> implements CardRepository {

    public CardRepositoryImp() {
        super(Card.class);
    }

    @Override
    public void add(final Card card) {
        super.add(card);
    }

    @Override
    public List<Card> findAll() {
        return super.findAll("id");
    }

    @Override
    public Card findById(final int cardId) {
        return super.findByProperty("id", cardId);
    }

    @Override
    public void update(Card card) {
        super.update(card);
    }

    @Override
    public Card findByName(String name) {
        return super.findByProperty("name", name);
    }
}
