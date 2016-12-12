package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_data_operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_data_operation.
 */
public interface Freecom_data_operationService {

    /**
     * Save a freecom_data_operation.
     * 
     * @param freecom_data_operation the entity to save
     * @return the persisted entity
     */
    Freecom_data_operation save(Freecom_data_operation freecom_data_operation);

    /**
     *  Get all the freecom_data_operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_data_operation> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_data_operation.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_data_operation findOne(Long id);

    /**
     *  Delete the "id" freecom_data_operation.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
