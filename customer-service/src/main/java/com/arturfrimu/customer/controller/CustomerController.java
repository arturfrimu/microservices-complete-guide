package com.arturfrimu.customer.controller;

import com.arturfrimu.customer.dto.command.CreateCustomerCommand;
import com.arturfrimu.customer.dto.request.CreateCustomerRequest;
import com.arturfrimu.customer.dto.response.CustomerDetailsResponse;
import com.arturfrimu.customer.dto.response.CustomerInfoResponse;
import com.arturfrimu.customer.dto.response.CustomerProductsResponse;
import com.arturfrimu.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Optional.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public final class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerInfoResponse>> list() {
        return ok(customerService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailsResponse> find(@PathVariable Long id) {
        var customer = customerService.find(id);
        return ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerInfoResponse> create(@RequestBody CreateCustomerRequest body) {
        var command = of(body).map(CreateCustomerCommand::valueOf).get();
        var createdCustomer = customerService.create(command);
        return ok(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ok().build();
    }

    @GetMapping("/{customerId}/products")
    public ResponseEntity<CustomerProductsResponse> listCustomerProducts(@PathVariable Long customerId) {
        var customerProducts = customerService.listCustomerProducts(customerId);
        return ok(customerProducts);
    }
}

