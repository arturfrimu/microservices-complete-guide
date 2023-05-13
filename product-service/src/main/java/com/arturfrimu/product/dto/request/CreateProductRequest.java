package com.arturfrimu.product.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public final class CreateProductRequest {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Long categoryId;
}
