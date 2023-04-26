package com.example.readingisgood.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookResponse {
    private long orderBookId;
    private long bookId;
    private String bookName;
    private int amount;
}
