package com.javatech.service;

import com.javatech.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRedis {

    @Autowired
    private RedisCacheService redisCacheService;


    public void TestRed() {

        ProductDto productDto1 = ProductDto.builder()
                .id(101L).price(120.0).name("Apple").build();


        ProductDto productDto2 = ProductDto.builder()
                .id(101L).price(120.0).name("Apple").build();
        List<ProductDto> productDtoList = Arrays.asList(productDto1, productDto2);

        redisCacheService.put("dto_key", productDto1, Duration.ofMinutes(10));


        redisCacheService.put("dto_keym", productDtoList, Duration.ofMinutes(10));


        List<ProductDto> response = redisCacheService.getList("dto_keym", ProductDto.class);



    }

}
