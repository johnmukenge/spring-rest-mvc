package it.johnson.demo.service;

import it.johnson.demo.mappers.BeerMapper;
import it.johnson.demo.model.BeerDTO;
import it.johnson.demo.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeer() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                        .orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {

        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(founder -> {
            founder.setBeerName(beerDTO.getBeerName());
            founder.setBeerStyle(beerDTO.getBeerStyle());
            founder.setUpc(beerDTO.getUpc());
            founder.setPrice(beerDTO.getPrice());

            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(founder))));

        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

    @Override
    public Boolean deleteById(UUID beerId) {

        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public void updateBeerPatchById(UUID beerId, BeerDTO beerDTO) {

    }
}
