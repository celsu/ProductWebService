package com.api.product.model;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
}
