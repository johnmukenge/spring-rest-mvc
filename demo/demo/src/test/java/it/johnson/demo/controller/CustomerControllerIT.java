package it.johnson.demo.controller;

import it.johnson.demo.entities.Customer;
import it.johnson.demo.model.CustomerDTO;
import it.johnson.demo.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    void testCustomerIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.retrieveCustomer(UUID.randomUUID());
        });
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