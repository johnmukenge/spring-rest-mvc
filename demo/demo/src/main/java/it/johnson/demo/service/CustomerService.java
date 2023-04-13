package it.johnson.demo.service;

import it.johnson.demo.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();
    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);
    void updateBeerById(UUID customerId, Customer customer);

    void deleteById(UUID beerId);

    void updateCustomerPatchById(UUID customerId, Customer customer);
}
