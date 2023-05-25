package com.arturfrimu.customer.dto.command;

import com.arturfrimu.customer.dto.request.CreateCustomerRequest;

public record CreateCustomerCommand(String name, String email, String address) {

    public static CreateCustomerCommand valueOf(CreateCustomerRequest createCustomerRequest) {
        return new CreateCustomerCommand(
                createCustomerRequest.name(),
                createCustomerRequest.email(),
                createCustomerRequest.address()
        );
    }
}
