package com.arturfrimu.customer.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public final class ProductInfoResponse {
    private Long productId;
    private String name;
}
