package com.javatech.inventory_service.service;

import com.javatech.inventory_service.dto.InventoryResponse;
import com.javatech.inventory_service.model.Inventory;
import com.javatech.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode){
       return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
    @Transactional( readOnly = true)
    public List<InventoryResponse> stockCheck(List<String> skuCode){
        List<InventoryResponse> inventoryList = inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()> 0)
                            .build()

                ).toList();

        return inventoryList;
    }


}
