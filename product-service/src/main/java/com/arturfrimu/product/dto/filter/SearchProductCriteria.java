package com.arturfrimu.product.dto.filter;

import lombok.Builder;

@Builder
public record SearchProductCriteria(Long customerId) {
}