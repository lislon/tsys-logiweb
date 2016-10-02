/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public interface BaseManager<E> {

    Iterable<E> findAll();

    /**
     * Persist the entity
     * @param entity
     */
    void save(E entity);

    E find(Integer key);

    E findOneOrDie(Integer key) throws EntityNotFoundException;

    /**
     * @param id Entity identifier
     * @return true when entity was deleted, false when entity was not found
     */
    boolean delete(Integer id);

}
