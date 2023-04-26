package com.example.readingisgood.controller;

import com.example.readingisgood.model.dto.request.OrderRequest;
import com.example.readingisgood.model.dto.response.Success;
import com.example.readingisgood.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.readingisgood.util.Constants.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Success> takeOrder(@Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(new Success(orderService.saveOrder(orderRequest), ORDER_CREATING_SUCCESS_MESSAGE));
    }

    @GetMapping
    public ResponseEntity<Success> listOrderByDate(@RequestParam String startDate, @RequestParam String endDate) {
        return ResponseEntity.ok(new Success(orderService.getOrderByDateBetween(startDate,endDate),ORDER_GET_BY_DATE_INTERVAL_SUCCESS_MESSAGE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Success> getOrderById(@PathVariable String id){
        return ResponseEntity.ok(new Success(orderService.getById(id), ORDER_GET_BY_ID_SUCCESS_MESSAGE));
    }
}
