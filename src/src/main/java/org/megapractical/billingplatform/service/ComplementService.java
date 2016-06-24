package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Complement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Complement.
 */
public interface ComplementService {

    /**
     * Save a complement.
     * 
     * @param complement the entity to save
     * @return the persisted entity
     */
    Complement save(Complement complement);

    /**
     *  Get all the complements.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Complement> findAll(Pageable pageable);

    /**
     *  Get the "id" complement.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Complement findOne(Long id);

    /**
     *  Delete the "id" complement.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
