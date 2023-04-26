package com.example.readingisgood.repository;

import com.example.readingisgood.model.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Book findBookByName(String name);
}
