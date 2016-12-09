package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_public_notariesService;
import org.megapractical.billingplatform.domain.Com_public_notaries;
import org.megapractical.billingplatform.repository.Com_public_notariesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_public_notaries.
 */
@Service
@Transactional
public class Com_public_notariesServiceImpl implements Com_public_notariesService{

    private final Logger log = LoggerFactory.getLogger(Com_public_notariesServiceImpl.class);
    
    @Inject
    private Com_public_notariesRepository com_public_notariesRepository;
    
    /**
     * Save a com_public_notaries.
     * 
     * @param com_public_notaries the entity to save
     * @return the persisted entity
     */
    public Com_public_notaries save(Com_public_notaries com_public_notaries) {
        log.debug("Request to save Com_public_notaries : {}", com_public_notaries);
        Com_public_notaries result = com_public_notariesRepository.save(com_public_notaries);
        return result;
    }

    /**
     *  Get all the com_public_notaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_public_notaries> findAll(Pageable pageable) {
        log.debug("Request to get all Com_public_notaries");
        Page<Com_public_notaries> result = com_public_notariesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_public_notaries by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_public_notaries findOne(Long id) {
        log.debug("Request to get Com_public_notaries : {}", id);
        Com_public_notaries com_public_notaries = com_public_notariesRepository.findOne(id);
        return com_public_notaries;
    }

    /**
     *  Delete the  com_public_notaries by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_public_notaries : {}", id);
        com_public_notariesRepository.delete(id);
    }
}
