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
    @Column(name = "id")
    private int id;

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
