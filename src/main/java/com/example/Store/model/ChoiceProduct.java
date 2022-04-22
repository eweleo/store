package com.example.Store.model;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "choice_products")
@ToString

@NoArgsConstructor
public class ChoiceProduct extends AbstractEntity {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    int quantity;


    public ChoiceProduct(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }


    public void setOrder(Order order) {
        this.order = order;
    }

    public int getOrderId() {
        return order.getId();
    }

    public int getProductId() {
        return product.getId();
    }

    public void setProduct(Product productId) {
        this.product = productId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
