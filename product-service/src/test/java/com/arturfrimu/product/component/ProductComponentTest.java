package com.arturfrimu.product.component;

import com.arturfrimu.product.component.random.request.RandomCreateProductRequest;
import com.arturfrimu.product.dto.request.CreateProductRequest;
import com.arturfrimu.product.dto.response.ProductDetailsResponse;
import com.arturfrimu.product.dto.response.ProductInfoResponse;
import com.arturfrimu.product.model.Category;
import com.arturfrimu.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.RequestEntity.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductComponentTest {

    @Autowired
    BaseRestTemplate restTemplate;
    @Autowired
    ProductRepository productRepository;

    private CreateProductRequest appleWatch;
    private CreateProductRequest macbookPro;

    @BeforeEach
    public void setup() {
        appleWatch = RandomCreateProductRequest.builder()
                .name("Apple Watch")
                .categoryId(1L)
                .build()
                .get();

        macbookPro = RandomCreateProductRequest.builder()
                .name("Macbook Pro")
                .categoryId(1L)
                .build()
                .get();
    }

    @AfterEach
    public void clean() {
        productRepository.deleteAll();
    }

    @Test
    public void testLifecycle() {
        testCreateProduct(appleWatch);
        testCreateProduct(macbookPro);

        var products = testListProducts();

        var nameToId = products.stream().collect(toMap(ProductInfoResponse::name, ProductInfoResponse::productId));

        testFindProduct(nameToId.get(macbookPro.name()), new ProductDetailsResponse(
                nameToId.get(macbookPro.name()),
                macbookPro.name(),
                macbookPro.description(),
                macbookPro.price(),
                new Category(macbookPro.categoryId(), "Electronics")
        ));

        testDeleteProduct(nameToId.get(macbookPro.name()));
    }

    private void testCreateProduct(CreateProductRequest body) {
        var response = restTemplate.exchange(post(PRODUCT_BASE_URL).body(body), PRODUCT_INFO);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var createdProduct = response.getBody();

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.productId()).isNotNull();
        assertThat(createdProduct.name()).isEqualTo(body.name());
    }

    private List<ProductInfoResponse> testListProducts() {
        var response = restTemplate.exchange(get(PRODUCT_BASE_URL).build(), PRODUCT_LIST);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var products = response.getBody();

        assertThat(products).isNotNull();
        assertThat(products)
                .extracting(ProductInfoResponse::name)
                .containsExactlyInAnyOrder(appleWatch.name(), macbookPro.name());

        return products;
    }

    private void testFindProduct(Long productId, ProductDetailsResponse expected) {
        var response = restTemplate.exchange(get(PRODUCT_BASE_URL + "/" + productId).build(), PRODUCT_DETAILS);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var product = response.getBody();

        assertThat(product).isNotNull();

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