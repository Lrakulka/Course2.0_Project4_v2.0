package com.epam.repository;

/**
 * Created by fg on 7/24/2016.
 */

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractRepository<T> {

    private final Class<T> clazz;

    @Autowired
    protected SessionFactory sessionFactory;

    public AbstractRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected List<T> findAll(String sortColumn) {
        Criteria criteria = getCriteria();
        if (sortColumn != null) {
            criteria.addOrder(Order.asc(sortColumn));
        }
        return criteria.list();
    }

    protected void add(final T object) {
        sessionFactory.getCurrentSession().save(object);
    }

    protected void update(final T object){
        sessionFactory.getCurrentSession().update(object);
    }

    protected T findByProperty(final String property, final Object value) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(property, value));
        return (T) criteria.uniqueResult();
    }

    protected List<T> findListByProperty(final String property, final Object value) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(property, value));
        return criteria.list();
    }

    protected Criteria getCriteria() {
        return sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
}
}