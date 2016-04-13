package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ElectronicDevice;
import com.mycompany.myapp.service.ElectronicDeviceService;
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
 * REST controller for managing ElectronicDevice.
 */
@RestController
@RequestMapping("/api")
public class ElectronicDeviceResource {

    private final Logger log = LoggerFactory.getLogger(ElectronicDeviceResource.class);
        
    @Inject
    private ElectronicDeviceService electronicDeviceService;
    
    /**
     * POST  /electronic-devices : Create a new electronicDevice.
     *
     * @param electronicDevice the electronicDevice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new electronicDevice, or with status 400 (Bad Request) if the electronicDevice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/electronic-devices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElectronicDevice> createElectronicDevice(@Valid @RequestBody ElectronicDevice electronicDevice) throws URISyntaxException {
        log.debug("REST request to save ElectronicDevice : {}", electronicDevice);
        if (electronicDevice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("electronicDevice", "idexists", "A new electronicDevice cannot already have an ID")).body(null);
        }
        ElectronicDevice result = electronicDeviceService.save(electronicDevice);
        return ResponseEntity.created(new URI("/api/electronic-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("electronicDevice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /electronic-devices : Updates an existing electronicDevice.
     *
     * @param electronicDevice the electronicDevice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated electronicDevice,
     * or with status 400 (Bad Request) if the electronicDevice is not valid,
     * or with status 500 (Internal Server Error) if the electronicDevice couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/electronic-devices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElectronicDevice> updateElectronicDevice(@Valid @RequestBody ElectronicDevice electronicDevice) throws URISyntaxException {
        log.debug("REST request to update ElectronicDevice : {}", electronicDevice);
        if (electronicDevice.getId() == null) {
            return createElectronicDevice(electronicDevice);
        }
        ElectronicDevice result = electronicDeviceService.save(electronicDevice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("electronicDevice", electronicDevice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /electronic-devices : get all the electronicDevices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of electronicDevices in body
     */
    @RequestMapping(value = "/electronic-devices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ElectronicDevice> getAllElectronicDevices() {
        log.debug("REST request to get all ElectronicDevices");
        return electronicDeviceService.findAll();
    }

    /**
     * GET  /electronic-devices/:id : get the "id" electronicDevice.
     *
     * @param id the id of the electronicDevice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the electronicDevice, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/electronic-devices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElectronicDevice> getElectronicDevice(@PathVariable Long id) {
        log.debug("REST request to get ElectronicDevice : {}", id);
        ElectronicDevice electronicDevice = electronicDeviceService.findOne(id);
        return Optional.ofNullable(electronicDevice)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /electronic-devices/:id : delete the "id" electronicDevice.
     *
     * @param id the id of the electronicDevice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/electronic-devices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteElectronicDevice(@PathVariable Long id) {
        log.debug("REST request to delete ElectronicDevice : {}", id);
        electronicDeviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("electronicDevice", id.toString())).build();
    }

}
