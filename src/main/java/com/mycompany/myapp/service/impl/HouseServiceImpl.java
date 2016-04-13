package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.HouseService;
import com.mycompany.myapp.domain.House;
import com.mycompany.myapp.repository.HouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing House.
 */
@Service
@Transactional
public class HouseServiceImpl implements HouseService{

    private final Logger log = LoggerFactory.getLogger(HouseServiceImpl.class);
    
    @Inject
    private HouseRepository houseRepository;
    
    /**
     * Save a house.
     * 
     * @param house the entity to save
     * @return the persisted entity
     */
    public House save(House house) {
        log.debug("Request to save House : {}", house);
        House result = houseRepository.save(house);
        return result;
    }

    /**
     *  Get all the houses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<House> findAll() {
        log.debug("Request to get all Houses");
        List<House> result = houseRepository.findAll();
        return result;
    }

    /**
     *  Get one house by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public House findOne(Long id) {
        log.debug("Request to get House : {}", id);
        House house = houseRepository.findOne(id);
        return house;
    }

    /**
     *  Delete the  house by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete House : {}", id);
        houseRepository.delete(id);
    }
}
