package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Key_entityService;
import org.megapractical.billingplatform.domain.Key_entity;
import org.megapractical.billingplatform.repository.Key_entityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Key_entity.
 */
@Service
@Transactional
public class Key_entityServiceImpl implements Key_entityService{

    private final Logger log = LoggerFactory.getLogger(Key_entityServiceImpl.class);
    
    @Inject
    private Key_entityRepository key_entityRepository;
    
    /**
     * Save a key_entity.
     * 
     * @param key_entity the entity to save
     * @return the persisted entity
     */
    public Key_entity save(Key_entity key_entity) {
        log.debug("Request to save Key_entity : {}", key_entity);
        Key_entity result = key_entityRepository.save(key_entity);
        return result;
    }

    /**
     *  Get all the key_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Key_entity> findAll(Pageable pageable) {
        log.debug("Request to get all Key_entities");
        Page<Key_entity> result = key_entityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one key_entity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Key_entity findOne(Long id) {
        log.debug("Request to get Key_entity : {}", id);
        Key_entity key_entity = key_entityRepository.findOne(id);
        return key_entity;
    }

    /**
     *  Delete the  key_entity by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Key_entity : {}", id);
        key_entityRepository.delete(id);
    }
}
