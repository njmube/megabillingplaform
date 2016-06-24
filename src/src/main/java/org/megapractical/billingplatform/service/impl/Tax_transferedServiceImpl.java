package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_transferedService;
import org.megapractical.billingplatform.domain.Tax_transfered;
import org.megapractical.billingplatform.repository.Tax_transferedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tax_transfered.
 */
@Service
@Transactional
public class Tax_transferedServiceImpl implements Tax_transferedService{

    private final Logger log = LoggerFactory.getLogger(Tax_transferedServiceImpl.class);
    
    @Inject
    private Tax_transferedRepository tax_transferedRepository;
    
    /**
     * Save a tax_transfered.
     * 
     * @param tax_transfered the entity to save
     * @return the persisted entity
     */
    public Tax_transfered save(Tax_transfered tax_transfered) {
        log.debug("Request to save Tax_transfered : {}", tax_transfered);
        Tax_transfered result = tax_transferedRepository.save(tax_transfered);
        return result;
    }

    /**
     *  Get all the tax_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tax_transfered> findAll(Pageable pageable) {
        log.debug("Request to get all Tax_transfereds");
        Page<Tax_transfered> result = tax_transferedRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one tax_transfered by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tax_transfered findOne(Long id) {
        log.debug("Request to get Tax_transfered : {}", id);
        Tax_transfered tax_transfered = tax_transferedRepository.findOne(id);
        return tax_transfered;
    }

    /**
     *  Delete the  tax_transfered by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_transfered : {}", id);
        tax_transferedRepository.delete(id);
    }
}
