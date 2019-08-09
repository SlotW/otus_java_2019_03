package ru.otus.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Alexandr Byankin on 07.08.2019
 */
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String street;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString(){
        return "Address: [" +
                "id=" + id + " | " +
                "street=" + street + "]";
    }
}
