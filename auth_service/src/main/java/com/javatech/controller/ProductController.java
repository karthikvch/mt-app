package com.javatech.controller;


import com.javatech.dto.ProductDto;
import com.javatech.dto.ProductRequest;
import com.javatech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/health")
    public String healthCheck(){
        return "product service is up and running";
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductRequest request){
        productService.addProduct(request);
        return "product saved successfully";
    }
    @GetMapping("/get")
    public List<ProductDto>  getProducts(){
        return productService.getProductList();
    }
}