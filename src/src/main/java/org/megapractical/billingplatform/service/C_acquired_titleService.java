package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_acquired_title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_acquired_title.
 */
public interface C_acquired_titleService {

    /**
     * Save a c_acquired_title.
     * 
     * @param c_acquired_title the entity to save
     * @return the persisted entity
     */
    C_acquired_title save(C_acquired_title c_acquired_title);

    /**
     *  Get all the c_acquired_titles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_acquired_title> findAll(Pageable pageable);

    /**
     *  Get the "id" c_acquired_title.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_acquired_title findOne(Long id);

    /**
     *  Delete the "id" c_acquired_title.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
