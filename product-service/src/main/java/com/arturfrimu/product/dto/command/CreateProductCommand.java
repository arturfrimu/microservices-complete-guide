package com.arturfrimu.product.dto.command;

import com.arturfrimu.product.dto.request.CreateProductRequest;

import java.math.BigDecimal;

public record CreateProductCommand(String name, String description, BigDecimal price, Long categoryId) {

    public static CreateProductCommand valueOf(CreateProductRequest createProductRequest) {
        return new CreateProductCommand(
                createProductRequest.name(),
                createProductRequest.description(),
                createProductRequest.price(),
                createProductRequest.categoryId()
        );
    }
}
