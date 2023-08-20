package com.api.product.service;

import com.api.product.exception.ProductNotFoundException;
import com.api.product.model.Product;
import com.api.product.repository.ProductDatabase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private List<Product> products;
    private static ObjectMapper objectMapper = new ObjectMapper();

    ProductDatabase pd = new ProductDatabase();


    public void select() {
        Long i= 0L;

    }

    public List<Product> select(Long idSearch){
        List<String> productsString = pd.selectall();
        List<Product> productList = new ArrayList<>();

        for (String productString : productsString) {
            String[] parts = productString.split(", ");
            Long id = null;
            String name = null;
            String brand = null;
            double price = 0.0;

            for (String part : parts) {
                String[] keyValue = part.split(": ");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    switch (key) {
                        case "Id":
                            id = Long.parseLong(value);
                            break;
                        case "Name":
                            name = value;
                            break;
                        case "Brand":
                            brand = value;
                            break;
                        case "Price":
                            price = Double.parseDouble(value);
                            break;
                    }
                }
            }

            if (id != null && name != null && brand != null) {
                Product product = new Product();
                product.setId(id);
                product.setName(name);
                product.setBrand(brand);
                product.setPrice(price);
                productList.add(product);
            }
        }
        productList.forEach(product -> System.out.println("Product Details: " + product));
        //productList.forEach(System.out::println);

        return productList;
        /*if(id== -1){
            List<String> products = pd.selectall();
            products.forEach(System.out::println);
            List<Product> productList = products.stream()
                    .map(this::convertStringToProduct)
                    .collect(Collectors.toList());
            return productList;
        }else{
            List<String> productDetails = pd.select(id);
            if (productDetails == null || productDetails.isEmpty()) {
                throw new ProductNotFoundException("Product with ID " + id + " not found.");
            } else {
                List<Product> productList = productDetails.stream()
                        .map(this::convertStringToProduct)
                        .collect(Collectors.toList());
                return productList;
            }
        }*/
    }

    /*private Product convertStringToProduct(String productString) {
        try {
            String[] parts = productString.split(", ");

            int id = Integer.parseInt(parts[0].substring(4)); // Remove "Id: " prefix
            String name = parts[1].substring(6); // Remove "Name: " prefix
            String brand = parts[2].substring(7); // Remove "Brand: " prefix
            double price = Double.parseDouble(parts[3].substring(8)); // Remove "Price: " prefix


            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setBrand(brand);
            product.setPrice(price);

            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public void insert(String po) {
        pd.insert(po);
    }
}
