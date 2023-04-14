package it.johnson.demo.mappers;

import it.johnson.demo.entities.Customer;
import it.johnson.demo.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMappers {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);
}
