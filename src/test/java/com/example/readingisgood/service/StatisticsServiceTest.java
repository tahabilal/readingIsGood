package com.example.readingisgood.service;

import com.example.readingisgood.model.domain.Customer;
import com.example.readingisgood.model.domain.Order;
import com.example.readingisgood.model.domain.OrderBook;
import com.example.readingisgood.model.dto.response.CustomerStatisticResponse;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StatisticsServiceTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void getStatistics() {

        StatisticsService statisticsService = new StatisticsService(customerRepository,orderRepository);
        OrderBook orderBook = new OrderBook();
        orderBook.setBookAmount(3);
        OrderBook orderBook1 = new OrderBook();
        orderBook1.setBookAmount(5);
        Customer customer = new Customer();
        customer.setName("name");
        em.persist(customer);

        Order order1 = new Order();
        order1.setDeliveryCity("city1");
        order1.setCreatedAt(LocalDateTime.of(2022,10,15,3,0));
        order1.setTotalPrice(10.1);
        order1.setOrderBooks(List.of(orderBook1));
        order1.setCustomer(customer);
        Order order2 = new Order();
        order2.setCreatedAt(LocalDateTime.of(2021,7,13,12,0));
        order2.setDeliveryCity("city2");
        order2.setCustomer(customer);
        order2.setTotalPrice(23.0);
        order2.setOrderBooks(List.of(orderBook));
        em.persist(order1);
        em.persist(order2);

        CustomerStatisticResponse customerStatisticResponse = statisticsService.getStatistics(customer.getId().toString());

        assertEquals("name",customerStatisticResponse.getCustomerResponse().getName());
    }
}