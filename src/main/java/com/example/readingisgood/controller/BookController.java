package com.example.readingisgood.controller;

import com.example.readingisgood.model.dto.request.BookRequest;
import com.example.readingisgood.model.dto.request.BookStockUpdateRequest;
import com.example.readingisgood.model.dto.response.Success;
import com.example.readingisgood.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.readingisgood.util.Constants.BOOK_CREATING_SUCCESS_MESSAGE;
import static com.example.readingisgood.util.Constants.BOOK_STOCK_CHANGE_SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Success> addBook(@Valid @RequestBody BookRequest bookRequest){
        return ResponseEntity.ok(new Success(bookService.save(bookRequest), BOOK_CREATING_SUCCESS_MESSAGE));
    }

    @PutMapping
    public ResponseEntity<Success> updateStock(@Valid @RequestBody BookStockUpdateRequest bookStockUpdateRequest){
        return ResponseEntity.ok(new Success(bookService.updateStock(bookStockUpdateRequest),BOOK_STOCK_CHANGE_SUCCESS_MESSAGE));
    }
}
