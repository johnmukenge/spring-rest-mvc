package it.johnson.demo.controller;

import it.johnson.demo.model.Customer;
import it.johnson.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer){
        customerService.updateCustomerPatchById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){
        customerService.deleteById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("customerId")UUID customerId, @RequestBody Customer customer){

        customerService.updateBeerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(value = CUSTOMER_PATH)
    public ResponseEntity handlePost(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", CUSTOMER_PATH + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<Customer> listOfCustomers(){
        return customerService.listCustomers();
    }
    @GetMapping(value = CUSTOMER_PATH_ID)
    public Customer retrieveCustomer(@PathVariable("customerId") UUID customerId){
        return customerService.getCustomerById(customerId);
    }
}
