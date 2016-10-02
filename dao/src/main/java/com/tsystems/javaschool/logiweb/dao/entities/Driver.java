/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;


/**
 * Created by Igor Avdeev on 8/22/16.
 */
@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
public class Driver {

    /**
     * Maximum duty hours per month.
     */
    public static final int MONTH_DUTY_HOURS = 176;

    /**
     * How many hours driver can work per day.
     */
    public static final int LIMIT_HOURS_DAY_DRIVE = 8;

    public enum Status {
        /**
         * Drivier is on duty and driving.
         */
        DUTY_DRIVE,
        /**
         * Drivier is on duty and resting.
         */
        DUTY_REST,
        /**
         * Driver is not on duty
         */
        REST
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    @Size(min = 1, max = 64)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 1, max = 64)
    private String lastName;

    @Column(name = "personal_code")
    @Size(min = 1, max = 64)
    private String personalCode;

    @Column(name = "hours_worked")
    private int hoursWorked;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    /**
     * Current driver order or null if non assigned
     */
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order currentOrder;

    /**
     * Date of beginning current duty
     */
    @Column(name = "duty_since")
    private Date lastDutySince;


    @Override
    public int hashCode() {
        return (new HashCodeBuilder())
                .append(personalCode)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Driver))
            return false;
        if (obj == this)
            return true;

        Driver rhs = (Driver) obj;
        return (new EqualsBuilder())
                .append(personalCode, rhs.personalCode)
                .build();
    }

}
