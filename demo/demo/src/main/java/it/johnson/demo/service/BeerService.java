package it.johnson.demo.service;

import it.johnson.demo.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeer();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO);

    Boolean deleteById(UUID beerId);

    void updateBeerPatchById(UUID beerId, BeerDTO beerDTO);
}
