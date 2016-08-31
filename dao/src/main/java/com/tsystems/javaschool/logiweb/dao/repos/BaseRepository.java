/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public abstract class BaseRepository<T> {

    private Class<T> classType;
    protected final EntityManager em;

    BaseRepository(Class<T> type, EntityManager em) {
        classType = type;
        this.em = em;
    }

    public void add(T entity) {
        em.persist(entity);
    }

    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(classType);
        Root<T> from = query.from(classType);

        return em.createQuery(query).getResultList();
    }

    public T find(Object key) {
        return em.find(classType, key);
    }

}
