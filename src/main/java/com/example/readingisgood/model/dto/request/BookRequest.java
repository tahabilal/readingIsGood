package com.example.readingisgood.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message="book name is required")
    private String name;
    @NotBlank(message="author name is required")
    private String author;
    @NotBlank(message="publish date is required")
    private String publishDate;
    @NotNull(message = "stock value is required")
    @Positive
    private int stock;
    @NotNull(message = "price value is required")
    @Positive
    private double price;
}
