/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.BaseManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public abstract class BaseManagerImpl<E, REPO extends CrudRepository<E, Integer>>
        implements BaseManager<E> {

    final protected REPO repo;

    public BaseManagerImpl(REPO repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public E find(int key)
    {
        return repo.findOne(key);
    }

    /**
     * Retrieve entity by primary key or throw exception if it's not found
     * @param key
     * @return Entity
     * @throws EntityNotFoundException
     */
    @Transactional(readOnly = true)
    public E findOneOrFail(int key) throws EntityNotFoundException
    {
        E e = repo.findOne(key);
        if (e == null) {
            // gets class name
            // unchecked cast: Type argument is guarantied to be same as E, because it is defined in class type argument
            @SuppressWarnings("uncheked")
            Class<E> eClass = (Class<E>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
    public void delete(int id) throws EntityNotFoundException {
        if (repo.findOne(id) == null) {
            Class<E> eClass = (Class<E>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            throw new EntityNotFoundException("Entity " + eClass.getSimpleName() + " with id = " + id + " is not found");
        }
        repo.delete(id);
    }

}
