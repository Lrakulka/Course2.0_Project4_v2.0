package com.epam.service;

import com.epam.model.Card;

/**
 * Created by fg on 7/27/2016.
 */
public interface CardService {
    void doAction(String actionAndCardId);

    void blockCard(int billId);

    void unBlockCard(int billId);

    void restoreCard(int billId);

    void deleteCard(int billId);

    Card findByName(String name);
}
