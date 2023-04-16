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
import java.util.stream.Collectors;

/**
 * Why use Java optional?
 * Using Java Optional is generally considered a best practice since it indicates
 * the return value may be null and reduces null type checking.
 * Using optional also helps reduce unintentional Null pointer errors at runtime.
 * */

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMappers customerMappers;
    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMappers::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMappers.customerToCustomerDto(customerRepository.findById(id)
                     .orElse(null)));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        return customerMappers.customerToCustomerDto(customerRepository.save(customerMappers.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        customerRepository.findById(customerId).ifPresent(founder -> {
            founder.setCustomerName(customerDTO.getCustomerName());

            customerRepository.save(founder);
        });

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO) {

    }
}
