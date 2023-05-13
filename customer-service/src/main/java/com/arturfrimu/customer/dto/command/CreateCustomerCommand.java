package com.arturfrimu.customer.dto.command;

import com.arturfrimu.customer.dto.request.CreateCustomerRequest;
import lombok.Getter;

@Getter
public final class CreateCustomerCommand {
    private final String name;
    private final String email;
    private final String address;

    public CreateCustomerCommand(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public static CreateCustomerCommand valueOf(CreateCustomerRequest createCustomerRequest) {
        return new CreateCustomerCommand(
                createCustomerRequest.getName(),
                createCustomerRequest.getEmail(),
                createCustomerRequest.getAddress()
        );
    }
}
