package com.example.readingisgood.controller;

import com.example.readingisgood.config.TestConfigUser;
import com.example.readingisgood.model.dto.request.CustomerRequest;
import com.example.readingisgood.service.CustomerService;
import com.example.readingisgood.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@AutoConfigureMockMvc
@SpringBootTest
@Import({TestConfigUser.class})
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @MockBean
    OrderService orderService;
    @Test @WithMockUser
    void getAllOrdersByCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id","1")
                        .queryParam("page","0")
                        .queryParam("size","5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(orderService).findOrders(any(String.class),any(Integer.class),any(Integer.class));
    }

    @Test @WithMockUser
    void saveCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CustomerRequest("name","mail@mail.com",
                                        "username","password","address"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(customerService).save(any(CustomerRequest.class));
    }
}