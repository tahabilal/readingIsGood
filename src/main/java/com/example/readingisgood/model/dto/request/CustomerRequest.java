package com.example.readingisgood.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank(message="customer name is required")
    private String name;
    @NotBlank(message="e-mail is required")
    @Email
    private String email;
    @NotBlank(message="username is required")
    private String username;
    @NotBlank(message="password is required")
    private String password;
    @NotBlank(message="address is required")
    private String address;
}
