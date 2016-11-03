package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_used_vehicleService;
import org.megapractical.billingplatform.domain.Com_used_vehicle;
import org.megapractical.billingplatform.repository.Com_used_vehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_used_vehicle.
 */
@Service
@Transactional
public class Com_used_vehicleServiceImpl implements Com_used_vehicleService{

    private final Logger log = LoggerFactory.getLogger(Com_used_vehicleServiceImpl.class);
    
    @Inject
    private Com_used_vehicleRepository com_used_vehicleRepository;
    
    /**
     * Save a com_used_vehicle.
     * 
     * @param com_used_vehicle the entity to save
     * @return the persisted entity
     */
    public Com_used_vehicle save(Com_used_vehicle com_used_vehicle) {
        log.debug("Request to save Com_used_vehicle : {}", com_used_vehicle);
        Com_used_vehicle result = com_used_vehicleRepository.save(com_used_vehicle);
        return result;
    }

    /**
     *  Get all the com_used_vehicles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_used_vehicle> findAll(Pageable pageable) {
        log.debug("Request to get all Com_used_vehicles");
        Page<Com_used_vehicle> result = com_used_vehicleRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_used_vehicle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_used_vehicle findOne(Long id) {
        log.debug("Request to get Com_used_vehicle : {}", id);
        Com_used_vehicle com_used_vehicle = com_used_vehicleRepository.findOne(id);
        return com_used_vehicle;
    }

    /**
     *  Delete the  com_used_vehicle by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_used_vehicle : {}", id);
        com_used_vehicleRepository.delete(id);
    }
}
