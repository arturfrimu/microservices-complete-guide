package com.arturfrimu.customer.service;

import com.arturfrimu.customer.client.ProductClient;
import com.arturfrimu.customer.dto.command.CreateCustomerCommand;
import com.arturfrimu.customer.dto.response.CustomerDetailsResponse;
import com.arturfrimu.customer.dto.response.CustomerInfoResponse;
import com.arturfrimu.customer.dto.response.CustomerProductsResponse;
import com.arturfrimu.customer.exception.ResourceNotFoundException;
import com.arturfrimu.customer.model.Customer;
import com.arturfrimu.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductClient productClient;

    public List<CustomerInfoResponse> list() {
        return customerRepository.findAll().stream().map(CustomerInfoResponse::valueOf).collect(toList());
    }

    public CustomerDetailsResponse find(Long id) {
        return customerRepository.findById(id).map(CustomerDetailsResponse::valueOf)
                .orElseThrow(() -> new ResourceNotFoundException(format("Customer not found with id: %s", id)));
    }

    @Transactional
    public CustomerInfoResponse create(CreateCustomerCommand command) {
        var newCustomer = new Customer(
                command.name(),
                command.email(),
                command.address()
        );

        return CustomerInfoResponse.valueOf(customerRepository.save(newCustomer));
    }

    public void delete(Long id) {
        var existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Customer not found with id: %s", id)));

        customerRepository.delete(existingCustomer);
    }

    public CustomerProductsResponse getCustomerProducts(Long customerId) {
        var customer = customerRepository.findById(customerId).map(CustomerDetailsResponse::valueOf)
                .orElseThrow(() -> new ResourceNotFoundException(format("Customer not found with id: %s", customerId)));

        var customerProducts = productClient.getCustomerProducts();

        return new CustomerProductsResponse(
                customer.customerId(),
                customer.name(),
                customerProducts
        );
    }
}
