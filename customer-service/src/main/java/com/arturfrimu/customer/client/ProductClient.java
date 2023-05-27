package com.arturfrimu.customer.client;

import com.arturfrimu.customer.dto.response.ProductInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "productClient", url = "http://localhost:8080/products")
public interface ProductClient {

    @GetMapping
    List<ProductInfoResponse> getCustomerProducts(@RequestParam Long customerId);
}
