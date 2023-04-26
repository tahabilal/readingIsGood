package com.example.readingisgood.repository;

import com.example.readingisgood.model.domain.OrderBook;
import org.springframework.data.repository.CrudRepository;

public interface OrderBookRepository extends CrudRepository<OrderBook, Long> {
}
