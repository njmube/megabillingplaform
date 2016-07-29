package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_type_operationService;
import org.megapractical.billingplatform.domain.C_type_operation;
import org.megapractical.billingplatform.repository.C_type_operationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_type_operation.
 */
@Service
@Transactional
public class C_type_operationServiceImpl implements C_type_operationService{

    private final Logger log = LoggerFactory.getLogger(C_type_operationServiceImpl.class);
    
    @Inject
    private C_type_operationRepository c_type_operationRepository;
    
    /**
     * Save a c_type_operation.
     * 
     * @param c_type_operation the entity to save
     * @return the persisted entity
     */
    public C_type_operation save(C_type_operation c_type_operation) {
        log.debug("Request to save C_type_operation : {}", c_type_operation);
        C_type_operation result = c_type_operationRepository.save(c_type_operation);
        return result;
    }

    /**
     *  Get all the c_type_operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_type_operation> findAll(Pageable pageable) {
        log.debug("Request to get all C_type_operations");
        Page<C_type_operation> result = c_type_operationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_type_operation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_type_operation findOne(Long id) {
        log.debug("Request to get C_type_operation : {}", id);
        C_type_operation c_type_operation = c_type_operationRepository.findOne(id);
        return c_type_operation;
    }

    /**
     *  Delete the  c_type_operation by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_type_operation : {}", id);
        c_type_operationRepository.delete(id);
    }
}
