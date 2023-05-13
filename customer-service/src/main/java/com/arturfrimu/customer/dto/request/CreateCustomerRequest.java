package com.arturfrimu.customer.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class CreateCustomerRequest {
    private final String name;
    private final String email;
    private final String address;
}
