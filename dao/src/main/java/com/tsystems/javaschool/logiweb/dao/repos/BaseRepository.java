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
 * Provides basic methods for working with raw Entities.
 */
public abstract class BaseRepository<T> {

    private Class<T> classType;
    protected final EntityManager em;

    BaseRepository(Class<T> type, EntityManager em) {
        classType = type;
        this.em = em;
    }

    /**
     * Creates (persist) the entity into database.
     *
     * @param entity
     */
    public void create(T entity) {
        em.persist(entity);
    }

    /**
     * Updates the entity (Key must be set).
     *
     * @param entity
     */
    public void update(T entity) {
        em.merge(entity);
    }

    public boolean delete(Object key) {
        T entity = find(key);
        if (entity != null) {
            em.remove(entity);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all values of entity from database.
     *
     * @return
     */
    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(classType);
        Root<T> from = query.from(classType);

        return em.createQuery(query).getResultList();
    }

    /**
     * Searchs single value by primary key.
     *
     * @param key Primary key
     * @return Enity or null when entity not found.
     */
    public T find(Object key) {
        return em.find(classType, key);
    }
}
