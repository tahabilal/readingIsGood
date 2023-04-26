package com.example.readingisgood.service;

import com.example.readingisgood.config.TestConfigAdmin;
import com.example.readingisgood.model.dto.request.CustomerRequest;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({TestConfigAdmin.class})
class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    UserRepository userRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void save() throws Exception {
        CustomerService customerService = new CustomerService(customerRepository, userRepository, passwordEncoder);
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("customer");
        customerRequest.setUsername("username");
        customerRequest.setAddress("address");
        customerRequest.setEmail("mail@mail.com");
        assertEquals("customer",customerService.save(customerRequest).getName());
    }
}