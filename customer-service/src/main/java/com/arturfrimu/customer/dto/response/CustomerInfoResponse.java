package com.arturfrimu.customer.dto.response;

import com.arturfrimu.customer.model.Customer;
import lombok.Getter;

@Getter
public final class CustomerInfoResponse {
    private final Long customerId;
    private final String name;

    public CustomerInfoResponse(Long customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public static CustomerInfoResponse valueOf(Customer customer) {
        return new CustomerInfoResponse(customer.getCustomerId(), customer.getName());
    }
}
