package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_local_retentionsService;
import org.megapractical.billingplatform.domain.Freecom_local_retentions;
import org.megapractical.billingplatform.repository.Freecom_local_retentionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_local_retentions.
 */
@Service
@Transactional
public class Freecom_local_retentionsServiceImpl implements Freecom_local_retentionsService{

    private final Logger log = LoggerFactory.getLogger(Freecom_local_retentionsServiceImpl.class);
    
    @Inject
    private Freecom_local_retentionsRepository freecom_local_retentionsRepository;
    
    /**
     * Save a freecom_local_retentions.
     * 
     * @param freecom_local_retentions the entity to save
     * @return the persisted entity
     */
    public Freecom_local_retentions save(Freecom_local_retentions freecom_local_retentions) {
        log.debug("Request to save Freecom_local_retentions : {}", freecom_local_retentions);
        Freecom_local_retentions result = freecom_local_retentionsRepository.save(freecom_local_retentions);
        return result;
    }

    /**
     *  Get all the freecom_local_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_local_retentions> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_local_retentions");
        Page<Freecom_local_retentions> result = freecom_local_retentionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_local_retentions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_local_retentions findOne(Long id) {
        log.debug("Request to get Freecom_local_retentions : {}", id);
        Freecom_local_retentions freecom_local_retentions = freecom_local_retentionsRepository.findOne(id);
        return freecom_local_retentions;
    }

    /**
     *  Delete the  freecom_local_retentions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_local_retentions : {}", id);
        freecom_local_retentionsRepository.delete(id);
    }
}
