/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.BaseManager;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class BaseManagerImpl<E, REPO extends CrudRepository<E, Integer>>
        implements BaseManager<E> {

    final protected REPO repo;

    public BaseManagerImpl(REPO repo) {
        this.repo = repo;
    }

    public Iterable<E> findAll() {
        return repo.findAll();
    }

    /**
     * Persist the entity
     *
     * @param entity
     */
    public void save(E entity)
    {
        repo.save(entity);
    }

    /**
     * Retrieve entity by primary key.
     *
     * @param key
     * @return Entity or null when it's not found
     */
    public E find(Integer key)
    {
        return repo.findOne(key);
    }

    /**
     * Retrieve entity by primary key or throw exception if it's not found
     * @param key
     * @return Entity
     * @throws EntityNotFoundException
     */
    public E findOneOrDie(Integer key) throws EntityNotFoundException
    {
        E e = repo.findOne(key);
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
    public boolean delete(Integer id) {
        if (repo.findOne(id) == null) {
            return false;
        }
        repo.delete(id);
        return true;
    }

}
