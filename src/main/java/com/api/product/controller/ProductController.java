package com.api.product.controller;

import com.api.product.model.Product;
import com.api.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
        return ResponseEntity.ok(ps.select(id));
    }


}
