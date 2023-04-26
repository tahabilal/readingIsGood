package com.example.readingisgood.service;

import com.example.readingisgood.exception.model.ResourceNotFoundException;
import com.example.readingisgood.model.domain.Customer;
import com.example.readingisgood.model.domain.Order;
import com.example.readingisgood.model.domain.OrderBook;
import com.example.readingisgood.model.dto.response.CustomerResponse;
import com.example.readingisgood.model.dto.response.CustomerStatisticResponse;
import com.example.readingisgood.model.dto.response.StatisticsResponse;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    public StatisticsService(final CustomerRepository customerRepository,final OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }
    public CustomerStatisticResponse getStatistics(String id){
        long lid = Long.parseLong(id);
        CustomerStatisticResponse customerStatisticResponse = new CustomerStatisticResponse();
        customerStatisticResponse.setStatisticsResponseList(new ArrayList<>());
        Customer customer = customerRepository.findById(lid).orElseThrow(() -> new ResourceNotFoundException("customer not found"));
        customerStatisticResponse.setCustomerResponse(customerToDto(customer));

        List<Order> orders = orderRepository.getOrdersByCustomer_Id(lid);
        List<Integer> years = getOrderYears(orders);
        for(int i=1; i<=12; i++) {
            int finalI = i;
            List<Order> sameMonthOrders = orders.stream()
                    .filter(order -> order.getCreatedAt().atZone(ZoneId.systemDefault()).getMonthValue() == finalI)
                    .collect(Collectors.toList());
            separateYearsStatistics(customerStatisticResponse,sameMonthOrders, years);
        }
        return customerStatisticResponse;
    }

    private void separateYearsStatistics(CustomerStatisticResponse customerStatisticResponse,
                                         List<Order> orders, List<Integer> years){
        for(int year : years){
            List<Order> collect = orders.stream()
                    .filter(order -> order.getCreatedAt().atZone(ZoneId.systemDefault()).getYear() == year)
                    .collect(Collectors.toList());
            if(collect.isEmpty()) continue;
            collectStatistics(customerStatisticResponse,collect);
        }
    }

    private void collectStatistics(CustomerStatisticResponse customerStatisticResponse,
                                   List<Order> orders){
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        statisticsResponse.setOrderCreateMonth(orders.get(0).getCreatedAt().atZone(ZoneId.systemDefault()).getMonth().name());
        statisticsResponse.setOrderCreateYear(orders.get(0).getCreatedAt().atZone(ZoneId.systemDefault()).getYear());
        statisticsResponse.setOrderCreateDate(orders.get(0).getCreatedAt());
        statisticsResponse.setTotalOrderCount(orders.size());
        statisticsResponse.setTotalBookCount(orders.stream().mapToInt(value ->
                value.getOrderBooks().stream().mapToInt(OrderBook::getBookAmount).sum()).sum());
        statisticsResponse.setTotalPurchasedAmount(orders.stream().mapToDouble(Order::getTotalPrice).sum());
        customerStatisticResponse.addStatistic(statisticsResponse);
    }

    private List<Integer> getOrderYears(List<Order> orders){
        List<Integer> years;
        years = orders.stream().map(order -> order.getCreatedAt()
                .atZone(ZoneId.systemDefault()).getYear()).distinct().collect(Collectors.toList());
        return years;
    }

    private CustomerResponse customerToDto(Customer customer){
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setEmail(customer.getEmail());
        return customerResponse;
    }
}
