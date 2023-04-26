package com.example.readingisgood.controller;

import com.example.readingisgood.config.TestConfigAdmin;
import com.example.readingisgood.service.StatisticsService;
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
@Import({TestConfigAdmin.class})
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StatisticsService statisticsService;
    @Test @WithMockUser
    void getCustomerMonthlyStatistics() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/statistics/{id}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(statisticsService).getStatistics(any(String.class));
    }
}