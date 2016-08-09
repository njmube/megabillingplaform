package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_ine_entityService;
import org.megapractical.billingplatform.domain.Freecom_ine_entity;
import org.megapractical.billingplatform.repository.Freecom_ine_entityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_ine_entity.
 */
@Service
@Transactional
public class Freecom_ine_entityServiceImpl implements Freecom_ine_entityService{

    private final Logger log = LoggerFactory.getLogger(Freecom_ine_entityServiceImpl.class);
    
    @Inject
    private Freecom_ine_entityRepository freecom_ine_entityRepository;
    
    /**
     * Save a freecom_ine_entity.
     * 
     * @param freecom_ine_entity the entity to save
     * @return the persisted entity
     */
    public Freecom_ine_entity save(Freecom_ine_entity freecom_ine_entity) {
        log.debug("Request to save Freecom_ine_entity : {}", freecom_ine_entity);
        Freecom_ine_entity result = freecom_ine_entityRepository.save(freecom_ine_entity);
        return result;
    }

    /**
     *  Get all the freecom_ine_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_ine_entity> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_ine_entities");
        Page<Freecom_ine_entity> result = freecom_ine_entityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_ine_entity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_ine_entity findOne(Long id) {
        log.debug("Request to get Freecom_ine_entity : {}", id);
        Freecom_ine_entity freecom_ine_entity = freecom_ine_entityRepository.findOne(id);
        return freecom_ine_entity;
    }

    /**
     *  Delete the  freecom_ine_entity by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_ine_entity : {}", id);
        freecom_ine_entityRepository.delete(id);
    }
}
