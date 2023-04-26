package com.example.readingisgood.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookRequest {
    @NotNull(message="book name is required")
    private long bookId;
    @NotNull(message = "book amount value is required")
    @Positive
    private int amount;
}
