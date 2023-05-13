package com.arturfrimu.customer.component;

import com.arturfrimu.customer.dto.request.CreateCustomerRequest;
import com.arturfrimu.customer.dto.response.CustomerDetailsResponse;
import com.arturfrimu.customer.dto.response.CustomerInfoResponse;
import com.arturfrimu.customer.repository.CustomerRepository;
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
class ComponentTest {

    @Autowired
    BaseRestTemplate restTemplate;
    @Autowired
    CustomerRepository customerRepository; // TODO: 01/05/2023 remove me

    @Test
    public void testLifecycle() {
        customerRepository.deleteAll(); // TODO: 01/05/2023 Use test containers instead

        testCreateCustomer(new CreateCustomerRequest( // TODO: 01/05/2023 Replace object creation with json format
                "John Snow",
                "johnsnow@gmail.com",
                "Oakwood Lane 33"
        ));

        testCreateCustomer(new CreateCustomerRequest(
                        "Alan key",
                        "alankey@gmail.com",
                        "Maple Terrace 79"
                )
        );

        var customers = testListCustomers();

        var nameToId = customers.stream().collect(toMap(CustomerInfoResponse::getName, CustomerInfoResponse::getCustomerId));

        testFindCustomer(nameToId.get("John Snow"), new CustomerDetailsResponse(
                nameToId.get("John Snow"),
                "John Snow",
                "johnsnow@gmail.com",
                "Oakwood Lane 33"
        ));

        testFindCustomer(nameToId.get("Alan key"), new CustomerDetailsResponse(
                nameToId.get("Alan key"),
                "Alan key",
                "alankey@gmail.com",
                "Maple Terrace 79"
        ));

        testDeleteCustomer(nameToId.get("Alan key"));
        testDeleteCustomer(nameToId.get("John Snow"));
    }

    private void testCreateCustomer(CreateCustomerRequest body) {
        var response = restTemplate.exchange(post(PRODUCT_BASE_URL).body(body), PRODUCT_INFO);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var createdCustomer = response.getBody();

        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.getCustomerId()).isNotNull();
        assertThat(createdCustomer.getName()).isEqualTo(body.getName());
    }

    private List<CustomerInfoResponse> testListCustomers() {
        var response = restTemplate.exchange(get(PRODUCT_BASE_URL).build(), PRODUCT_LIST);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var customers = response.getBody();

        assertThat(customers).isNotNull();
        assertThat(customers)
                .extracting(CustomerInfoResponse::getName)
                .containsExactlyInAnyOrder("John Snow", "Alan key");

        return customers;
    }

    private void testFindCustomer(Long customerId, CustomerDetailsResponse expected) {
        var response = restTemplate
                .exchange(get(PRODUCT_BASE_URL + "/" + customerId).build(), PRODUCT_DETAILS);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        var customer = response.getBody();

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