package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_local_transferedService;
import org.megapractical.billingplatform.domain.Freecom_local_transfered;
import org.megapractical.billingplatform.repository.Freecom_local_transferedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_local_transfered.
 */
@Service
@Transactional
public class Freecom_local_transferedServiceImpl implements Freecom_local_transferedService{

    private final Logger log = LoggerFactory.getLogger(Freecom_local_transferedServiceImpl.class);
    
    @Inject
    private Freecom_local_transferedRepository freecom_local_transferedRepository;
    
    /**
     * Save a freecom_local_transfered.
     * 
     * @param freecom_local_transfered the entity to save
     * @return the persisted entity
     */
    public Freecom_local_transfered save(Freecom_local_transfered freecom_local_transfered) {
        log.debug("Request to save Freecom_local_transfered : {}", freecom_local_transfered);
        Freecom_local_transfered result = freecom_local_transferedRepository.save(freecom_local_transfered);
        return result;
    }

    /**
     *  Get all the freecom_local_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_local_transfered> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_local_transfereds");
        Page<Freecom_local_transfered> result = freecom_local_transferedRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_local_transfered by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_local_transfered findOne(Long id) {
        log.debug("Request to get Freecom_local_transfered : {}", id);
        Freecom_local_transfered freecom_local_transfered = freecom_local_transferedRepository.findOne(id);
        return freecom_local_transfered;
    }

    /**
     *  Delete the  freecom_local_transfered by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_local_transfered : {}", id);
        freecom_local_transferedRepository.delete(id);
    }
}
