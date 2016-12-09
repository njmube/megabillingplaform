package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_data_operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_data_operation.
 */
public interface Com_data_operationService {

    /**
     * Save a com_data_operation.
     * 
     * @param com_data_operation the entity to save
     * @return the persisted entity
     */
    Com_data_operation save(Com_data_operation com_data_operation);

    /**
     *  Get all the com_data_operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_data_operation> findAll(Pageable pageable);

    /**
     *  Get the "id" com_data_operation.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_data_operation findOne(Long id);

    /**
     *  Delete the "id" com_data_operation.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
