package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_fuel_consumptionService;
import org.megapractical.billingplatform.domain.Com_fuel_consumption;
import org.megapractical.billingplatform.repository.Com_fuel_consumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_fuel_consumption.
 */
@Service
@Transactional
public class Com_fuel_consumptionServiceImpl implements Com_fuel_consumptionService{

    private final Logger log = LoggerFactory.getLogger(Com_fuel_consumptionServiceImpl.class);
    
    @Inject
    private Com_fuel_consumptionRepository com_fuel_consumptionRepository;
    
    /**
     * Save a com_fuel_consumption.
     * 
     * @param com_fuel_consumption the entity to save
     * @return the persisted entity
     */
    public Com_fuel_consumption save(Com_fuel_consumption com_fuel_consumption) {
        log.debug("Request to save Com_fuel_consumption : {}", com_fuel_consumption);
        Com_fuel_consumption result = com_fuel_consumptionRepository.save(com_fuel_consumption);
        return result;
    }

    /**
     *  Get all the com_fuel_consumptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_fuel_consumption> findAll(Pageable pageable) {
        log.debug("Request to get all Com_fuel_consumptions");
        Page<Com_fuel_consumption> result = com_fuel_consumptionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_fuel_consumption by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_fuel_consumption findOne(Long id) {
        log.debug("Request to get Com_fuel_consumption : {}", id);
        Com_fuel_consumption com_fuel_consumption = com_fuel_consumptionRepository.findOne(id);
        return com_fuel_consumption;
    }

    /**
     *  Delete the  com_fuel_consumption by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_fuel_consumption : {}", id);
        com_fuel_consumptionRepository.delete(id);
    }
}
