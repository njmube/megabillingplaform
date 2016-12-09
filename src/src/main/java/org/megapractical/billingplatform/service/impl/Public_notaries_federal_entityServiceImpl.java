package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Public_notaries_federal_entityService;
import org.megapractical.billingplatform.domain.Public_notaries_federal_entity;
import org.megapractical.billingplatform.repository.Public_notaries_federal_entityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Public_notaries_federal_entity.
 */
@Service
@Transactional
public class Public_notaries_federal_entityServiceImpl implements Public_notaries_federal_entityService{

    private final Logger log = LoggerFactory.getLogger(Public_notaries_federal_entityServiceImpl.class);
    
    @Inject
    private Public_notaries_federal_entityRepository public_notaries_federal_entityRepository;
    
    /**
     * Save a public_notaries_federal_entity.
     * 
     * @param public_notaries_federal_entity the entity to save
     * @return the persisted entity
     */
    public Public_notaries_federal_entity save(Public_notaries_federal_entity public_notaries_federal_entity) {
        log.debug("Request to save Public_notaries_federal_entity : {}", public_notaries_federal_entity);
        Public_notaries_federal_entity result = public_notaries_federal_entityRepository.save(public_notaries_federal_entity);
        return result;
    }

    /**
     *  Get all the public_notaries_federal_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Public_notaries_federal_entity> findAll(Pageable pageable) {
        log.debug("Request to get all Public_notaries_federal_entities");
        Page<Public_notaries_federal_entity> result = public_notaries_federal_entityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one public_notaries_federal_entity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Public_notaries_federal_entity findOne(Long id) {
        log.debug("Request to get Public_notaries_federal_entity : {}", id);
        Public_notaries_federal_entity public_notaries_federal_entity = public_notaries_federal_entityRepository.findOne(id);
        return public_notaries_federal_entity;
    }

    /**
     *  Delete the  public_notaries_federal_entity by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Public_notaries_federal_entity : {}", id);
        public_notaries_federal_entityRepository.delete(id);
    }
}
