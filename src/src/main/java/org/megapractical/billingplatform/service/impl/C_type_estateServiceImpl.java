package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_type_estateService;
import org.megapractical.billingplatform.domain.C_type_estate;
import org.megapractical.billingplatform.repository.C_type_estateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_type_estate.
 */
@Service
@Transactional
public class C_type_estateServiceImpl implements C_type_estateService{

    private final Logger log = LoggerFactory.getLogger(C_type_estateServiceImpl.class);
    
    @Inject
    private C_type_estateRepository c_type_estateRepository;
    
    /**
     * Save a c_type_estate.
     * 
     * @param c_type_estate the entity to save
     * @return the persisted entity
     */
    public C_type_estate save(C_type_estate c_type_estate) {
        log.debug("Request to save C_type_estate : {}", c_type_estate);
        C_type_estate result = c_type_estateRepository.save(c_type_estate);
        return result;
    }

    /**
     *  Get all the c_type_estates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_type_estate> findAll(Pageable pageable) {
        log.debug("Request to get all C_type_estates");
        Page<C_type_estate> result = c_type_estateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_type_estate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_type_estate findOne(Long id) {
        log.debug("Request to get C_type_estate : {}", id);
        C_type_estate c_type_estate = c_type_estateRepository.findOne(id);
        return c_type_estate;
    }

    /**
     *  Delete the  c_type_estate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_type_estate : {}", id);
        c_type_estateRepository.delete(id);
    }
}
