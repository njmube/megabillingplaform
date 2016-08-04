package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_used_vehicleService;
import org.megapractical.billingplatform.domain.Freecom_used_vehicle;
import org.megapractical.billingplatform.repository.Freecom_used_vehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_used_vehicle.
 */
@Service
@Transactional
public class Freecom_used_vehicleServiceImpl implements Freecom_used_vehicleService{

    private final Logger log = LoggerFactory.getLogger(Freecom_used_vehicleServiceImpl.class);
    
    @Inject
    private Freecom_used_vehicleRepository freecom_used_vehicleRepository;
    
    /**
     * Save a freecom_used_vehicle.
     * 
     * @param freecom_used_vehicle the entity to save
     * @return the persisted entity
     */
    public Freecom_used_vehicle save(Freecom_used_vehicle freecom_used_vehicle) {
        log.debug("Request to save Freecom_used_vehicle : {}", freecom_used_vehicle);
        Freecom_used_vehicle result = freecom_used_vehicleRepository.save(freecom_used_vehicle);
        return result;
    }

    /**
     *  Get all the freecom_used_vehicles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_used_vehicle> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_used_vehicles");
        Page<Freecom_used_vehicle> result = freecom_used_vehicleRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_used_vehicle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_used_vehicle findOne(Long id) {
        log.debug("Request to get Freecom_used_vehicle : {}", id);
        Freecom_used_vehicle freecom_used_vehicle = freecom_used_vehicleRepository.findOne(id);
        return freecom_used_vehicle;
    }

    /**
     *  Delete the  freecom_used_vehicle by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_used_vehicle : {}", id);
        freecom_used_vehicleRepository.delete(id);
    }
}
