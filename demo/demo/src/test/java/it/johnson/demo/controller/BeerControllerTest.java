package it.johnson.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.johnson.demo.model.Beer;
import it.johnson.demo.service.BeerService;
import it.johnson.demo.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //@Autowired
    //BeerController beerController;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void updateBeerPatchById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateById() {
    }
    @BeforeEach
    void setUp(){
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testCreateNewBeer() throws Exception {

        Beer beer = beerServiceImpl.listBeer().get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeer().get(1));

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));


    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeer()).willReturn(beerServiceImpl.listBeer());

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getBeerById() throws Exception {
        Beer beerTester = beerServiceImpl.listBeer().get(0);

        given(beerService.getBeerById(beerTester.getId())).willReturn(beerTester);

        mockMvc.perform(get("/api/v1/beer/" + beerTester.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beerTester.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(beerTester.getBeerName())));

        //System.out.println(beerController.getBeerById(UUID.randomUUID()));
    }
}