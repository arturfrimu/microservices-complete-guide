package com.arturfrimu.customer.component.random.requests;

import com.arturfrimu.customer.dto.request.CreateCustomerRequest;
import lombok.Builder;
import lombok.Builder.Default;

import java.util.function.Supplier;

import static com.arturfrimu.customer.commons.CommonRandom.randomString;

@Builder
public class RandomCreateCustomerRequest implements Supplier<CreateCustomerRequest> {

    @Default
    private final String name = randomString(10);
    @Default
    private final String email = randomString(10);
    @Default
    private final String address = randomString(10);

    @Override
    public CreateCustomerRequest get() {
        return new CreateCustomerRequest(name, email, address);
    }
}
