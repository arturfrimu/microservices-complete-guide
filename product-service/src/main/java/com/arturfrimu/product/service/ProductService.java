package com.arturfrimu.product.service;

import com.arturfrimu.product.dto.command.CreateProductCommand;
import com.arturfrimu.product.dto.filter.SearchProductCriteria;
import com.arturfrimu.product.dto.response.ProductDetailsResponse;
import com.arturfrimu.product.dto.response.ProductInfoResponse;
import com.arturfrimu.product.exception.ResourceNotFoundException;
import com.arturfrimu.product.model.Product;
import com.arturfrimu.product.repository.CategoryRepository;
import com.arturfrimu.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CriteriaProductRepository criteriaProductRepository;

    public List<ProductInfoResponse> list(SearchProductCriteria productFilter) {
        return criteriaProductRepository.list(productFilter);
    }

    public ProductDetailsResponse find(Long id) {
        return productRepository.findById(id).map(ProductDetailsResponse::valueOf)
                .orElseThrow(() -> new ResourceNotFoundException(format("Product not found with id: %s", id)));
    }

    @Transactional
    public ProductInfoResponse create(CreateProductCommand command) {
        var category = categoryRepository.findById(command.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(format("Category not found with id: %s", command.categoryId())));

        var newProduct = new Product(
                command.name(),
                command.description(),
                command.price(),
                category
        );

        return ProductInfoResponse.valueOf(productRepository.save(newProduct));
    }

    public void delete(Long id) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Product not found with id: %s", id)));

        productRepository.delete(existingProduct);
    }
}
