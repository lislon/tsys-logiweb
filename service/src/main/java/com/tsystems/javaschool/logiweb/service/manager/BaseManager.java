/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.repos.BaseRepository;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;

import java.util.List;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public interface BaseManager<E> {

    List<E> findAll();

    /**
     * Persist the entity
     * @param entity
     */
    void save(E entity);

    E find(int key);

    E findOne(int key) throws EntityNotFoundException;

    /**
     * @param id Entity identifier
     * @return true when entity was deleted, false when entity was not found
     */
    boolean delete(int id);

}
