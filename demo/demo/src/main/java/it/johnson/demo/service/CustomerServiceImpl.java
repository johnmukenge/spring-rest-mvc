package it.johnson.demo.service;

import it.johnson.demo.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService{
    Map<UUID, Customer> customerMap;
    public CustomerServiceImpl(){
        this.customerMap  = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PIPPO")
                .version("BAUDO")
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PLUTO")
                .version("BAUDO")
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PLUTO2")
                .version("BAUDO2")
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }
}
