package com.example.Store.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.Objects;


@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(name = "uniqueName", columnNames = {"name"})})
@NoArgsConstructor
@Data

public class Product extends AbstractEntity {

    @NotBlank(message = "Product's name must not be empty")
    private String name;

    public Product(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
