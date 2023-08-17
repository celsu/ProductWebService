package com.api.product.repository;
import com.api.product.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ProductDatabase implements Database{

    private List<Product> products;
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
        //System.out.println("LINHA BODY: " + row);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Lê o conteúdo atual do arquivo
            List<Product> currentProducts = objectMapper.readValue(new File(JSON_FILE_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

            System.out.println("Product Convertido: "+convertStringToProduct(row));
            // Adiciona o novo objeto Product à lista
            currentProducts.add(convertStringToProduct(row));

            // Escreve a lista atualizada de volta no arquivo
            objectMapper.writeValue(new File(JSON_FILE_PATH), currentProducts);

            System.out.println("Product inserted successfully.");

        } catch (IOException e) {
            System.out.println("Failed to insert product.");
            e.printStackTrace();
        }

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

        public static Product convertStringToProduct(String jsonString) {
            try {
                System.out.println(jsonString);
                return objectMapper.readValue(jsonString, Product.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
}
