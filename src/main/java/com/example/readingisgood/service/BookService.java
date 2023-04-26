package com.example.readingisgood.service;

import com.example.readingisgood.exception.model.ResourceNotFoundException;
import com.example.readingisgood.model.domain.Book;
import com.example.readingisgood.model.dto.request.BookRequest;
import com.example.readingisgood.model.dto.request.BookStockUpdateRequest;
import com.example.readingisgood.model.dto.response.BookResponse;
import com.example.readingisgood.model.dto.response.BookStockResponse;
import com.example.readingisgood.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse save(BookRequest bookRequest){
        Book book = toBook(bookRequest);
        return toBookResponse(bookRepository.save(book));
    }

    public BookStockResponse updateStock(BookStockUpdateRequest bookStockUpdateRequest){
        Book book = bookRepository.findById(bookStockUpdateRequest.getId()).
                orElseThrow(() -> new ResourceNotFoundException("book not found"));
        BookStockResponse bookStockResponse = toBookStockResponse(book, bookStockUpdateRequest.getChangingStock());
        book.setStock(bookStockUpdateRequest.getChangingStock());
        bookRepository.save(book);
        return bookStockResponse;
    }

    private Book toBook(BookRequest bookRequest){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate publishDate = LocalDate.parse(bookRequest.getPublishDate(),formatter);
        Book book = new Book();
        book.setName(bookRequest.getName());
        book.setPublishDate(publishDate);
        book.setStock(bookRequest.getStock());
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());
        return book;
    }

    private BookResponse toBookResponse(Book book){
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setName(book.getName());
        bookResponse.setStock(book.getStock());
        bookResponse.setPrice(book.getPrice());
        return bookResponse;
    }

    private BookStockResponse toBookStockResponse(Book book, int newStock){
        BookStockResponse bookStockResponse = new BookStockResponse();
        bookStockResponse.setId(book.getId());
        bookStockResponse.setName(book.getName());
        bookStockResponse.setOldStock(book.getStock());
        bookStockResponse.setNewStock(newStock);
        return bookStockResponse;
    }
}
