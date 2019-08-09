package ru.otus.hibernate.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexandr Byankin on 22.07.2019
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int age;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString(){
        return "User: [" +
                "id=" + id + " | " +
                "name=" + (name == null ? "null" : name) + " | " +
                "age=" + age + "|" +
                "address=" + address + "|" +
                "phones=[" + phones.stream()
                .map(Phone::toString)
                .reduce((ident, str)->ident + "|" + str)
                .orElse("") + "]" +
                "]";
    }

}
