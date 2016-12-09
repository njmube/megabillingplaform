package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_dataunacquiringService;
import org.megapractical.billingplatform.domain.Com_dataunacquiring;
import org.megapractical.billingplatform.repository.Com_dataunacquiringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_dataunacquiring.
 */
@Service
@Transactional
public class Com_dataunacquiringServiceImpl implements Com_dataunacquiringService{

    private final Logger log = LoggerFactory.getLogger(Com_dataunacquiringServiceImpl.class);
    
    @Inject
    private Com_dataunacquiringRepository com_dataunacquiringRepository;
    
    /**
     * Save a com_dataunacquiring.
     * 
     * @param com_dataunacquiring the entity to save
     * @return the persisted entity
     */
    public Com_dataunacquiring save(Com_dataunacquiring com_dataunacquiring) {
        log.debug("Request to save Com_dataunacquiring : {}", com_dataunacquiring);
        Com_dataunacquiring result = com_dataunacquiringRepository.save(com_dataunacquiring);
        return result;
    }

    /**
     *  Get all the com_dataunacquirings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_dataunacquiring> findAll(Pageable pageable) {
        log.debug("Request to get all Com_dataunacquirings");
        Page<Com_dataunacquiring> result = com_dataunacquiringRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_dataunacquiring by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_dataunacquiring findOne(Long id) {
        log.debug("Request to get Com_dataunacquiring : {}", id);
        Com_dataunacquiring com_dataunacquiring = com_dataunacquiringRepository.findOne(id);
        return com_dataunacquiring;
    }

    /**
     *  Delete the  com_dataunacquiring by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_dataunacquiring : {}", id);
        com_dataunacquiringRepository.delete(id);
    }
}
