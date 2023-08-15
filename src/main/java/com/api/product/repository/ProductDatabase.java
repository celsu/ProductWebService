package com.api.product.repository;

import com.api.product.model.Product;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDatabase implements Database{

    private List<Product> products;
    private final String JSON_FILE_PATH = "src/main/java/com/api/product/repository/products.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public ProductDatabase(){
        loadProducts();
    }

    public void loadProducts(){
        try {
            products = objectMapper.readValue(new File(JSON_FILE_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            /*products.forEach((po -> {
                System.out.println("ID: " + po.getId());
                System.out.println("Name: " + po.getName());
                System.out.println("Brand: " + po.getBrand());
                System.out.println("Price: " + po.getPrice());
            }));*/
        } catch (IOException e) {
            System.out.println("Problem to load JSON.");
            e.printStackTrace();
            products = new ArrayList<>();
        }
    }

    @Override
    public List<String> select(Long id) {
        List<String> productDetails = products.stream()
                .filter(product -> product.getId() == id)
                .map(product -> "Id: " + product.getId() +
                        ", Name: " + product.getName() +
                        ", Brand: " + product.getBrand() +
                        ", Price: " + product.getPrice())
                .collect(Collectors.toList());

        if (productDetails.isEmpty()) {
            productDetails.add("Product not found.");
        }
        return productDetails;
    }

    public List<String> selectall() {
        List<String> productDetails = products.stream()
                .map(product -> "Id: " + product.getId() +
                        ", Name: " + product.getName() +
                        ", Brand: " + product.getBrand() +
                        ", Price: " + product.getPrice())
                .collect(Collectors.toList());

        if (productDetails.isEmpty()) {
            productDetails.add("Product not found.");
        }
        return productDetails;
    }

    @Override
    public Long insert(String row) {
        return null;
    }

    @Override
    public boolean update(Long id, String newRow) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
