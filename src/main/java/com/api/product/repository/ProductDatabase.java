package com.api.product.repository;
import com.api.product.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;


public class ProductDatabase implements Database{

    private List<Product> products;
    private List<Product> productsPost;

    private List<Product> productsUpdate;
    private final String JSON_FILE_PATH = "src/main/java/com/api/product/repository/products.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ResourceLoader resourceLoader;

    public ProductDatabase(){
        loadProducts();
    }

    public void loadProducts(){
        try {
            products = objectMapper.readValue(new File(JSON_FILE_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        } catch (IOException e) {
            System.out.println("Problem to load JSON.");
            e.printStackTrace();
            products = new ArrayList<>();
        }
    }

    @Override
    public List<String> select(Long id) {
        return null;
    }

    public List<String> selectall() {
        List<String> allproducts = products.stream()
                .map(product -> "Id: " + product.getId() +
                        ", Name: " + product.getName() +
                        ", Brand: " + product.getBrand() +
                        ", Price: " + product.getPrice())
                .collect(Collectors.toList());
        if (allproducts.isEmpty()) {
            allproducts.add("Product not found.");
        }
        return allproducts;
    }

    @Override
    public Long insert(String row) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            productsPost = objectMapper.readValue(row, new TypeReference<List<Product>>() {});
            products.addAll(productsPost);
            System.out.println(products);
            objectMapper.writeValue(new File(JSON_FILE_PATH), products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(Long id, String newRow) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Product productToUpdate = objectMapper.readValue(newRow, Product.class);
            productToUpdate.setId(id);

            //productsUpdate
            products = products.stream()
                    .map(product -> product.getId().equals(id) ? productToUpdate : product)
                    .collect(Collectors.toList());
            objectMapper.writeValue(new File(JSON_FILE_PATH), products);
            return true;
            //products.forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        loadProducts();
        products.removeIf(product -> product.getId().equals(id));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(products.toString());
        //products.forEach(System.out::println);
        return false;
    }
}
