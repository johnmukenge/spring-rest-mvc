package it.johnson.demo.service;

import it.johnson.demo.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeer();

    Optional<Beer> getBeerById(UUID id);

    Beer saveBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void updateBeerPatchById(UUID beerId, Beer beer);
}
