package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_type_operation_ce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_type_operation_ce.
 */
public interface C_type_operation_ceService {

    /**
     * Save a c_type_operation_ce.
     * 
     * @param c_type_operation_ce the entity to save
     * @return the persisted entity
     */
    C_type_operation_ce save(C_type_operation_ce c_type_operation_ce);

    /**
     *  Get all the c_type_operation_ces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_type_operation_ce> findAll(Pageable pageable);

    /**
     *  Get the "id" c_type_operation_ce.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_type_operation_ce findOne(Long id);

    /**
     *  Delete the "id" c_type_operation_ce.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
