package it.johnson.demo.service;

import it.johnson.demo.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();
    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    void updateCustomerById(UUID customerId, CustomerDTO customerDTO);

    void deleteById(UUID beerId);

    void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO);
}
