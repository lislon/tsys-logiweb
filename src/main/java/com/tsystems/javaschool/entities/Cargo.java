package com.tsystems.javaschool.entities;

import javax.persistence.*;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "cargoes", schema = "logiweb")
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
