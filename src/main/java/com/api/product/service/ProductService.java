package com.api.product.service;

import com.api.product.repository.ProductDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    ProductDatabase pd = new ProductDatabase();


    public void select() {
        Long i= 0L;

    }

    public List<String> select(Long id){
        if(id== -1){
            List<String> products = pd.selectall(id);
            return products;
        }else{
            List<String> productDetails = pd.select(id);
            return productDetails;
        }
    }
}
