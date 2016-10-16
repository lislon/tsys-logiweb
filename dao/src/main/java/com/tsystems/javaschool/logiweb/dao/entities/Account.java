package com.tsystems.javaschool.logiweb.dao.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Account {

    public enum UserRole {
        ROLE_MANAGER,
        ROLE_DRIVER
    }

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "password", nullable = false)
    private String passwordMd5;

    @OneToOne
    @Column(name = "driver_id")
    private Driver driver;

}