package it.johnson.demo.bootstrap;

import it.johnson.demo.entities.Beer;
import it.johnson.demo.entities.Customer;
import it.johnson.demo.model.BeerStyle;
import it.johnson.demo.repositories.BeerRepository;
import it.johnson.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;


    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData(){
        if(beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("34453452")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine city")
                    .beerStyle(BeerStyle.IPA)
                    .upc("3242324")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(7645)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

    }

    public void loadCustomerData(){
        if(customerRepository.count() == 0){
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Customer 1")
                    .version(1)
                    .createDate(LocalDateTime.now())
                    .lastDateModified(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Customer 2")
                    .version(1)
                    .createDate(LocalDateTime.now())
                    .lastDateModified(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Customer 3")
                    .version(1)
                    .createDate(LocalDateTime.now())
                    .lastDateModified(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
