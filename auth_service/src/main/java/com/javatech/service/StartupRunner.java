package com.javatech.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class StartupRunner implements CommandLineRunner {

    private final MyCustomClient myCustomClient;

    public StartupRunner(MyCustomClient myCustomClient){
        this.myCustomClient = myCustomClient;
    }

    @Override
    public void run(String... args) throws Exception {
         System.out.println("we are in StartupRunner Class : "+  myCustomClient.sayHello());


    }
}
