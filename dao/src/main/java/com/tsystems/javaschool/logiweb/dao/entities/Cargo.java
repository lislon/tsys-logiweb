/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "cargoes")
@ToString(exclude = "order")
@Data
@NoArgsConstructor
public class Cargo {
    public enum Status { PREPARED, SHIPPED, DELIVERED }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(min = 1)
    private String name;

    @Column(name = "title")
    @NotNull
    @Size(min = 1)
    private String title;

    @Column(name = "weight")
    @Min(1)
    private Integer weight;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cargo))
            return false;
        if (obj == this)
            return true;

        Cargo rhs = (Cargo) obj;
        return (new EqualsBuilder())
                .append(name, rhs.name)
                .build();
    }

}
