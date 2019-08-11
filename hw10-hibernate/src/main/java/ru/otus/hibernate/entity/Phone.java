package ru.otus.hibernate.entity;

import javax.persistence.*;

/**
 * Created by Alexandr Byankin on 07.08.2019
 */
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "number", length = 20, nullable = false)
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
