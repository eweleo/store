package com.example.Store.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Inventory extends AbstractEntity {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private LocalDateTime dateTime;

    public Inventory(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.dateTime = LocalDateTime.now();

    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.dateTime = LocalDateTime.now();
    }

    public Inventory(Product product) {
        this.product = product;
        this.quantity = 100;
    }

    public void setProduct(int id) {
        this.product.setId(id);
    }


}
