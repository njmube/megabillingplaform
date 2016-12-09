package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_data_operationService;
import org.megapractical.billingplatform.domain.Com_data_operation;
import org.megapractical.billingplatform.repository.Com_data_operationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_data_operation.
 */
@Service
@Transactional
public class Com_data_operationServiceImpl implements Com_data_operationService{

    private final Logger log = LoggerFactory.getLogger(Com_data_operationServiceImpl.class);
    
    @Inject
    private Com_data_operationRepository com_data_operationRepository;
    
    /**
     * Save a com_data_operation.
     * 
     * @param com_data_operation the entity to save
     * @return the persisted entity
     */
    public Com_data_operation save(Com_data_operation com_data_operation) {
        log.debug("Request to save Com_data_operation : {}", com_data_operation);
        Com_data_operation result = com_data_operationRepository.save(com_data_operation);
        return result;
    }

    /**
     *  Get all the com_data_operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_data_operation> findAll(Pageable pageable) {
        log.debug("Request to get all Com_data_operations");
        Page<Com_data_operation> result = com_data_operationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_data_operation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_data_operation findOne(Long id) {
        log.debug("Request to get Com_data_operation : {}", id);
        Com_data_operation com_data_operation = com_data_operationRepository.findOne(id);
        return com_data_operation;
    }

    /**
     *  Delete the  com_data_operation by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_data_operation : {}", id);
        com_data_operationRepository.delete(id);
    }
}
