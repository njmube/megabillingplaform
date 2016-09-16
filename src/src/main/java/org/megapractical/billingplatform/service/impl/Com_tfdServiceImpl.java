package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_tfdService;
import org.megapractical.billingplatform.domain.Com_tfd;
import org.megapractical.billingplatform.repository.Com_tfdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_tfd.
 */
@Service
@Transactional
public class Com_tfdServiceImpl implements Com_tfdService{

    private final Logger log = LoggerFactory.getLogger(Com_tfdServiceImpl.class);
    
    @Inject
    private Com_tfdRepository com_tfdRepository;
    
    /**
     * Save a com_tfd.
     * 
     * @param com_tfd the entity to save
     * @return the persisted entity
     */
    public Com_tfd save(Com_tfd com_tfd) {
        log.debug("Request to save Com_tfd : {}", com_tfd);
        Com_tfd result = com_tfdRepository.save(com_tfd);
        return result;
    }

    /**
     *  Get all the com_tfds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_tfd> findAll(Pageable pageable) {
        log.debug("Request to get all Com_tfds");
        Page<Com_tfd> result = com_tfdRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_tfd by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_tfd findOne(Long id) {
        log.debug("Request to get Com_tfd : {}", id);
        Com_tfd com_tfd = com_tfdRepository.findOne(id);
        return com_tfd;
    }

    /**
     *  Delete the  com_tfd by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_tfd : {}", id);
        com_tfdRepository.delete(id);
    }
}
