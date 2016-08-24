package com.tsystems.javaschool.entities;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "orders", schema = "logiweb")
public class Order {
    private int id;
    private Timestamp dateCreated;
    private Timestamp dateCompleted;
    private byte isCompleted;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_created")
    @UpdateTimestamp
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "date_completed")
    @Null
    public Timestamp getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Timestamp dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    @Basic
    @Column(name = "is_completed")
    public byte getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(byte isCompleted) {
        this.isCompleted = isCompleted;
    }
}
