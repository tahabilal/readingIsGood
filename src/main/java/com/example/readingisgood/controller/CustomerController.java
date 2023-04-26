package com.example.readingisgood.controller;


import com.example.readingisgood.model.dto.request.CustomerRequest;
import com.example.readingisgood.model.dto.response.Success;
import com.example.readingisgood.service.CustomerService;
import com.example.readingisgood.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.readingisgood.util.Constants.CUSTOMER_CREATING_SUCCESS_MESSAGE;
import static com.example.readingisgood.util.Constants.CUSTOMER_ORDER_GET_SUCCESS_MESSAGE;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    public CustomerController(final CustomerService customerService,
                              final OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Success> getAllOrdersByCustomer(@PathVariable String id,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(new Success(orderService.findOrders(id, page, size),CUSTOMER_ORDER_GET_SUCCESS_MESSAGE));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Success> saveCustomer(@Valid @RequestBody CustomerRequest customer) throws Exception {
        return ResponseEntity.ok(new Success(customerService.save(customer), CUSTOMER_CREATING_SUCCESS_MESSAGE));
    }
}
