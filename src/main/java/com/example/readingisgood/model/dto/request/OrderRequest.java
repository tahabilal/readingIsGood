package com.example.readingisgood.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message="customer id is required")
    private Long customerId;
    @NotBlank(message="delivery street is required")
    private String deliveryStreet;
    @NotBlank(message="delivery city is required")
    private String deliveryCity;
    @NotBlank(message="delivery state is required")
    private String deliveryState;
    @NotBlank(message="delivery zip is required")
    private String deliveryZip;

    @Size(min=1, message="You must choose at least 1 book")
    private List<OrderBookRequest> orderBookRequestList;
}
