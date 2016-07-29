package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_type_operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_type_operation.
 */
public interface C_type_operationService {

    /**
     * Save a c_type_operation.
     * 
     * @param c_type_operation the entity to save
     * @return the persisted entity
     */
    C_type_operation save(C_type_operation c_type_operation);

    /**
     *  Get all the c_type_operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_type_operation> findAll(Pageable pageable);

    /**
     *  Get the "id" c_type_operation.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_type_operation findOne(Long id);

    /**
     *  Delete the "id" c_type_operation.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
