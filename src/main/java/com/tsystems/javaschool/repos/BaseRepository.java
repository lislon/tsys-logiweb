package com.tsystems.javaschool.repos;

import com.tsystems.javaschool.helper.MyEmFactory;

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

    BaseRepository(Class<T> type) {
        classType = type;
    }

    protected EntityManager createEm() {
        return MyEmFactory.createEntityManager();
    }

    public void add(T entity) {
        EntityManager entityManager = MyEmFactory.createEntityManager();
        entityManager.persist(entity);
        entityManager.close();
    }

    public List<T> findAll() {

        CriteriaBuilder criteriaBuilder = MyEmFactory.createCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(classType);
        Root<T> from = query.from(classType);

        EntityManager entityManager = MyEmFactory.createEntityManager();
        return entityManager.createQuery(query).getResultList();
    }

}
