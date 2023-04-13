package it.johnson.demo.service;

import it.johnson.demo.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

@Service
public class CustomerServiceImpl implements CustomerService{
    Map<UUID, Customer> customerMap;
    public CustomerServiceImpl(){
        this.customerMap  = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PIPPO")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PLUTO")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("PLUTO2")
                .version(1)
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

    @Override
    public Customer saveCustomer(Customer customer) {

        Customer customerToSave = Customer.builder()
                .id(UUID.randomUUID())
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .version(customer.getVersion())
                .customerName(customer.getCustomerName())
                .build();
        customerMap.put(customerToSave.getId(), customerToSave);
        return customerToSave;
    }

    @Override
    public void updateBeerById(UUID customerId, Customer customer) {
        Customer customerToUpdate = Customer.builder()
                .id(customerId)
                .customerName(customer.getCustomerName())
                .lastDateModified(LocalDateTime.now())
                .version(customer.getVersion())
                .build();

        customerMap.put(customerToUpdate.getId(), customer);
    }

    @Override
    public void deleteById(UUID beerId) {
        customerMap.remove(beerId);
    }

    @Override
    public void updateCustomerPatchById(UUID customerId, Customer customer) {
        Customer existing  = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }
        if(customer.getVersion() != null ){
            existing.setVersion(customer.getVersion());
        }
    }

    private static <T> void checkAndSetAttribute(Optional<T> value, Consumer<T> setter) {
        value.ifPresent(setter);
    }
}
