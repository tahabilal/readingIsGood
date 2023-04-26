package com.example.readingisgood.service;

import com.example.readingisgood.model.domain.Customer;
import com.example.readingisgood.model.domain.User;
import com.example.readingisgood.model.dto.request.CustomerRequest;
import com.example.readingisgood.model.dto.response.CustomerResponse;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public CustomerService(final CustomerRepository customerRepository,
                           final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse save(CustomerRequest customerRequest) throws Exception {
        if(customerRepository.findByEmail(customerRequest.getEmail()).isPresent())
            throw new Exception("this email already defined another customer");
        Customer newCustomer = toCustomer(customerRequest);
        return toCustomerResponse(customerRepository.save(newCustomer));
    }

    private Customer toCustomer(CustomerRequest customerRequest){
        User user = new User();
        user.setUsername(customerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        user.setIsCustomer(true);
        userRepository.save(user);
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setAddress(customerRequest.getAddress());
        customer.setUser(user);
        return customer;
    }

    private CustomerResponse toCustomerResponse(Customer customer){
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setEmail(customer.getEmail());
        return customerResponse;
    }
}
