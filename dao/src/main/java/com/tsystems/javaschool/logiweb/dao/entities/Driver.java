/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * Created by Igor Avdeev on 8/22/16.
 */
@Entity
@Table(name = "drivers")
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
    private Order order;

    /**
     * Date of beginning current duty
     */
    @Column(name = "duty_since")
    private Date lastDutySince;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


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

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalCode='" + personalCode + '\'' +
                ", hoursWorked=" + hoursWorked +
                ", status=" + status +
                ", city=" + city +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order currentOrder) {
        this.order = currentOrder;
    }

    public Date getLastDutySince() {
        return lastDutySince;
    }

    public void setLastDutySince(Date lastDutySince) {
        this.lastDutySince = lastDutySince;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
