package com.example.readingisgood.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatisticsResponse {

    private LocalDateTime orderCreateDate;
    private String orderCreateMonth;
    private int orderCreateYear;
    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;
}
