package com.example.readingisgood.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CustomerStatisticResponse {
    private CustomerResponse customerResponse;
    private List<StatisticsResponse> statisticsResponseList;

    public void addStatistic(StatisticsResponse statisticsResponse){
        this.statisticsResponseList.add(statisticsResponse);
    }
}
