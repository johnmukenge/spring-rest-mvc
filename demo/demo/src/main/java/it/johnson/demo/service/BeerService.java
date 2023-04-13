package it.johnson.demo.service;

import it.johnson.demo.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeer();

    Beer getBeerById(UUID id);

    Beer saveBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void updateBeerPatchById(UUID beerId, Beer beer);
}
