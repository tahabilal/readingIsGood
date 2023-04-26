package com.example.readingisgood.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private long orderId;
    private long customerId;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private List<OrderBookResponse> orderBookResponseList;
}
