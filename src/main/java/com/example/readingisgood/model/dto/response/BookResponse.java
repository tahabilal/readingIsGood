package com.example.readingisgood.model.dto.response;

import lombok.Data;

@Data
public class BookResponse {
    private long id;
    private String name;
    private int stock;
    private double price;
}
