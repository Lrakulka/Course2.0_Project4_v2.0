package com.epam.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by fg on 7/27/2016.
 */
@Service
@Transactional
public class CardServiceImp implements CardService {
    private static final Logger logger = Logger.getLogger(CardServiceImp.class);
}
