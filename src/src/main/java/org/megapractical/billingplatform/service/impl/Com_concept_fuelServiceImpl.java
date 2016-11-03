package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_concept_fuelService;
import org.megapractical.billingplatform.domain.Com_concept_fuel;
import org.megapractical.billingplatform.repository.Com_concept_fuelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_concept_fuel.
 */
@Service
@Transactional
public class Com_concept_fuelServiceImpl implements Com_concept_fuelService{

    private final Logger log = LoggerFactory.getLogger(Com_concept_fuelServiceImpl.class);
    
    @Inject
    private Com_concept_fuelRepository com_concept_fuelRepository;
    
    /**
     * Save a com_concept_fuel.
     * 
     * @param com_concept_fuel the entity to save
     * @return the persisted entity
     */
    public Com_concept_fuel save(Com_concept_fuel com_concept_fuel) {
        log.debug("Request to save Com_concept_fuel : {}", com_concept_fuel);
        Com_concept_fuel result = com_concept_fuelRepository.save(com_concept_fuel);
        return result;
    }

    /**
     *  Get all the com_concept_fuels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_concept_fuel> findAll(Pageable pageable) {
        log.debug("Request to get all Com_concept_fuels");
        Page<Com_concept_fuel> result = com_concept_fuelRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_concept_fuel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_concept_fuel findOne(Long id) {
        log.debug("Request to get Com_concept_fuel : {}", id);
        Com_concept_fuel com_concept_fuel = com_concept_fuelRepository.findOne(id);
        return com_concept_fuel;
    }

    /**
     *  Delete the  com_concept_fuel by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_concept_fuel : {}", id);
        com_concept_fuelRepository.delete(id);
    }
}
