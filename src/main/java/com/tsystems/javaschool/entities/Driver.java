package com.tsystems.javaschool.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 * Created by Igor Avdeev on 8/22/16.
 */
@Entity
@Table(name = "drivers")
public class Driver {

    public enum Status { DUTY_DRIVE, DUTY_REST, REST }

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_name")
    @Size(min = 2, max = 64)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 64)
    private String lastName;

    @Column(name = "hours_worked")
    private int hoursWorked;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
