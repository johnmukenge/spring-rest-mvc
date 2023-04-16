package it.johnson.demo.service;

import it.johnson.demo.model.BeerDTO;
import it.johnson.demo.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService{

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beerDTO1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal((12.99)))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTO2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal((11.99)))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTO3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine city")
                .beerStyle(BeerStyle.IPA)
                .upc("12356222")
                .price(new BigDecimal((13.99)))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beerDTO1.getId(), beerDTO1);
        beerMap.put(beerDTO2.getId(), beerDTO2);
        beerMap.put(beerDTO3.getId(), beerDTO3);

    }

    @Override
    public List<BeerDTO> listBeer(){
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        log.debug("Get Beer Id service was called. Id: " + id.toString());

        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {

        BeerDTO savedBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .upc(beerDTO.getUpc())
                .version(1)
                .price(beerDTO.getPrice())
                .build();

        beerMap.put(savedBeerDTO.getId(), savedBeerDTO);
        return savedBeerDTO;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO existing = beerMap.get(beerId);
        existing.setBeerName(beerDTO.getBeerName());
        existing.setBeerStyle(beerDTO.getBeerStyle());
        existing.setPrice(beerDTO.getPrice());
        existing.setUpc(beerDTO.getUpc());
        existing.setQuantityOnHand(beerDTO.getQuantityOnHand());

        beerMap.put(existing.getId(), existing);

        return Optional.of(existing);
    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void updateBeerPatchById(UUID beerId, BeerDTO beerDTO) {
        //Beer existing = beerMap.get(beerId);
        /**if(StringUtils.hasText(beer.getBeerName())){
            existing.setBeerName(beer.getBeerName());
        }
        if(beer.getBeerStyle() != null){
            existing.setBeerStyle(beer.getBeerStyle());
        }
        if(beer.getPrice() != null){
            existing.setPrice(beer.getPrice());
        }
        if(beer.getQuantityOnHand() != null){
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }
        if(StringUtils.hasText(beer.getUpc())){
            existing.setUpc(beer.getUpc());
        }**/

        /**
         * This version of the code uses a lambda expression as the second argument
         * to the computeIfPresent() method. The lambda expression takes two arguments,
         * id and existing, and updates the fields of existing based on the values
         * of the corresponding fields in beer. The lambda expression then returns
         * the updated existing object, which is stored back into the map using
         * the computeIfPresent() method.
         * */

        beerMap.computeIfPresent(beerId, (id, existing) -> {
            if (StringUtils.hasText(beerDTO.getBeerName())) {
                existing.setBeerName(beerDTO.getBeerName());
            }
            if (beerDTO.getBeerStyle() != null) {
                existing.setBeerStyle(beerDTO.getBeerStyle());
            }
            if (beerDTO.getPrice() != null) {
                existing.setPrice(beerDTO.getPrice());
            }
            if (beerDTO.getQuantityOnHand() != null) {
                existing.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }
            if (StringUtils.hasText(beerDTO.getUpc())) {
                existing.setUpc(beerDTO.getUpc());
            }
            return existing;
        });

        /**
         * This version of the code uses a lambda expression as the second argument
         * to the computeIfPresent() method. The lambda expression takes two arguments,
         * id and existing, and updates the fields of existing based on the values
         * of the corresponding fields in beer. The lambda expression then returns
         * the updated existing object, which is stored back into the map using
         * the computeIfPresent() method.
         * */
    }
}
