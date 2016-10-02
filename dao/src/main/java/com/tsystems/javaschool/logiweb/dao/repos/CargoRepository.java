/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
@Repository
@Service
public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    /**
     * Returns list of cargoes that belongs to order.
     *
     * @param orderId
     * @return
     */
    List<Cargo> findByOrderId(int orderId);
}
