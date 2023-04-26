package com.example.readingisgood.service;

import com.example.readingisgood.model.domain.Customer;
import com.example.readingisgood.model.domain.Order;
import com.example.readingisgood.model.dto.request.OrderBookRequest;
import com.example.readingisgood.model.dto.request.OrderRequest;
import com.example.readingisgood.model.dto.response.OrderResponse;
import com.example.readingisgood.repository.BookRepository;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void getById() {
        OrderService orderService = new OrderService(orderRepository,customerRepository,bookRepository);
        Customer customer = new Customer();
        customer.setName("name");
        em.persist(customer);
        Order order = new Order();
        order.setCustomer(customer);
        order.setDeliveryCity("city");
        em.persist(order);

        assertEquals("city", orderService.getById(order.getId().toString()).getDeliveryCity());
    }

    @Test
    void getOrderByDateBetween() {
        OrderService orderService = new OrderService(orderRepository,customerRepository,bookRepository);
        Customer customer = new Customer();
        customer.setName("name");

        em.persist(customer);
        Order order1 = new Order();
        order1.setCreatedAt(LocalDateTime.of(2022,10,15,3,0));
        order1.setDeliveryCity("city1");
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setCreatedAt(LocalDateTime.of(2021,7,13,12,0));
        order2.setDeliveryCity("city2");
        order2.setCustomer(customer);
        em.persist(order1);
        em.persist(order2);

        List<OrderResponse> orders = orderService.getOrderByDateBetween("2020-11-13","2023-10-15");

        assertEquals(2,orders.size());
        assertEquals("city1", orders.get(0).getDeliveryCity());
    }

    @Test
    void saveOrder() {
        OrderService orderService = new OrderService(orderRepository,customerRepository,bookRepository);
        Customer customer = new Customer();
        customer.setName("name");
        em.persist(customer);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(customer.getId());
        orderRequest.setOrderBookRequestList(List.of(new OrderBookRequest(1,2)));
        orderRequest.setDeliveryCity("city");

        assertEquals(2,orderService.saveOrder(orderRequest).getOrderBookResponseList().get(0).getAmount());
    }

    @Test
    void findOrders() {
        OrderService orderService = new OrderService(orderRepository,customerRepository,bookRepository);
        Customer customer = new Customer();
        customer.setName("name");
        em.persist(customer);

        Order order1 = new Order();
        order1.setDeliveryCity("city1");
        order1.setCustomer(customer);
        Order order2 = new Order();
        order2.setDeliveryCity("city2");
        order2.setCustomer(customer);
        em.persist(order1);
        em.persist(order2);

        Page<OrderResponse> orders = orderService.findOrders(customer.getId().toString(), 0,1);

        assertEquals(1,orders.getContent().size());
        assertEquals("city1",orders.getContent().get(0).getDeliveryCity());
    }
}