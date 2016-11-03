package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_local_taxesService;
import org.megapractical.billingplatform.domain.Com_local_taxes;
import org.megapractical.billingplatform.repository.Com_local_taxesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_local_taxes.
 */
@Service
@Transactional
public class Com_local_taxesServiceImpl implements Com_local_taxesService{

    private final Logger log = LoggerFactory.getLogger(Com_local_taxesServiceImpl.class);
    
    @Inject
    private Com_local_taxesRepository com_local_taxesRepository;
    
    /**
     * Save a com_local_taxes.
     * 
     * @param com_local_taxes the entity to save
     * @return the persisted entity
     */
    public Com_local_taxes save(Com_local_taxes com_local_taxes) {
        log.debug("Request to save Com_local_taxes : {}", com_local_taxes);
        Com_local_taxes result = com_local_taxesRepository.save(com_local_taxes);
        return result;
    }

    /**
     *  Get all the com_local_taxes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_local_taxes> findAll(Pageable pageable) {
        log.debug("Request to get all Com_local_taxes");
        Page<Com_local_taxes> result = com_local_taxesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_local_taxes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_local_taxes findOne(Long id) {
        log.debug("Request to get Com_local_taxes : {}", id);
        Com_local_taxes com_local_taxes = com_local_taxesRepository.findOne(id);
        return com_local_taxes;
    }

    /**
     *  Delete the  com_local_taxes by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_local_taxes : {}", id);
        com_local_taxesRepository.delete(id);
    }
}
