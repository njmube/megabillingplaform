package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_educational_institutionsService;
import org.megapractical.billingplatform.domain.Freecom_educational_institutions;
import org.megapractical.billingplatform.repository.Freecom_educational_institutionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_educational_institutions.
 */
@Service
@Transactional
public class Freecom_educational_institutionsServiceImpl implements Freecom_educational_institutionsService{

    private final Logger log = LoggerFactory.getLogger(Freecom_educational_institutionsServiceImpl.class);
    
    @Inject
    private Freecom_educational_institutionsRepository freecom_educational_institutionsRepository;
    
    /**
     * Save a freecom_educational_institutions.
     * 
     * @param freecom_educational_institutions the entity to save
     * @return the persisted entity
     */
    public Freecom_educational_institutions save(Freecom_educational_institutions freecom_educational_institutions) {
        log.debug("Request to save Freecom_educational_institutions : {}", freecom_educational_institutions);
        Freecom_educational_institutions result = freecom_educational_institutionsRepository.save(freecom_educational_institutions);
        return result;
    }

    /**
     *  Get all the freecom_educational_institutions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_educational_institutions> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_educational_institutions");
        Page<Freecom_educational_institutions> result = freecom_educational_institutionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_educational_institutions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_educational_institutions findOne(Long id) {
        log.debug("Request to get Freecom_educational_institutions : {}", id);
        Freecom_educational_institutions freecom_educational_institutions = freecom_educational_institutionsRepository.findOne(id);
        return freecom_educational_institutions;
    }

    /**
     *  Delete the  freecom_educational_institutions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_educational_institutions : {}", id);
        freecom_educational_institutionsRepository.delete(id);
    }
}
