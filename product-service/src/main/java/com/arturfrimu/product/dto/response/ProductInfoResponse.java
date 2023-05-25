package com.arturfrimu.product.dto.response;

import com.arturfrimu.product.model.Product;

public record ProductInfoResponse(Long productId, String name) {

    public static ProductInfoResponse valueOf(Product product) {
        return new ProductInfoResponse(product.getProductId(), product.getName());
    }
}
