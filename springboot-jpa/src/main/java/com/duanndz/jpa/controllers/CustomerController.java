package com.duanndz.jpa.controllers;

import com.duanndz.domain.Customer;
import com.duanndz.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * duan.nguyen
 * Datetime 5/8/20 15:29
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        return customerRepository.save(customer);
    }
}
