package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_type_operation_ceService;
import org.megapractical.billingplatform.domain.C_type_operation_ce;
import org.megapractical.billingplatform.repository.C_type_operation_ceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_type_operation_ce.
 */
@Service
@Transactional
public class C_type_operation_ceServiceImpl implements C_type_operation_ceService{

    private final Logger log = LoggerFactory.getLogger(C_type_operation_ceServiceImpl.class);
    
    @Inject
    private C_type_operation_ceRepository c_type_operation_ceRepository;
    
    /**
     * Save a c_type_operation_ce.
     * 
     * @param c_type_operation_ce the entity to save
     * @return the persisted entity
     */
    public C_type_operation_ce save(C_type_operation_ce c_type_operation_ce) {
        log.debug("Request to save C_type_operation_ce : {}", c_type_operation_ce);
        C_type_operation_ce result = c_type_operation_ceRepository.save(c_type_operation_ce);
        return result;
    }

    /**
     *  Get all the c_type_operation_ces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_type_operation_ce> findAll(Pageable pageable) {
        log.debug("Request to get all C_type_operation_ces");
        Page<C_type_operation_ce> result = c_type_operation_ceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_type_operation_ce by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_type_operation_ce findOne(Long id) {
        log.debug("Request to get C_type_operation_ce : {}", id);
        C_type_operation_ce c_type_operation_ce = c_type_operation_ceRepository.findOne(id);
        return c_type_operation_ce;
    }

    /**
     *  Delete the  c_type_operation_ce by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_type_operation_ce : {}", id);
        c_type_operation_ceRepository.delete(id);
    }
}
