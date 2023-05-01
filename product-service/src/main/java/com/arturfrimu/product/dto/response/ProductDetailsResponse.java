package com.arturfrimu.product.dto.response;

import com.arturfrimu.product.model.Category;
import com.arturfrimu.product.model.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class ProductDetailsResponse {
    private final Long productId;
    private final String name;
    private final String description;
    private final Double price;
    private final Category category;

    public ProductDetailsResponse(Long productId, String name, String description, Double price, Category category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public static ProductDetailsResponse valueOf(Product product) {
        return new ProductDetailsResponse(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }
}
