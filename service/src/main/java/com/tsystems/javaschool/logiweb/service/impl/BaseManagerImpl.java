/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.repos.BaseRepository;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.BaseManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class BaseManagerImpl<E, REPO extends BaseRepository<E>>
        implements BaseManager<E> {

    final protected REPO repo;
    final protected ServiceContainer services;

    public BaseManagerImpl(REPO repo, ServiceContainer services) {
        this.repo = repo;
        this.services = services;
    }

    public List<E> findAll() {
        return repo.findAll();
    }

    /**
     * Persist the entity
     *
     * @param entity
     */
    public void save(E entity)
    {
        repo.create(entity);
    }

    /**
     * Retrieve entity by primary key.
     *
     * @param key
     * @return Entity or null when it's not found
     */
    public E find(int key)
    {
        return repo.find(key);
    }

    /**
     * Retrieve entity by primary key or throw exception if it's not found
     * @param key
     * @return Entity
     * @throws EntityNotFoundException
     */
    public E findOne(int key) throws EntityNotFoundException
    {
        E e = repo.find(key);
        if (e == null) {
            // gets class name
            Class<E> eClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            throw new EntityNotFoundException("Entity " + eClass.getSimpleName() + " with id = " + key + " is not found");
        }
        return e;
    }

    /**
     * Deletes an entity by primary key.
     *
     * @param id Entity identifier
     * @return true when entity was deleted, false when entity was not found
     */
    public boolean delete(int id) {
        return repo.delete(id);
    }

}
