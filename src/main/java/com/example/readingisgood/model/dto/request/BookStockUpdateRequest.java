package com.example.readingisgood.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStockUpdateRequest {
    @NotNull(message = "id cant be empty")
    private long id;
    @NotNull(message = "changing stock value is required")
    @Positive
    private int changingStock;
}