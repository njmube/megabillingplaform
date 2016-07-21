package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_process_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_process_type.
 */
public interface C_process_typeService {

    /**
     * Save a c_process_type.
     * 
     * @param c_process_type the entity to save
     * @return the persisted entity
     */
    C_process_type save(C_process_type c_process_type);

    /**
     *  Get all the c_process_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_process_type> findAll(Pageable pageable);

    /**
     *  Get the "id" c_process_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_process_type findOne(Long id);

    /**
     *  Delete the "id" c_process_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
