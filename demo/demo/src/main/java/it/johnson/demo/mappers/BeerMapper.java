package it.johnson.demo.mappers;

import it.johnson.demo.entities.Beer;
import it.johnson.demo.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDto(Beer beer);
}
