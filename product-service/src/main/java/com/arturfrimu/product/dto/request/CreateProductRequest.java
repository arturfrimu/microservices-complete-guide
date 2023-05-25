package com.arturfrimu.product.dto.request;

import java.math.BigDecimal;

public record CreateProductRequest(String name, String description, BigDecimal price, Long categoryId) {
}
