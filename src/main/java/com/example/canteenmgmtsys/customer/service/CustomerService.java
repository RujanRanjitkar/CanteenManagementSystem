package com.example.canteenmgmtsys.customer.service;

import com.example.canteenmgmtsys.customer.dto.CustomerRequestDto;
import com.example.canteenmgmtsys.customer.model.Customer;

public interface CustomerService {
    Customer addNewCustomer(CustomerRequestDto customerRequestDto);

}
