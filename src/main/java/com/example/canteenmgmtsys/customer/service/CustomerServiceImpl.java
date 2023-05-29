package com.example.canteenmgmtsys.customer.service;

import com.example.canteenmgmtsys.customException.exceptions.ResourceAlreadyExistsException;
import com.example.canteenmgmtsys.customer.dto.CustomerRequestDto;
import com.example.canteenmgmtsys.customer.enums.Role;
import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.customer.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Customer addNewCustomer(CustomerRequestDto customerRequestDto) {

        logger.info("Getting existing customer according to email or phone number");
        List<Customer> existingCustomerList = customerRepo.findByEmailOrPhoneNumber(customerRequestDto.getEmail(), customerRequestDto.getPhoneNumber());

        if (!existingCustomerList.isEmpty()) {

            logger.error("Customer already exists");
            throw new ResourceAlreadyExistsException("Customer with email or phone number already exists. Please try again!!!");
        }

        Customer customer = new Customer();

        if (customerRequestDto.getEmail().equals("rujan@gmail.com")) {
            customer.setRole(Role.ADMIN);
        } else {
            customer.setRole(Role.USER);
        }

        logger.info("Converting customerRequestDto into customer");
        customer.setCustomerName(customerRequestDto.getCustomerName());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setPhoneNumber(customerRequestDto.getPhoneNumber());
        customer.setPassword(passwordEncoder.encode(customerRequestDto.getPassword()));

        logger.info("Adding new customer in database");
        customerRepo.save(customer);
        return customer;
    }
}
