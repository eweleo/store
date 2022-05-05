package com.example.Store.entities;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends AbstractEntity {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @OneToMany(mappedBy = "user"/*, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}*/)
    List<Order> orders = new ArrayList<>();

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
