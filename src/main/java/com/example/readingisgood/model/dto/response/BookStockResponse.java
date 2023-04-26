package com.example.readingisgood.model.dto.response;

import lombok.Data;

@Data
public class BookStockResponse {
    private long id;
    private String name;
    private int oldStock;
    private int newStock;
}
