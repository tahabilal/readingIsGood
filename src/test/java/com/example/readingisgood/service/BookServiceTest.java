package com.example.readingisgood.service;

import com.example.readingisgood.model.domain.Book;
import com.example.readingisgood.model.dto.request.BookRequest;
import com.example.readingisgood.model.dto.request.BookStockUpdateRequest;
import com.example.readingisgood.model.dto.response.BookResponse;
import com.example.readingisgood.model.dto.response.BookStockResponse;
import com.example.readingisgood.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class BookServiceTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void save() {
        BookRequest bookRequest = new BookRequest("name","author","2021-10-05",3,10.1);
        BookService bookService = new BookService(bookRepository);
        BookResponse save = bookService.save(bookRequest);
        assertEquals("name",save.getName());
        assertEquals(3,save.getStock());
        assertEquals(10.1,save.getPrice());
    }

    @Test
    void updateStock() {
        BookService bookService = new BookService(bookRepository);

        Book book = new Book();
        book.setName("book");
        book.setStock(5);

        em.persist(book);

        BookStockUpdateRequest bookStockUpdateRequest = new BookStockUpdateRequest();
        bookStockUpdateRequest.setId(book.getId());
        bookStockUpdateRequest.setChangingStock(10);
        BookStockResponse bookStockResponse = bookService.updateStock(bookStockUpdateRequest);

        assertEquals(5,bookStockResponse.getOldStock());
        assertEquals(10,bookStockResponse.getNewStock());
        assertEquals("book",bookStockResponse.getName());
    }
}