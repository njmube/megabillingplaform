package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_retentionsService;
import org.megapractical.billingplatform.domain.Tax_retentions;
import org.megapractical.billingplatform.repository.Tax_retentionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tax_retentions.
 */
@Service
@Transactional
public class Tax_retentionsServiceImpl implements Tax_retentionsService{

    private final Logger log = LoggerFactory.getLogger(Tax_retentionsServiceImpl.class);
    
    @Inject
    private Tax_retentionsRepository tax_retentionsRepository;
    
    /**
     * Save a tax_retentions.
     * 
     * @param tax_retentions the entity to save
     * @return the persisted entity
     */
    public Tax_retentions save(Tax_retentions tax_retentions) {
        log.debug("Request to save Tax_retentions : {}", tax_retentions);
        Tax_retentions result = tax_retentionsRepository.save(tax_retentions);
        return result;
    }

    /**
     *  Get all the tax_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tax_retentions> findAll(Pageable pageable) {
        log.debug("Request to get all Tax_retentions");
        Page<Tax_retentions> result = tax_retentionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one tax_retentions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tax_retentions findOne(Long id) {
        log.debug("Request to get Tax_retentions : {}", id);
        Tax_retentions tax_retentions = tax_retentionsRepository.findOne(id);
        return tax_retentions;
    }

    /**
     *  Delete the  tax_retentions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_retentions : {}", id);
        tax_retentionsRepository.delete(id);
    }
}
