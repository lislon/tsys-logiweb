/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import javax.persistence.*;
import lombok.*;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "cargoes")
@Data
@NoArgsConstructor
public class Cargo {
    public enum Status { PREPARED, SHIPPED, DELIVERED }


    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "status")
    @Enumerated
    private Status status;
}
