package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Rate_typeService;
import org.megapractical.billingplatform.domain.Rate_type;
import org.megapractical.billingplatform.repository.Rate_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Rate_type.
 */
@Service
@Transactional
public class Rate_typeServiceImpl implements Rate_typeService{

    private final Logger log = LoggerFactory.getLogger(Rate_typeServiceImpl.class);
    
    @Inject
    private Rate_typeRepository rate_typeRepository;
    
    /**
     * Save a rate_type.
     * 
     * @param rate_type the entity to save
     * @return the persisted entity
     */
    public Rate_type save(Rate_type rate_type) {
        log.debug("Request to save Rate_type : {}", rate_type);
        Rate_type result = rate_typeRepository.save(rate_type);
        return result;
    }

    /**
     *  Get all the rate_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Rate_type> findAll(Pageable pageable) {
        log.debug("Request to get all Rate_types");
        Page<Rate_type> result = rate_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one rate_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Rate_type findOne(Long id) {
        log.debug("Request to get Rate_type : {}", id);
        Rate_type rate_type = rate_typeRepository.findOne(id);
        return rate_type;
    }

    /**
     *  Delete the  rate_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Rate_type : {}", id);
        rate_typeRepository.delete(id);
    }
}
