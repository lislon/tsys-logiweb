/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Repository
@Service
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    /**
     * Searchs all drivers who are located in given city, free of duty and worked no more them `maxHours` hours in current month.
     * @param cityId City, where we search drivers
     * @param maxHours Maximum hours worked in current month.
     * @return
     */
    @Query("from Driver d " +
            " where d.city.id = :cityId and d.status = 'REST' and d.hoursWorked < :maxHours" +
            " and not exists (from Order o join o.drivers od where od = d and o.isCompleted = false )")
    List<Driver> findFreeDriversInCity(@Param("cityId") int cityId, @Param("maxHours") int maxHours);

    @Query("from Driver d where d.order.id = ?1")
    Driver findByOrderId(int orderId);

}
