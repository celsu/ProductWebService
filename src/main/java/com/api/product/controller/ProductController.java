package com.api.product.controller;

import com.api.product.model.Product;
import com.api.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService ps;

    @Autowired
    public ProductController(ProductService ps) {
        this.ps = ps;
    }

    @GetMapping("/{id}")
    public ResponseEntity getAllProducts(@PathVariable("id") Long id){

        List<Product> products = ps.select(id);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity postProduct (@RequestBody String po){
        try {
            ps.insert(po);
            return ResponseEntity.ok("Created");

        }catch (Exception e){
            return (ResponseEntity) ResponseEntity.badRequest();

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct (@PathVariable("id") Long id){
        ps.delete(id);
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct (@PathVariable("id") Long id, @RequestBody String po){

            ps.update(id, po);
            return null;
    }
}
