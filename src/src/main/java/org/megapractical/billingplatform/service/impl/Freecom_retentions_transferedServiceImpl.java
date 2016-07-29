package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_retentions_transferedService;
import org.megapractical.billingplatform.domain.Freecom_retentions_transfered;
import org.megapractical.billingplatform.repository.Freecom_retentions_transferedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_retentions_transfered.
 */
@Service
@Transactional
public class Freecom_retentions_transferedServiceImpl implements Freecom_retentions_transferedService{

    private final Logger log = LoggerFactory.getLogger(Freecom_retentions_transferedServiceImpl.class);
    
    @Inject
    private Freecom_retentions_transferedRepository freecom_retentions_transferedRepository;
    
    /**
     * Save a freecom_retentions_transfered.
     * 
     * @param freecom_retentions_transfered the entity to save
     * @return the persisted entity
     */
    public Freecom_retentions_transfered save(Freecom_retentions_transfered freecom_retentions_transfered) {
        log.debug("Request to save Freecom_retentions_transfered : {}", freecom_retentions_transfered);
        Freecom_retentions_transfered result = freecom_retentions_transferedRepository.save(freecom_retentions_transfered);
        return result;
    }

    /**
     *  Get all the freecom_retentions_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_retentions_transfered> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_retentions_transfereds");
        Page<Freecom_retentions_transfered> result = freecom_retentions_transferedRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_retentions_transfered by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_retentions_transfered findOne(Long id) {
        log.debug("Request to get Freecom_retentions_transfered : {}", id);
        Freecom_retentions_transfered freecom_retentions_transfered = freecom_retentions_transferedRepository.findOne(id);
        return freecom_retentions_transfered;
    }

    /**
     *  Delete the  freecom_retentions_transfered by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_retentions_transfered : {}", id);
        freecom_retentions_transferedRepository.delete(id);
    }
}
