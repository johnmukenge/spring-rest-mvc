package it.johnson.demo.controller;

import it.johnson.demo.model.Customer;
import it.johnson.demo.service.CustomerService;
import it.johnson.demo.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    /**
     * Why use Spring MockMVC?
     * Spring MockMVC allows you to test the controller interactions in a servlet
     * context without the application running in a application server.
     * MockMVC provides us a test environment to test a spring MVC controller.
     * It does not provides the full spring context but it allows us to test easily a specific controller.
     * */

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void updateBeerPatchById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void handlePost() {
    }

    @Test
    void listOfCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void retrieveCustomer() throws Exception {
        Customer customerTester = customerService.listCustomers().get(0);
        given(customerService.getCustomerById(customerTester.getId())).willReturn(customerTester);
        mockMvc.perform(get("/api/v1/customer/" + customerTester.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customerTester.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(customerTester.getCustomerName())));

    }
}