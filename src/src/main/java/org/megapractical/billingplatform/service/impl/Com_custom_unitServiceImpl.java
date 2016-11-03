package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_custom_unitService;
import org.megapractical.billingplatform.domain.Com_custom_unit;
import org.megapractical.billingplatform.repository.Com_custom_unitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_custom_unit.
 */
@Service
@Transactional
public class Com_custom_unitServiceImpl implements Com_custom_unitService{

    private final Logger log = LoggerFactory.getLogger(Com_custom_unitServiceImpl.class);
    
    @Inject
    private Com_custom_unitRepository com_custom_unitRepository;
    
    /**
     * Save a com_custom_unit.
     * 
     * @param com_custom_unit the entity to save
     * @return the persisted entity
     */
    public Com_custom_unit save(Com_custom_unit com_custom_unit) {
        log.debug("Request to save Com_custom_unit : {}", com_custom_unit);
        Com_custom_unit result = com_custom_unitRepository.save(com_custom_unit);
        return result;
    }

    /**
     *  Get all the com_custom_units.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_custom_unit> findAll(Pageable pageable) {
        log.debug("Request to get all Com_custom_units");
        Page<Com_custom_unit> result = com_custom_unitRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_custom_unit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_custom_unit findOne(Long id) {
        log.debug("Request to get Com_custom_unit : {}", id);
        Com_custom_unit com_custom_unit = com_custom_unitRepository.findOne(id);
        return com_custom_unit;
    }

    /**
     *  Delete the  com_custom_unit by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_custom_unit : {}", id);
        com_custom_unitRepository.delete(id);
    }
}
