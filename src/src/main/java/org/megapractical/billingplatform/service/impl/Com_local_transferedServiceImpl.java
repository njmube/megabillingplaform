package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_local_transferedService;
import org.megapractical.billingplatform.domain.Com_local_transfered;
import org.megapractical.billingplatform.repository.Com_local_transferedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_local_transfered.
 */
@Service
@Transactional
public class Com_local_transferedServiceImpl implements Com_local_transferedService{

    private final Logger log = LoggerFactory.getLogger(Com_local_transferedServiceImpl.class);
    
    @Inject
    private Com_local_transferedRepository com_local_transferedRepository;
    
    /**
     * Save a com_local_transfered.
     * 
     * @param com_local_transfered the entity to save
     * @return the persisted entity
     */
    public Com_local_transfered save(Com_local_transfered com_local_transfered) {
        log.debug("Request to save Com_local_transfered : {}", com_local_transfered);
        Com_local_transfered result = com_local_transferedRepository.save(com_local_transfered);
        return result;
    }

    /**
     *  Get all the com_local_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_local_transfered> findAll(Pageable pageable) {
        log.debug("Request to get all Com_local_transfereds");
        Page<Com_local_transfered> result = com_local_transferedRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_local_transfered by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_local_transfered findOne(Long id) {
        log.debug("Request to get Com_local_transfered : {}", id);
        Com_local_transfered com_local_transfered = com_local_transferedRepository.findOne(id);
        return com_local_transfered;
    }

    /**
     *  Delete the  com_local_transfered by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_local_transfered : {}", id);
        com_local_transferedRepository.delete(id);
    }
}
