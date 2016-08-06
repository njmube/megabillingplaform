package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_concept_fuelService;
import org.megapractical.billingplatform.domain.Freecom_concept_fuel;
import org.megapractical.billingplatform.repository.Freecom_concept_fuelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_concept_fuel.
 */
@Service
@Transactional
public class Freecom_concept_fuelServiceImpl implements Freecom_concept_fuelService{

    private final Logger log = LoggerFactory.getLogger(Freecom_concept_fuelServiceImpl.class);
    
    @Inject
    private Freecom_concept_fuelRepository freecom_concept_fuelRepository;
    
    /**
     * Save a freecom_concept_fuel.
     * 
     * @param freecom_concept_fuel the entity to save
     * @return the persisted entity
     */
    public Freecom_concept_fuel save(Freecom_concept_fuel freecom_concept_fuel) {
        log.debug("Request to save Freecom_concept_fuel : {}", freecom_concept_fuel);
        Freecom_concept_fuel result = freecom_concept_fuelRepository.save(freecom_concept_fuel);
        return result;
    }

    /**
     *  Get all the freecom_concept_fuels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_concept_fuel> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_concept_fuels");
        Page<Freecom_concept_fuel> result = freecom_concept_fuelRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_concept_fuel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_concept_fuel findOne(Long id) {
        log.debug("Request to get Freecom_concept_fuel : {}", id);
        Freecom_concept_fuel freecom_concept_fuel = freecom_concept_fuelRepository.findOne(id);
        return freecom_concept_fuel;
    }

    /**
     *  Delete the  freecom_concept_fuel by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_concept_fuel : {}", id);
        freecom_concept_fuelRepository.delete(id);
    }
}
