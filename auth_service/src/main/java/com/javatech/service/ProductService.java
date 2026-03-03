package com.javatech.service;


import com.javatech.dto.ProductDto;
import com.javatech.dto.ProductRequest;
import com.javatech.model.Product;
import com.javatech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public void addProduct(ProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice()).build();
        productRepository.save(product);
    }

    public List<ProductDto> getProductList() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = productList.stream().map(p -> mapToProductDTO(p)).collect(Collectors.toList());
        return productDtoList;
    }

    private ProductDto mapToProductDTO(Product p) {
        ProductDto productDto = new ProductDto();
        productDto.setName(p.getName());
        productDto.setPrice(p.getPrice());
        return productDto;
    }
}
