package com.arturfrimu.product.component;

import com.arturfrimu.product.dto.request.CreateProductRequest;
import com.arturfrimu.product.dto.response.ProductDetailsResponse;
import com.arturfrimu.product.dto.response.ProductInfoResponse;
import com.arturfrimu.product.model.Category;
import com.arturfrimu.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.RequestEntity.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ComponentTest {

    @Autowired
    BaseRestTemplate restTemplate;
    @Autowired
    ProductRepository productRepository; // TODO: 01/05/2023 remove me

    @Test
    public void testLifecycle() {
        productRepository.deleteAll(); // TODO: 01/05/2023 Use test containers instead

        testCreateProduct(new CreateProductRequest( // TODO: 01/05/2023 Replace object creation with json format
                "Apple MacBook Pro",
                "Powerful laptop with M1 chip and Retina display",
                BigDecimal.valueOf(1499.99),
                1L
        ));

        testCreateProduct(new CreateProductRequest(
                "Samsung Galaxy S21",
                "Flagship Android smartphone with 5G capabilities",
                BigDecimal.valueOf(799.99),
                1L)
        );

        var products = testListProducts();

        var nameToId = products.stream().collect(toMap(ProductInfoResponse::getName, ProductInfoResponse::getProductId));

        testFindProduct(nameToId.get("Apple MacBook Pro"), new ProductDetailsResponse(
                nameToId.get("Apple MacBook Pro"),
                "Apple MacBook Pro",
                "Powerful laptop with M1 chip and Retina display",
                BigDecimal.valueOf(1499.99),
                new Category(1L, "Electronics")
        ));

        testDeleteProduct(nameToId.get("Samsung Galaxy S21"));
        testDeleteProduct(nameToId.get("Apple MacBook Pro"));
    }

    private void testCreateProduct(CreateProductRequest body) {
        var response = restTemplate.exchange(post(PRODUCT_BASE_URL).body(body), PRODUCT_INFO);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var createdProduct = response.getBody();

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getProductId()).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo(body.getName());
    }

    private List<ProductInfoResponse> testListProducts() {
        var response = restTemplate.exchange(get(PRODUCT_BASE_URL).build(), PRODUCT_LIST);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var products = response.getBody();

        assertThat(products).isNotNull();
        assertThat(products)
                .extracting(ProductInfoResponse::getName)
                .containsExactlyInAnyOrder("Apple MacBook Pro", "Samsung Galaxy S21");

        return products;
    }

    private void testFindProduct(Long productId, ProductDetailsResponse expected) {
        var response = restTemplate
                .exchange(get(PRODUCT_BASE_URL + "/" + productId).build(), PRODUCT_DETAILS);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var product = response.getBody();

        assertThat(product).isEqualTo(expected);
    }

    private void testDeleteProduct(Long productId) {
        var response = restTemplate.exchange(delete(PRODUCT_BASE_URL + "/" + productId)
                .build(), VOID);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    // @formatter:off
    static final String PRODUCT_BASE_URL = "/products";

    static final ParameterizedTypeReference<Void> VOID = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<ProductInfoResponse> PRODUCT_INFO = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<ProductDetailsResponse> PRODUCT_DETAILS = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<List<ProductInfoResponse>> PRODUCT_LIST = new ParameterizedTypeReference<>() {};
    //formatter:on
}