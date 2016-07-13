package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Process_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Process_type.
 */
public interface Process_typeService {

    /**
     * Save a process_type.
     * 
     * @param process_type the entity to save
     * @return the persisted entity
     */
    Process_type save(Process_type process_type);

    /**
     *  Get all the process_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Process_type> findAll(Pageable pageable);

    /**
     *  Get the "id" process_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Process_type findOne(Long id);

    /**
     *  Delete the "id" process_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
