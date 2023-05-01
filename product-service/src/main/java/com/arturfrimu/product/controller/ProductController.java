package com.arturfrimu.product.controller;

import com.arturfrimu.product.dto.command.CreateProductCommand;
import com.arturfrimu.product.dto.response.ProductInfoResponse;
import com.arturfrimu.product.dto.request.CreateProductRequest;
import com.arturfrimu.product.dto.response.ProductDetailsResponse;
import com.arturfrimu.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Optional.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public final class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductInfoResponse>> list() {
        return ok(productService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsResponse> find(@PathVariable Long id) {
        var product = productService.find(id);
        return ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductInfoResponse> create(@RequestBody CreateProductRequest body) {
        var command = of(body).map(CreateProductCommand::valueOf).get();
        var createdProduct = productService.create(command);
        return ok(createdProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ok().build();
    }
}

