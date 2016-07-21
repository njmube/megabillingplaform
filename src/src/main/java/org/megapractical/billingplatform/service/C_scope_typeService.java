package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_scope_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_scope_type.
 */
public interface C_scope_typeService {

    /**
     * Save a c_scope_type.
     * 
     * @param c_scope_type the entity to save
     * @return the persisted entity
     */
    C_scope_type save(C_scope_type c_scope_type);

    /**
     *  Get all the c_scope_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_scope_type> findAll(Pageable pageable);

    /**
     *  Get the "id" c_scope_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_scope_type findOne(Long id);

    /**
     *  Delete the "id" c_scope_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
