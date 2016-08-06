package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_fuel_consumptionService;
import org.megapractical.billingplatform.domain.Freecom_fuel_consumption;
import org.megapractical.billingplatform.repository.Freecom_fuel_consumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_fuel_consumption.
 */
@Service
@Transactional
public class Freecom_fuel_consumptionServiceImpl implements Freecom_fuel_consumptionService{

    private final Logger log = LoggerFactory.getLogger(Freecom_fuel_consumptionServiceImpl.class);
    
    @Inject
    private Freecom_fuel_consumptionRepository freecom_fuel_consumptionRepository;
    
    /**
     * Save a freecom_fuel_consumption.
     * 
     * @param freecom_fuel_consumption the entity to save
     * @return the persisted entity
     */
    public Freecom_fuel_consumption save(Freecom_fuel_consumption freecom_fuel_consumption) {
        log.debug("Request to save Freecom_fuel_consumption : {}", freecom_fuel_consumption);
        Freecom_fuel_consumption result = freecom_fuel_consumptionRepository.save(freecom_fuel_consumption);
        return result;
    }

    /**
     *  Get all the freecom_fuel_consumptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_fuel_consumption> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_fuel_consumptions");
        Page<Freecom_fuel_consumption> result = freecom_fuel_consumptionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_fuel_consumption by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_fuel_consumption findOne(Long id) {
        log.debug("Request to get Freecom_fuel_consumption : {}", id);
        Freecom_fuel_consumption freecom_fuel_consumption = freecom_fuel_consumptionRepository.findOne(id);
        return freecom_fuel_consumption;
    }

    /**
     *  Delete the  freecom_fuel_consumption by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_fuel_consumption : {}", id);
        freecom_fuel_consumptionRepository.delete(id);
    }
}
