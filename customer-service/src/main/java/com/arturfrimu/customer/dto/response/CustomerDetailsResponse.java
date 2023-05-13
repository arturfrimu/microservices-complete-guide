package com.arturfrimu.customer.dto.response;

import com.arturfrimu.customer.model.Customer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class CustomerDetailsResponse {
    private final Long customerId;
    private final String name;
    private final String email;
    private final String address;

    public CustomerDetailsResponse(Long customerId, String name, String email, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public static CustomerDetailsResponse valueOf(Customer customer) {
        return new CustomerDetailsResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
