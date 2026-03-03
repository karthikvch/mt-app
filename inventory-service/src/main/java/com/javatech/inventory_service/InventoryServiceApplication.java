package com.javatech.inventory_service;

import com.javatech.inventory_service.model.Inventory;
import com.javatech.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository  inventoryRepository){
	    	return args -> {
				Inventory inventory = new Inventory();
				inventory.setSkuCode("Iphone_IN");
				inventory.setQuantity(95);

				Inventory inventory2 = new Inventory();
				inventory2.setSkuCode("miromax");
				inventory2.setQuantity(300);

				Inventory inventor3 = new Inventory();
				inventor3.setSkuCode("Samsung");
				inventor3.setQuantity(0);

				//inventoryRepository.save(inventory);
				//inventoryRepository.save(inventory2);
				//inventoryRepository.save(inventor3);

			};
	}

}
