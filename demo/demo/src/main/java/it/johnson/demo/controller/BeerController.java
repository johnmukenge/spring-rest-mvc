package it.johnson.demo.controller;

import it.johnson.demo.model.Beer;
import it.johnson.demo.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {

    private final BeerService beerService;

    @RequestMapping("api/v1/beer")
    public List<Beer> listBeers(){
        return beerService.listBeer();
    }

    public Beer getBeerById(UUID id){
        log.debug("Get Beer Id Controller was called");
        return beerService.getBeerById(id);
    }

}
