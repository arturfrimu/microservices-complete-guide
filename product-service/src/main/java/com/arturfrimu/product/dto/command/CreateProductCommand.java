package com.arturfrimu.product.dto.command;

import com.arturfrimu.product.dto.request.CreateProductRequest;
import lombok.Getter;

@Getter
public final class CreateProductCommand {
    private final String name;
    private final String description;
    private final Double price;
    private final Long categoryId;

    public CreateProductCommand(String name, String description, Double price, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public static CreateProductCommand valueOf(CreateProductRequest createProductRequest) {
        return new CreateProductCommand(
                createProductRequest.getName(),
                createProductRequest.getDescription(),
                createProductRequest.getPrice(),
                createProductRequest.getCategoryId()
        );
    }
}
