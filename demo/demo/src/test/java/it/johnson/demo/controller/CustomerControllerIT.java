package it.johnson.demo.controller;

import it.johnson.demo.entities.Beer;
import it.johnson.demo.entities.Customer;
import it.johnson.demo.mappers.CustomerMappers;
import it.johnson.demo.model.CustomerDTO;
import it.johnson.demo.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMappers customerMappers;

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound(){
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound(){
        assertThrows(NotFoundException.class, () ->{
            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void testCustomerIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.retrieveCustomer(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMappers.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.updateById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updateCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updateCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Transactional
    @Rollback
    @Test
    void saveCustomerTest(){
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testGetById(){
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.retrieveCustomer(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testListCustomers(){
        List<CustomerDTO> dtos = customerController.listOfCustomers();

        assertThat(dtos.size()).isEqualTo(3);

    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listOfCustomers();

        assertThat(dtos.size()).isEqualTo(0);

    }

}