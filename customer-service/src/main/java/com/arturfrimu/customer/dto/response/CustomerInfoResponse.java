package com.arturfrimu.customer.dto.response;

import com.arturfrimu.customer.model.Customer;

public record CustomerInfoResponse(Long customerId, String name) {

    public static CustomerInfoResponse valueOf(Customer customer) {
        return new CustomerInfoResponse(customer.getCustomerId(), customer.getName());
    }
}
