package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ElectronicDeviceService;
import com.mycompany.myapp.domain.ElectronicDevice;
import com.mycompany.myapp.repository.ElectronicDeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ElectronicDevice.
 */
@Service
@Transactional
public class ElectronicDeviceServiceImpl implements ElectronicDeviceService{

    private final Logger log = LoggerFactory.getLogger(ElectronicDeviceServiceImpl.class);
    
    @Inject
    private ElectronicDeviceRepository electronicDeviceRepository;
    
    /**
     * Save a electronicDevice.
     * 
     * @param electronicDevice the entity to save
     * @return the persisted entity
     */
    public ElectronicDevice save(ElectronicDevice electronicDevice) {
        log.debug("Request to save ElectronicDevice : {}", electronicDevice);
        ElectronicDevice result = electronicDeviceRepository.save(electronicDevice);
        return result;
    }

    /**
     *  Get all the electronicDevices.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ElectronicDevice> findAll() {
        log.debug("Request to get all ElectronicDevices");
        List<ElectronicDevice> result = electronicDeviceRepository.findAll();
        return result;
    }

    /**
     *  Get one electronicDevice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ElectronicDevice findOne(Long id) {
        log.debug("Request to get ElectronicDevice : {}", id);
        ElectronicDevice electronicDevice = electronicDeviceRepository.findOne(id);
        return electronicDevice;
    }

    /**
     *  Delete the  electronicDevice by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ElectronicDevice : {}", id);
        electronicDeviceRepository.delete(id);
    }
}
