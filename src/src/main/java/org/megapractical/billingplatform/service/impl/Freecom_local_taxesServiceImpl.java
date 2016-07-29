package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_local_taxesService;
import org.megapractical.billingplatform.domain.Freecom_local_taxes;
import org.megapractical.billingplatform.repository.Freecom_local_taxesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_local_taxes.
 */
@Service
@Transactional
public class Freecom_local_taxesServiceImpl implements Freecom_local_taxesService{

    private final Logger log = LoggerFactory.getLogger(Freecom_local_taxesServiceImpl.class);
    
    @Inject
    private Freecom_local_taxesRepository freecom_local_taxesRepository;
    
    /**
     * Save a freecom_local_taxes.
     * 
     * @param freecom_local_taxes the entity to save
     * @return the persisted entity
     */
    public Freecom_local_taxes save(Freecom_local_taxes freecom_local_taxes) {
        log.debug("Request to save Freecom_local_taxes : {}", freecom_local_taxes);
        Freecom_local_taxes result = freecom_local_taxesRepository.save(freecom_local_taxes);
        return result;
    }

    /**
     *  Get all the freecom_local_taxes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_local_taxes> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_local_taxes");
        Page<Freecom_local_taxes> result = freecom_local_taxesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_local_taxes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_local_taxes findOne(Long id) {
        log.debug("Request to get Freecom_local_taxes : {}", id);
        Freecom_local_taxes freecom_local_taxes = freecom_local_taxesRepository.findOne(id);
        return freecom_local_taxes;
    }

    /**
     *  Delete the  freecom_local_taxes by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_local_taxes : {}", id);
        freecom_local_taxesRepository.delete(id);
    }
}
