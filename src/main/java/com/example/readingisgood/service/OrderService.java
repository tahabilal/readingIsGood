package com.example.readingisgood.service;

import com.example.readingisgood.exception.model.ResourceNotFoundException;
import com.example.readingisgood.model.domain.Book;
import com.example.readingisgood.model.domain.Customer;
import com.example.readingisgood.model.domain.Order;
import com.example.readingisgood.model.domain.OrderBook;
import com.example.readingisgood.model.dto.request.OrderRequest;
import com.example.readingisgood.model.dto.response.OrderBookResponse;
import com.example.readingisgood.model.dto.response.OrderResponse;
import com.example.readingisgood.repository.BookRepository;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.OrderRepository;
import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public OrderService(final OrderRepository orderRepository,final CustomerRepository customerRepository,
                        final BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public OrderResponse getById(String id){
        long lid = Long.parseLong(id);
        Order order = orderRepository.findById(lid).orElseThrow(() -> new ResourceNotFoundException("order not found"));
        return toOrderResponse(order);
    }

    public List<OrderResponse> getOrderByDateBetween(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime lclStartDate = LocalDate.parse(startDate,formatter).atStartOfDay();
        LocalDateTime lclEndDate = LocalDate.parse(endDate,formatter).atStartOfDay();
        return toOrderResponseList(orderRepository.getOrdersByCreatedAtBetween(lclStartDate, lclEndDate));
    }

    public OrderResponse saveOrder(OrderRequest orderRequest){
        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("customer not found"));
        return toOrderResponse(orderRepository.save(toOrder(orderRequest,customer)));
    }

    private List<OrderBook> createOrderBooks(OrderRequest orderRequest, Order order){
        AtomicDouble totalPrice = new AtomicDouble();
        List<OrderBook> orderBooks = new ArrayList<>();
        orderRequest.getOrderBookRequestList().forEach(bookInfo -> {
            OrderBook orderBook = new OrderBook();
            Book book = bookRepository.findById(bookInfo.getBookId()).
                    orElseThrow(() -> new ResourceNotFoundException("book not found"));
            try {
                book.reduceStock(bookInfo.getAmount());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            totalPrice.addAndGet(book.getPrice() * bookInfo.getAmount());
            orderBook.setBook(book);
            orderBook.setBookAmount(bookInfo.getAmount());
            orderBooks.add(orderBook);
        });
        order.setTotalPrice(totalPrice.get());
        return orderBooks;
    }

    public Page<OrderResponse> findOrders(String id, int page, int size){
        long lid = Long.parseLong(id);
        PageRequest pageRequest = PageRequest.of(page,size);
        return orderRepository.getOrdersByCustomer_Id(lid, pageRequest).map(this::toOrderResponse);
    }

    private Order toOrder(OrderRequest orderRequest, Customer customer){
        Order order = new Order();
        order.setDeliveryStreet(orderRequest.getDeliveryStreet());
        order.setDeliveryState(orderRequest.getDeliveryState());
        order.setDeliveryCity(orderRequest.getDeliveryCity());
        order.setDeliveryZip(orderRequest.getDeliveryZip());
        order.setOrderBooks(createOrderBooks(orderRequest,order));
        order.setCustomer(customer);
        return order;
    }

    private OrderResponse toOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getId());
        orderResponse.setCustomerId(order.getCustomer().getId());
        orderResponse.setDeliveryCity(order.getDeliveryCity());
        orderResponse.setDeliveryZip(order.getDeliveryZip());
        orderResponse.setDeliveryStreet(order.getDeliveryStreet());
        orderResponse.setDeliveryState(order.getDeliveryState());
        orderResponse.setOrderBookResponseList(toOrderBookInfoResponseList(order.getOrderBooks()));
        return orderResponse;
    }

    private List<OrderBookResponse> toOrderBookInfoResponseList(List<OrderBook> orderBooks){
        return orderBooks.stream().map(orderBook ->
                new OrderBookResponse(orderBook.getId(),orderBook.getBook().getId()
                        ,orderBook.getBook().getName(),orderBook.getBookAmount())).collect(Collectors.toList());
    }

    private List<OrderResponse> toOrderResponseList(List<Order> orders){
        return orders.stream().map(this::toOrderResponse).collect(Collectors.toList());
    }
}
