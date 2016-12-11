package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_pn_federal_entityService;
import org.megapractical.billingplatform.domain.C_pn_federal_entity;
import org.megapractical.billingplatform.repository.C_pn_federal_entityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_pn_federal_entity.
 */
@Service
@Transactional
public class C_pn_federal_entityServiceImpl implements C_pn_federal_entityService{

    private final Logger log = LoggerFactory.getLogger(C_pn_federal_entityServiceImpl.class);
    
    @Inject
    private C_pn_federal_entityRepository c_pn_federal_entityRepository;
    
    /**
     * Save a c_pn_federal_entity.
     * 
     * @param c_pn_federal_entity the entity to save
     * @return the persisted entity
     */
    public C_pn_federal_entity save(C_pn_federal_entity c_pn_federal_entity) {
        log.debug("Request to save C_pn_federal_entity : {}", c_pn_federal_entity);
        C_pn_federal_entity result = c_pn_federal_entityRepository.save(c_pn_federal_entity);
        return result;
    }

    /**
     *  Get all the c_pn_federal_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_pn_federal_entity> findAll(Pageable pageable) {
        log.debug("Request to get all C_pn_federal_entities");
        Page<C_pn_federal_entity> result = c_pn_federal_entityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_pn_federal_entity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_pn_federal_entity findOne(Long id) {
        log.debug("Request to get C_pn_federal_entity : {}", id);
        C_pn_federal_entity c_pn_federal_entity = c_pn_federal_entityRepository.findOne(id);
        return c_pn_federal_entity;
    }

    /**
     *  Delete the  c_pn_federal_entity by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_pn_federal_entity : {}", id);
        c_pn_federal_entityRepository.delete(id);
    }
}
