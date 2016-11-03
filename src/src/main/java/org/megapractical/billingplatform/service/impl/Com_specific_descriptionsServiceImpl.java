package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_specific_descriptionsService;
import org.megapractical.billingplatform.domain.Com_specific_descriptions;
import org.megapractical.billingplatform.repository.Com_specific_descriptionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_specific_descriptions.
 */
@Service
@Transactional
public class Com_specific_descriptionsServiceImpl implements Com_specific_descriptionsService{

    private final Logger log = LoggerFactory.getLogger(Com_specific_descriptionsServiceImpl.class);
    
    @Inject
    private Com_specific_descriptionsRepository com_specific_descriptionsRepository;
    
    /**
     * Save a com_specific_descriptions.
     * 
     * @param com_specific_descriptions the entity to save
     * @return the persisted entity
     */
    public Com_specific_descriptions save(Com_specific_descriptions com_specific_descriptions) {
        log.debug("Request to save Com_specific_descriptions : {}", com_specific_descriptions);
        Com_specific_descriptions result = com_specific_descriptionsRepository.save(com_specific_descriptions);
        return result;
    }

    /**
     *  Get all the com_specific_descriptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_specific_descriptions> findAll(Pageable pageable) {
        log.debug("Request to get all Com_specific_descriptions");
        Page<Com_specific_descriptions> result = com_specific_descriptionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_specific_descriptions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_specific_descriptions findOne(Long id) {
        log.debug("Request to get Com_specific_descriptions : {}", id);
        Com_specific_descriptions com_specific_descriptions = com_specific_descriptionsRepository.findOne(id);
        return com_specific_descriptions;
    }

    /**
     *  Delete the  com_specific_descriptions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_specific_descriptions : {}", id);
        com_specific_descriptionsRepository.delete(id);
    }
}
