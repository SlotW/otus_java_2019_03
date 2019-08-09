package ru.otus.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Alexandr Byankin on 07.08.2019
 */
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue
    private Long id;
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return "Phone: [" +
                "id=" + id + " | " +
                "number=" + number + "]";
    }

}
