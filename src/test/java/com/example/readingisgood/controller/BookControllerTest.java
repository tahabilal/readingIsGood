package com.example.readingisgood.controller;

import com.example.readingisgood.config.TestConfigAdmin;
import com.example.readingisgood.model.dto.request.BookRequest;
import com.example.readingisgood.model.dto.request.BookStockUpdateRequest;
import com.example.readingisgood.service.BookService;
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

@AutoConfigureMockMvc
@SpringBootTest
@Import({TestConfigAdmin.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookService bookService;

    @Test @WithMockUser
    void addBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new BookRequest("bookName","author","2021-10-16",2,5))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }


    @Test @WithMockUser
    void updateStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new BookStockUpdateRequest(1,10))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }
}