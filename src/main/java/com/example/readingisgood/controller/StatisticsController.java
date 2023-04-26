package com.example.readingisgood.controller;

import com.example.readingisgood.model.dto.response.Success;
import com.example.readingisgood.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Success> getCustomerMonthlyStatistics(@PathVariable String id){
        return ResponseEntity.ok(new Success(statisticsService.getStatistics(id),
                "customer's monthly statistics operation completed"));
    }
}
