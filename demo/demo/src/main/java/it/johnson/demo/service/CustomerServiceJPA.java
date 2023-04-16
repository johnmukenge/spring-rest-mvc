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
import java.util.concurrent.atomic.AtomicReference;
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
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(founder -> {
            founder.setCustomerName(customerDTO.getCustomerName());

            atomicReference.set(Optional.of(customerMappers.customerToCustomerDto(customerRepository.save(founder))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

    @Override
    public Boolean deleteById(UUID customerId) {
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO) {

    }
}
