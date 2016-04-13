package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ElectronicDevice;

import java.util.List;

/**
 * Service Interface for managing ElectronicDevice.
 */
public interface ElectronicDeviceService {

    /**
     * Save a electronicDevice.
     * 
     * @param electronicDevice the entity to save
     * @return the persisted entity
     */
    ElectronicDevice save(ElectronicDevice electronicDevice);

    /**
     *  Get all the electronicDevices.
     *  
     *  @return the list of entities
     */
    List<ElectronicDevice> findAll();

    /**
     *  Get the "id" electronicDevice.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ElectronicDevice findOne(Long id);

    /**
     *  Delete the "id" electronicDevice.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
