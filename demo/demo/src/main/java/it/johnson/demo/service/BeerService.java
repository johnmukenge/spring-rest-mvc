package it.johnson.demo.service;

import it.johnson.demo.model.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}
