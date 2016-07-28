package com.epam.service;

import com.epam.controller.MainController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by fg on 7/27/2016.
 */
@Service
@Transactional
public class BillServiceImp implements BillService {
    private static final Logger logger = Logger.getLogger(BillServiceImp.class);
}
