package com.example.canteenmgmtsys.customer.controller;

import com.example.canteenmgmtsys.customer.dto.CustomerRequestDto;
import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/add_new_customer")
    public String addNewCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors("email")) {
            FieldError emailError = bindingResult.getFieldError("email");
            return emailError.getDefaultMessage();

        } else if (bindingResult.hasFieldErrors("phoneNumber")) {
            FieldError phoneNumberError = bindingResult.getFieldError("phoneNumber");
            return phoneNumberError.getDefaultMessage();
        }

        Customer customer = customerService.addNewCustomer(customerRequestDto);

        return "Customer with id " + customer.getCustomerId() + " created successfully";
    }
}
