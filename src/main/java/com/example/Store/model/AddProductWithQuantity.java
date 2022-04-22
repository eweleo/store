package com.example.Store.model;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
public class AddProductWithQuantity {
    @NotBlank
    private String name;

    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public AddProductWithQuantity(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
