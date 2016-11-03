package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_ine_entityService;
import org.megapractical.billingplatform.domain.Com_ine_entity;
import org.megapractical.billingplatform.repository.Com_ine_entityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_ine_entity.
 */
@Service
@Transactional
public class Com_ine_entityServiceImpl implements Com_ine_entityService{

    private final Logger log = LoggerFactory.getLogger(Com_ine_entityServiceImpl.class);
    
    @Inject
    private Com_ine_entityRepository com_ine_entityRepository;
    
    /**
     * Save a com_ine_entity.
     * 
     * @param com_ine_entity the entity to save
     * @return the persisted entity
     */
    public Com_ine_entity save(Com_ine_entity com_ine_entity) {
        log.debug("Request to save Com_ine_entity : {}", com_ine_entity);
        Com_ine_entity result = com_ine_entityRepository.save(com_ine_entity);
        return result;
    }

    /**
     *  Get all the com_ine_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_ine_entity> findAll(Pageable pageable) {
        log.debug("Request to get all Com_ine_entities");
        Page<Com_ine_entity> result = com_ine_entityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_ine_entity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_ine_entity findOne(Long id) {
        log.debug("Request to get Com_ine_entity : {}", id);
        Com_ine_entity com_ine_entity = com_ine_entityRepository.findOne(id);
        return com_ine_entity;
    }

    /**
     *  Delete the  com_ine_entity by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_ine_entity : {}", id);
        com_ine_entityRepository.delete(id);
    }
}
