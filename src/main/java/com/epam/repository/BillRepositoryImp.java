package com.epam.repository;

import com.epam.model.Bill;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 */
@Repository
public class BillRepositoryImp extends AbstractRepository<Bill> implements BillRepository {

    public BillRepositoryImp() {
        super(Bill.class);
    }

    @Override
    public void add(final Bill bill) {
        super.add(bill);
    }

    @Override
    public List<Bill> findAll() {
        return super.findAll("id");
    }

    @Override
    public Bill findById(final int billId) {
        return super.findByProperty("id", billId);
    }

    @Override
    public Bill findByName(final String name) {
        return super.findByProperty("name", name);
    }

    @Override
    public void update(Bill bill) { super.update(bill);}
}
