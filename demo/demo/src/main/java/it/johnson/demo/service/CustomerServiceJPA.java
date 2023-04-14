package it.johnson.demo.service;

import it.johnson.demo.mappers.CustomerMappers;
import it.johnson.demo.model.CustomerDTO;
import it.johnson.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMappers customerMappers;
    @Override
    public List<CustomerDTO> listCustomers() {
        return null;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void updateBeerById(UUID customerId, CustomerDTO customerDTO) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO) {

    }
}
