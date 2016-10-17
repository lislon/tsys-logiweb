/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;

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

    E find(int key);

    E findOneOrFail(int key) throws EntityNotFoundException;

    /**
     * @param id Entity identifier
     * @throws EntityNotFoundException when id is not found
     */
    void delete(int id) throws EntityNotFoundException;

}
