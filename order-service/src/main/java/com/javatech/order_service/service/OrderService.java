package com.javatech.order_service.service;

import com.javatech.order_service.dto.InventoryResponse;
import com.javatech.order_service.dto.OrderLineItemsDto;
import com.javatech.order_service.dto.OrderRequest;
import com.javatech.order_service.dto.OrderResponse;
import com.javatech.order_service.model.Order;
import com.javatech.order_service.model.OrderLineItems;
import com.javatech.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItems().stream().map(orderLineItemsDto -> mapToDto(orderLineItemsDto, order)).toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItems -> orderLineItems.getSkuCode())
                .toList();


        //Call Inventory service and place a order if stock exist or stock in
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();  //by default its asynch call. now make it synch call with block method
         Boolean allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());
        if(allProductsInStock) {
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product is not in stock, Please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto, Order order) {
          OrderLineItems  orderLineItems = OrderLineItems.builder()
                  .skuCode(orderLineItemsDto.getSkuCode())
                  .price(orderLineItemsDto.getPrice())
                  .quantity(orderLineItemsDto.getQuantity())
                  .order(order)
                  .build();
         return orderLineItems;
    }

    public List<OrderResponse> getAllOrders() {
       List<Order> orderList = orderRepository.findAll();
        return orderList.stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .orderNumber(order.getOrderNumber())
                        .orderLineItemsList(
                                order.getOrderLineItemsList().stream()
                                        .map(item -> OrderLineItemsDto.builder()
                                                .id(item.getId())
                                                .skuCode(item.getSkuCode())
                                                .price(item.getPrice())
                                                .quantity(item.getQuantity())
                                                .build()
                                        )
                                        .toList()
                        )
                        .build()
                )
                .toList();
    }
}
