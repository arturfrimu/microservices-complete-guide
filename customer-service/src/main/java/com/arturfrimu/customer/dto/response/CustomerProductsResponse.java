package com.arturfrimu.customer.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public final class CustomerProductsResponse {
    private final Long customerId;
    private final String name;
    private final List<ProductInfoResponse> products;
}
