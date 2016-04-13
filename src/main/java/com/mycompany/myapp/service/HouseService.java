package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.House;

import java.util.List;

/**
 * Service Interface for managing House.
 */
public interface HouseService {

    /**
     * Save a house.
     * 
     * @param house the entity to save
     * @return the persisted entity
     */
    House save(House house);

    /**
     *  Get all the houses.
     *  
     *  @return the list of entities
     */
    List<House> findAll();

    /**
     *  Get the "id" house.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    House findOne(Long id);

    /**
     *  Delete the "id" house.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
