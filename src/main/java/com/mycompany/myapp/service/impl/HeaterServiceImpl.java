package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.HeaterService;
import com.mycompany.myapp.domain.Heater;
import com.mycompany.myapp.repository.HeaterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Heater.
 */
@Service
@Transactional
public class HeaterServiceImpl implements HeaterService{

    private final Logger log = LoggerFactory.getLogger(HeaterServiceImpl.class);
    
    @Inject
    private HeaterRepository heaterRepository;
    
    /**
     * Save a heater.
     * 
     * @param heater the entity to save
     * @return the persisted entity
     */
    public Heater save(Heater heater) {
        log.debug("Request to save Heater : {}", heater);
        Heater result = heaterRepository.save(heater);
        return result;
    }

    /**
     *  Get all the heaters.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Heater> findAll() {
        log.debug("Request to get all Heaters");
        List<Heater> result = heaterRepository.findAll();
        return result;
    }

    /**
     *  Get one heater by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Heater findOne(Long id) {
        log.debug("Request to get Heater : {}", id);
        Heater heater = heaterRepository.findOne(id);
        return heater;
    }

    /**
     *  Delete the  heater by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Heater : {}", id);
        heaterRepository.delete(id);
    }
}
