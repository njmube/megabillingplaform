package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_desc_estateService;
import org.megapractical.billingplatform.domain.Freecom_desc_estate;
import org.megapractical.billingplatform.repository.Freecom_desc_estateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_desc_estate.
 */
@Service
@Transactional
public class Freecom_desc_estateServiceImpl implements Freecom_desc_estateService{

    private final Logger log = LoggerFactory.getLogger(Freecom_desc_estateServiceImpl.class);
    
    @Inject
    private Freecom_desc_estateRepository freecom_desc_estateRepository;
    
    /**
     * Save a freecom_desc_estate.
     * 
     * @param freecom_desc_estate the entity to save
     * @return the persisted entity
     */
    public Freecom_desc_estate save(Freecom_desc_estate freecom_desc_estate) {
        log.debug("Request to save Freecom_desc_estate : {}", freecom_desc_estate);
        Freecom_desc_estate result = freecom_desc_estateRepository.save(freecom_desc_estate);
        return result;
    }

    /**
     *  Get all the freecom_desc_estates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_desc_estate> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_desc_estates");
        Page<Freecom_desc_estate> result = freecom_desc_estateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_desc_estate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_desc_estate findOne(Long id) {
        log.debug("Request to get Freecom_desc_estate : {}", id);
        Freecom_desc_estate freecom_desc_estate = freecom_desc_estateRepository.findOne(id);
        return freecom_desc_estate;
    }

    /**
     *  Delete the  freecom_desc_estate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_desc_estate : {}", id);
        freecom_desc_estateRepository.delete(id);
    }
}
