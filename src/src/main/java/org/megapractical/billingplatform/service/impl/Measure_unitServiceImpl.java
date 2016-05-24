package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Measure_unitService;
import org.megapractical.billingplatform.domain.Measure_unit;
import org.megapractical.billingplatform.repository.Measure_unitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Measure_unit.
 */
@Service
@Transactional
public class Measure_unitServiceImpl implements Measure_unitService{

    private final Logger log = LoggerFactory.getLogger(Measure_unitServiceImpl.class);
    
    @Inject
    private Measure_unitRepository measure_unitRepository;
    
    /**
     * Save a measure_unit.
     * 
     * @param measure_unit the entity to save
     * @return the persisted entity
     */
    public Measure_unit save(Measure_unit measure_unit) {
        log.debug("Request to save Measure_unit : {}", measure_unit);
        Measure_unit result = measure_unitRepository.save(measure_unit);
        return result;
    }

    /**
     *  Get all the measure_units.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Measure_unit> findAll(Pageable pageable) {
        log.debug("Request to get all Measure_units");
        Page<Measure_unit> result = measure_unitRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one measure_unit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Measure_unit findOne(Long id) {
        log.debug("Request to get Measure_unit : {}", id);
        Measure_unit measure_unit = measure_unitRepository.findOne(id);
        return measure_unit;
    }

    /**
     *  Delete the  measure_unit by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Measure_unit : {}", id);
        measure_unitRepository.delete(id);
    }
}
