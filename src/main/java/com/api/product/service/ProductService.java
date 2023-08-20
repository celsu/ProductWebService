package com.api.product.service;

import com.api.product.model.Product;
import com.api.product.repository.ProductDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


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

        productList = convertStringToProduct(productsString);

        if(idSearch == -1){
            return productList;
        }else{
            return productList.stream()
                    .filter(product -> product.getId().equals(idSearch)).toList();
        }
        //productList.forEach(product -> System.out.println("Product Details: " + product));
    }

    private List<Product> convertStringToProduct(List<String> productsString) {
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
        return productList;
    }


    public void insert(String po) {
        pd.insert(po);
    }

    public void delete(Long id) {
        pd.delete(id);

    }

    public void update(Long id, String po) {
        pd.update(id,po);
    }
}