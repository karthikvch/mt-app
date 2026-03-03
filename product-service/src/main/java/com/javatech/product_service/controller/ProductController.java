package com.javatech.product_service.controller;

import com.javatech.product_service.dto.ProductRequest;
import com.javatech.product_service.dto.ProductResponse;
import com.javatech.product_service.model.Product;
import com.javatech.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest ){
        log.info("createProduct Behavior is started - {}", productRequest);
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        log.info("GetAllProducts behavior is started");
        return productService.getAllProducts();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest){
        Product update = productService.updateProduct(productRequest);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
