package it.johnson.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.johnson.demo.model.CustomerDTO;
import it.johnson.demo.service.CustomerService;
import it.johnson.demo.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customerDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerPatchById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(customerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(customerMap.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getCustomerName());

    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(customerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testUpdateNewCustomer() throws Exception {

        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        given(customerService.updateCustomerById(any(), any())).willReturn(Optional.of(customerDTO));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void testCreateNewCustomer() throws Exception {

        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);
        customerDTO.setVersion(null);
        customerDTO.setId(null);

        given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void listOfCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveCustomer() throws Exception {
        CustomerDTO customerDTOTester = customerService.listCustomers().get(0);
        given(customerService.getCustomerById(customerDTOTester.getId())).willReturn(Optional.of(customerDTOTester));
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, customerDTOTester.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customerDTOTester.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(customerDTOTester.getCustomerName())));

    }
}