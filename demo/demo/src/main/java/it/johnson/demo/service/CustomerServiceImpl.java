package it.johnson.demo.service;

import it.johnson.demo.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

@Service
public class CustomerServiceImpl implements CustomerService{
    Map<UUID, CustomerDTO> customerMap;
    public CustomerServiceImpl(){
        this.customerMap  = new HashMap<>();

        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("PIPPO")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("PLUTO")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("PLUTO2")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .build();

        customerMap.put(customerDTO1.getId(), customerDTO1);
        customerMap.put(customerDTO2.getId(), customerDTO2);
        customerMap.put(customerDTO3.getId(), customerDTO3);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        CustomerDTO customerDTOToSave = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .createDate(LocalDateTime.now())
                .lastDateModified(LocalDateTime.now())
                .version(customerDTO.getVersion())
                .customerName(customerDTO.getCustomerName())
                .build();
        customerMap.put(customerDTOToSave.getId(), customerDTOToSave);
        return customerDTOToSave;
    }

    @Override
    public void updateBeerById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO customerDTOToUpdate = CustomerDTO.builder()
                .id(customerId)
                .customerName(customerDTO.getCustomerName())
                .lastDateModified(LocalDateTime.now())
                .version(customerDTO.getVersion())
                .build();

        customerMap.put(customerDTOToUpdate.getId(), customerDTO);
    }

    @Override
    public void deleteById(UUID beerId) {
        customerMap.remove(beerId);
    }

    @Override
    public void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO existing  = customerMap.get(customerId);

        if(StringUtils.hasText(customerDTO.getCustomerName())){
            existing.setCustomerName(customerDTO.getCustomerName());
        }
        if(customerDTO.getVersion() != null ){
            existing.setVersion(customerDTO.getVersion());
        }
    }

    private static <T> void checkAndSetAttribute(Optional<T> value, Consumer<T> setter) {
        value.ifPresent(setter);
    }
}
