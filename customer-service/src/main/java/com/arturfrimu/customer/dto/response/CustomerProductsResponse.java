package com.arturfrimu.customer.dto.response;

import java.util.List;

public record CustomerProductsResponse(Long customerId, String name, List<ProductInfoResponse> products) {
}
