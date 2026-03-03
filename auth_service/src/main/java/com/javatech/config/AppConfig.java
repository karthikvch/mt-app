package com.javatech.config;


import com.javatech.service.MyCustomClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MyCustomClient myCustomClient(){
        System.out.println("➡ @Bean method executed: Creating MyCustomClient");
        return new MyCustomClient();
    }

}

