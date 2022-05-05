package com.example.Store.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
@NoArgsConstructor
@Setter
public class Order extends AbstractEntity {

    @ManyToOne//(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
    private boolean isDone = false;
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ChoiceProduct> choiceProducts = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public Order(boolean isDone) {
        this.isDone = isDone;
        dateTime = LocalDateTime.now();
    }

    public Order(int userId) {
        isDone = false;
        dateTime = LocalDateTime.now();
        user.setId(userId);
    }

    public Order(User user) {
        this.isDone = false;
        dateTime = LocalDateTime.now();
        this.user = user;
    }

    public Order(User user, boolean isDone) {
        this.isDone = isDone;
        dateTime = LocalDateTime.now();
        this.user = user;
    }

    public Order(int userId, boolean isDone) {
        this.isDone = isDone;
        dateTime = LocalDateTime.now();
        user.setId(userId);
    }

    public int getId() {
        return super.getId();
    }

    @JsonIgnore
    public int getUserId() {
        return user.getId();
    }

    public boolean getDone() {
        return this.isDone;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDone(boolean done) {
        isDone = done;
        dateTime = LocalDateTime.now();
    }

    public void setDone() {
        isDone = true;
        dateTime = LocalDateTime.now();
    }


    public List<ChoiceProduct> getChoiceProducts() {
        return choiceProducts;
    }

    public void setChoiceProducts(List<ChoiceProduct> choiceProduct) {
        this.choiceProducts = choiceProduct;
    }

    public void setChoiceProduct(ChoiceProduct choiceProduct) {
        choiceProducts.add(choiceProduct);
    }
}
