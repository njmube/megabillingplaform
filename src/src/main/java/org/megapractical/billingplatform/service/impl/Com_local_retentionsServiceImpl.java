package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_local_retentionsService;
import org.megapractical.billingplatform.domain.Com_local_retentions;
import org.megapractical.billingplatform.repository.Com_local_retentionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_local_retentions.
 */
@Service
@Transactional
public class Com_local_retentionsServiceImpl implements Com_local_retentionsService{

    private final Logger log = LoggerFactory.getLogger(Com_local_retentionsServiceImpl.class);
    
    @Inject
    private Com_local_retentionsRepository com_local_retentionsRepository;
    
    /**
     * Save a com_local_retentions.
     * 
     * @param com_local_retentions the entity to save
     * @return the persisted entity
     */
    public Com_local_retentions save(Com_local_retentions com_local_retentions) {
        log.debug("Request to save Com_local_retentions : {}", com_local_retentions);
        Com_local_retentions result = com_local_retentionsRepository.save(com_local_retentions);
        return result;
    }

    /**
     *  Get all the com_local_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_local_retentions> findAll(Pageable pageable) {
        log.debug("Request to get all Com_local_retentions");
        Page<Com_local_retentions> result = com_local_retentionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_local_retentions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_local_retentions findOne(Long id) {
        log.debug("Request to get Com_local_retentions : {}", id);
        Com_local_retentions com_local_retentions = com_local_retentionsRepository.findOne(id);
        return com_local_retentions;
    }

    /**
     *  Delete the  com_local_retentions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_local_retentions : {}", id);
        com_local_retentionsRepository.delete(id);
    }
}
