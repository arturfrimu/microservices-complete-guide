package com.arturfrimu.customer.component;

import com.arturfrimu.customer.component.random.requests.RandomCreateCustomerRequest;
import com.arturfrimu.customer.dto.request.CreateCustomerRequest;
import com.arturfrimu.customer.dto.response.CustomerDetailsResponse;
import com.arturfrimu.customer.dto.response.CustomerInfoResponse;
import com.arturfrimu.customer.repository.CustomerRepository;
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
class CustomerComponentTest {

    @Autowired
    BaseRestTemplate restTemplate;
    @Autowired
    CustomerRepository customerRepository;

    private CreateCustomerRequest johnSnow;
    private CreateCustomerRequest alanKey;

    @BeforeEach
    public void setup() {
        johnSnow = RandomCreateCustomerRequest.builder()
                .name("John Snow")
                .build()
                .get();

        alanKey = RandomCreateCustomerRequest.builder()
                .name("Alan key")
                .build()
                .get();
    }

    @AfterEach
    public void clean() {
        customerRepository.deleteAll(); // TODO: 01/05/2023 Use test containers instead
    }

    @Test
    public void testLifecycle() {
        testCreateCustomer(johnSnow);
        testCreateCustomer(alanKey);

        var customers = testListCustomers();

        var nameToId = customers.stream().collect(toMap(CustomerInfoResponse::name, CustomerInfoResponse::customerId));

        testFindCustomer(nameToId.get(johnSnow.name()), new CustomerDetailsResponse(
                nameToId.get(johnSnow.name()),
                johnSnow.name(),
                johnSnow.email(),
                johnSnow.address()
        ));

        testDeleteCustomer(nameToId.get(alanKey.name()));
        testDeleteCustomer(nameToId.get(johnSnow.name()));
    }

    private void testCreateCustomer(CreateCustomerRequest body) {
        var response = restTemplate.exchange(post(PRODUCT_BASE_URL).body(body), PRODUCT_INFO);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var createdCustomer = response.getBody();

        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.customerId()).isNotNull();
        assertThat(createdCustomer.name()).isEqualTo(body.name());
    }

    private List<CustomerInfoResponse> testListCustomers() {
        var response = restTemplate.exchange(get(PRODUCT_BASE_URL).build(), PRODUCT_LIST);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var customers = response.getBody();

        assertThat(customers).isNotNull();
        assertThat(customers)
                .extracting(CustomerInfoResponse::name)
                .containsExactlyInAnyOrder(johnSnow.name(), alanKey.name());

        return customers;
    }

    private void testFindCustomer(Long customerId, CustomerDetailsResponse expected) {
        var response = restTemplate.exchange(get(PRODUCT_BASE_URL + "/" + customerId).build(), PRODUCT_DETAILS);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var customer = response.getBody();

        assertThat(customer).isNotNull();
        assertThat(customer).isEqualTo(expected);
    }

    private void testDeleteCustomer(Long customerId) {
        var response = restTemplate.exchange(delete(PRODUCT_BASE_URL + "/" + customerId)
                .build(), VOID);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    // @formatter:off
    static final String PRODUCT_BASE_URL = "/customers";

    static final ParameterizedTypeReference<Void> VOID = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<CustomerInfoResponse> PRODUCT_INFO = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<CustomerDetailsResponse> PRODUCT_DETAILS = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<List<CustomerInfoResponse>> PRODUCT_LIST = new ParameterizedTypeReference<>() {};
    //formatter:on
}