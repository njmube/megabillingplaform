package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_tax_rateService;
import org.megapractical.billingplatform.domain.C_tax_rate;
import org.megapractical.billingplatform.repository.C_tax_rateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_tax_rate.
 */
@Service
@Transactional
public class C_tax_rateServiceImpl implements C_tax_rateService{

    private final Logger log = LoggerFactory.getLogger(C_tax_rateServiceImpl.class);
    
    @Inject
    private C_tax_rateRepository c_tax_rateRepository;
    
    /**
     * Save a c_tax_rate.
     * 
     * @param c_tax_rate the entity to save
     * @return the persisted entity
     */
    public C_tax_rate save(C_tax_rate c_tax_rate) {
        log.debug("Request to save C_tax_rate : {}", c_tax_rate);
        C_tax_rate result = c_tax_rateRepository.save(c_tax_rate);
        return result;
    }

    /**
     *  Get all the c_tax_rates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_tax_rate> findAll(Pageable pageable) {
        log.debug("Request to get all C_tax_rates");
        Page<C_tax_rate> result = c_tax_rateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_tax_rate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_tax_rate findOne(Long id) {
        log.debug("Request to get C_tax_rate : {}", id);
        C_tax_rate c_tax_rate = c_tax_rateRepository.findOne(id);
        return c_tax_rate;
    }

    /**
     *  Delete the  c_tax_rate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_tax_rate : {}", id);
        c_tax_rateRepository.delete(id);
    }
}
