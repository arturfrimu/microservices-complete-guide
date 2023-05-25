package com.arturfrimu.product.component.random.request;

import com.arturfrimu.product.dto.request.CreateProductRequest;
import lombok.Builder;
import lombok.Builder.Default;

import java.math.BigDecimal;
import java.util.function.Supplier;

import static com.arturfrimu.product.commons.CommonRandom.*;

@Builder
public class RandomCreateProductRequest implements Supplier<CreateProductRequest> {

    @Default
    private final String name = randomString(10);
    @Default
    private final String description = randomString(10);
    @Default
    private final BigDecimal price = randomBigDecimal(100, 2000);
    @Default
    private final Long categoryId = randomLong(10, 20);

    @Override
    public CreateProductRequest get() {
        return new CreateProductRequest(name, description, price, categoryId);
    }
}
