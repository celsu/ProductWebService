package com.api.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
    private Long id;
    private String name;
    private String brand;
    private double price;
}
