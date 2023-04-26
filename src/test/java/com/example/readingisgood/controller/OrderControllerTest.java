package com.example.readingisgood.controller;

import com.example.readingisgood.config.TestConfigAdmin;
import com.example.readingisgood.model.dto.request.OrderBookRequest;
import com.example.readingisgood.model.dto.request.OrderRequest;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
@AutoConfigureMockMvc
@SpringBootTest
@Import({TestConfigAdmin.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @Test @WithMockUser
    void takeOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OrderRequest(1L,"street",
                                "city","state","zip",List.of(new OrderBookRequest())))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(orderService).saveOrder(any(OrderRequest.class));
    }

    @Test @WithMockUser
    void listOrderByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("startDate","2020-10-05")
                    .queryParam("endDate","2023-10-05"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(orderService).getOrderByDateBetween(any(String.class),any(String.class));
    }

    @Test @WithMockUser
    void getOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(orderService).getById(any(String.class));
    }
}