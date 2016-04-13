package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Heater;

import java.util.List;

/**
 * Service Interface for managing Heater.
 */
public interface HeaterService {

    /**
     * Save a heater.
     * 
     * @param heater the entity to save
     * @return the persisted entity
     */
    Heater save(Heater heater);

    /**
     *  Get all the heaters.
     *  
     *  @return the list of entities
     */
    List<Heater> findAll();

    /**
     *  Get the "id" heater.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Heater findOne(Long id);

    /**
     *  Delete the "id" heater.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
