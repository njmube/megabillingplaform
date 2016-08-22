package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_custom_unitService;
import org.megapractical.billingplatform.domain.Freecom_custom_unit;
import org.megapractical.billingplatform.repository.Freecom_custom_unitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_custom_unit.
 */
@Service
@Transactional
public class Freecom_custom_unitServiceImpl implements Freecom_custom_unitService{

    private final Logger log = LoggerFactory.getLogger(Freecom_custom_unitServiceImpl.class);
    
    @Inject
    private Freecom_custom_unitRepository freecom_custom_unitRepository;
    
    /**
     * Save a freecom_custom_unit.
     * 
     * @param freecom_custom_unit the entity to save
     * @return the persisted entity
     */
    public Freecom_custom_unit save(Freecom_custom_unit freecom_custom_unit) {
        log.debug("Request to save Freecom_custom_unit : {}", freecom_custom_unit);
        Freecom_custom_unit result = freecom_custom_unitRepository.save(freecom_custom_unit);
        return result;
    }

    /**
     *  Get all the freecom_custom_units.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_custom_unit> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_custom_units");
        Page<Freecom_custom_unit> result = freecom_custom_unitRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_custom_unit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_custom_unit findOne(Long id) {
        log.debug("Request to get Freecom_custom_unit : {}", id);
        Freecom_custom_unit freecom_custom_unit = freecom_custom_unitRepository.findOne(id);
        return freecom_custom_unit;
    }

    /**
     *  Delete the  freecom_custom_unit by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_custom_unit : {}", id);
        freecom_custom_unitRepository.delete(id);
    }
}
