package it.johnson.demo.controller;

import it.johnson.demo.model.Beer;
import it.johnson.demo.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    public Beer getBeerById(UUID id){
        log.debug("Get Beer Id Controller was called");
        return beerService.getBeerById(id);
    }

}
