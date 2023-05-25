package com.arturfrimu.customer.dto.response;

import com.arturfrimu.customer.model.Customer;

public record CustomerDetailsResponse(Long customerId, String name, String email, String address) {

    public static CustomerDetailsResponse valueOf(Customer customer) {
        return new CustomerDetailsResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
