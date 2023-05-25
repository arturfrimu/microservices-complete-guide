package com.arturfrimu.product.dto.response;

import com.arturfrimu.product.model.Category;
import com.arturfrimu.product.model.Product;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductDetailsResponse(Long productId, String name, String description, BigDecimal price, Category category) {

    public static ProductDetailsResponse valueOf(Product product) {
        return new ProductDetailsResponse(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailsResponse that = (ProductDetailsResponse) o;
        return Objects.equals(productId, that.productId) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, category);
    }
}
