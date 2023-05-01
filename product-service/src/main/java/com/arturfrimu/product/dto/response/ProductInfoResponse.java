package com.arturfrimu.product.dto.response;

import com.arturfrimu.product.model.Product;
import lombok.Getter;

@Getter
public final class ProductInfoResponse {
    private final Long productId;
    private final String name;

    public ProductInfoResponse(Long productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    public static ProductInfoResponse valueOf(Product product) {
        return new ProductInfoResponse(product.getProductId(), product.getName());
    }
}
