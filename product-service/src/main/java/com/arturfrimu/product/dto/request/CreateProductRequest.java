package com.arturfrimu.product.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public final class CreateProductRequest {
    private final String name;
    private final String description;
    private final Double price;
    private final Long categoryId;
}
