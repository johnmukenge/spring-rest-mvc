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
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("{customerId}")
    public ResponseEntity updateBeerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer){
        customerService.updateCustomerPatchById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){
        customerService.deleteById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId")UUID customerId, @RequestBody Customer customer){

        customerService.updateBeerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listOfCustomers(){
        return customerService.listCustomers();
    }
    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer retrieveCustomer(@PathVariable("customerId") UUID customerId){
        return customerService.getCustomerById(customerId);
    }
}
