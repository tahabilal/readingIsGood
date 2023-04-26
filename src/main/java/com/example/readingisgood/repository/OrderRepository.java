package com.example.readingisgood.repository;

import com.example.readingisgood.model.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> getOrdersByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Order> getOrdersByCustomer_Id(long id, Pageable pageable);

    List<Order> getOrdersByCustomer_Id(long id);
}
