package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.House;
import com.mycompany.myapp.service.HouseService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing House.
 */
@RestController
@RequestMapping("/api")
public class HouseResource {

    private final Logger log = LoggerFactory.getLogger(HouseResource.class);
        
    @Inject
    private HouseService houseService;
    
    /**
     * POST  /houses : Create a new house.
     *
     * @param house the house to create
     * @return the ResponseEntity with status 201 (Created) and with body the new house, or with status 400 (Bad Request) if the house has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/houses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<House> createHouse(@RequestBody House house) throws URISyntaxException {
        log.debug("REST request to save House : {}", house);
        if (house.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("house", "idexists", "A new house cannot already have an ID")).body(null);
        }
        House result = houseService.save(house);
        return ResponseEntity.created(new URI("/api/houses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("house", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /houses : Updates an existing house.
     *
     * @param house the house to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated house,
     * or with status 400 (Bad Request) if the house is not valid,
     * or with status 500 (Internal Server Error) if the house couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/houses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<House> updateHouse(@RequestBody House house) throws URISyntaxException {
        log.debug("REST request to update House : {}", house);
        if (house.getId() == null) {
            return createHouse(house);
        }
        House result = houseService.save(house);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("house", house.getId().toString()))
            .body(result);
    }

    /**
     * GET  /houses : get all the houses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of houses in body
     */
    @RequestMapping(value = "/houses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<House> getAllHouses() {
        log.debug("REST request to get all Houses");
        return houseService.findAll();
    }

    /**
     * GET  /houses/:id : get the "id" house.
     *
     * @param id the id of the house to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the house, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/houses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<House> getHouse(@PathVariable Long id) {
        log.debug("REST request to get House : {}", id);
        House house = houseService.findOne(id);
        return Optional.ofNullable(house)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /houses/:id : delete the "id" house.
     *
     * @param id the id of the house to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/houses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        log.debug("REST request to delete House : {}", id);
        houseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("house", id.toString())).build();
    }

}
