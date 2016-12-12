package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_data_operationService;
import org.megapractical.billingplatform.domain.Freecom_data_operation;
import org.megapractical.billingplatform.repository.Freecom_data_operationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_data_operation.
 */
@Service
@Transactional
public class Freecom_data_operationServiceImpl implements Freecom_data_operationService{

    private final Logger log = LoggerFactory.getLogger(Freecom_data_operationServiceImpl.class);
    
    @Inject
    private Freecom_data_operationRepository freecom_data_operationRepository;
    
    /**
     * Save a freecom_data_operation.
     * 
     * @param freecom_data_operation the entity to save
     * @return the persisted entity
     */
    public Freecom_data_operation save(Freecom_data_operation freecom_data_operation) {
        log.debug("Request to save Freecom_data_operation : {}", freecom_data_operation);
        Freecom_data_operation result = freecom_data_operationRepository.save(freecom_data_operation);
        return result;
    }

    /**
     *  Get all the freecom_data_operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_data_operation> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_data_operations");
        Page<Freecom_data_operation> result = freecom_data_operationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_data_operation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_data_operation findOne(Long id) {
        log.debug("Request to get Freecom_data_operation : {}", id);
        Freecom_data_operation freecom_data_operation = freecom_data_operationRepository.findOne(id);
        return freecom_data_operation;
    }

    /**
     *  Delete the  freecom_data_operation by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_data_operation : {}", id);
        freecom_data_operationRepository.delete(id);
    }
}
