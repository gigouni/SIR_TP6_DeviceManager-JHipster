package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Heater;
import com.mycompany.myapp.service.HeaterService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Heater.
 */
@RestController
@RequestMapping("/api")
public class HeaterResource {

    private final Logger log = LoggerFactory.getLogger(HeaterResource.class);
        
    @Inject
    private HeaterService heaterService;
    
    /**
     * POST  /heaters : Create a new heater.
     *
     * @param heater the heater to create
     * @return the ResponseEntity with status 201 (Created) and with body the new heater, or with status 400 (Bad Request) if the heater has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/heaters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Heater> createHeater(@Valid @RequestBody Heater heater) throws URISyntaxException {
        log.debug("REST request to save Heater : {}", heater);
        if (heater.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heater", "idexists", "A new heater cannot already have an ID")).body(null);
        }
        Heater result = heaterService.save(heater);
        return ResponseEntity.created(new URI("/api/heaters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heater", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heaters : Updates an existing heater.
     *
     * @param heater the heater to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated heater,
     * or with status 400 (Bad Request) if the heater is not valid,
     * or with status 500 (Internal Server Error) if the heater couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/heaters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Heater> updateHeater(@Valid @RequestBody Heater heater) throws URISyntaxException {
        log.debug("REST request to update Heater : {}", heater);
        if (heater.getId() == null) {
            return createHeater(heater);
        }
        Heater result = heaterService.save(heater);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heater", heater.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heaters : get all the heaters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of heaters in body
     */
    @RequestMapping(value = "/heaters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Heater> getAllHeaters() {
        log.debug("REST request to get all Heaters");
        return heaterService.findAll();
    }

    /**
     * GET  /heaters/:id : get the "id" heater.
     *
     * @param id the id of the heater to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the heater, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/heaters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Heater> getHeater(@PathVariable Long id) {
        log.debug("REST request to get Heater : {}", id);
        Heater heater = heaterService.findOne(id);
        return Optional.ofNullable(heater)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heaters/:id : delete the "id" heater.
     *
     * @param id the id of the heater to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/heaters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeater(@PathVariable Long id) {
        log.debug("REST request to delete Heater : {}", id);
        heaterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heater", id.toString())).build();
    }

}
