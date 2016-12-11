package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_desc_estateService;
import org.megapractical.billingplatform.domain.Com_desc_estate;
import org.megapractical.billingplatform.repository.Com_desc_estateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_desc_estate.
 */
@Service
@Transactional
public class Com_desc_estateServiceImpl implements Com_desc_estateService{

    private final Logger log = LoggerFactory.getLogger(Com_desc_estateServiceImpl.class);
    
    @Inject
    private Com_desc_estateRepository com_desc_estateRepository;
    
    /**
     * Save a com_desc_estate.
     * 
     * @param com_desc_estate the entity to save
     * @return the persisted entity
     */
    public Com_desc_estate save(Com_desc_estate com_desc_estate) {
        log.debug("Request to save Com_desc_estate : {}", com_desc_estate);
        Com_desc_estate result = com_desc_estateRepository.save(com_desc_estate);
        return result;
    }

    /**
     *  Get all the com_desc_estates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_desc_estate> findAll(Pageable pageable) {
        log.debug("Request to get all Com_desc_estates");
        Page<Com_desc_estate> result = com_desc_estateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_desc_estate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_desc_estate findOne(Long id) {
        log.debug("Request to get Com_desc_estate : {}", id);
        Com_desc_estate com_desc_estate = com_desc_estateRepository.findOne(id);
        return com_desc_estate;
    }

    /**
     *  Delete the  com_desc_estate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_desc_estate : {}", id);
        com_desc_estateRepository.delete(id);
    }
}
