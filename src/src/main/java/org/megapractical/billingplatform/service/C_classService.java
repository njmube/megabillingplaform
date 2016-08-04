package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_class.
 */
public interface C_classService {

    /**
     * Save a c_class.
     * 
     * @param c_class the entity to save
     * @return the persisted entity
     */
    C_class save(C_class c_class);

    /**
     *  Get all the c_classes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_class> findAll(Pageable pageable);

    /**
     *  Get the "id" c_class.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_class findOne(Long id);

    /**
     *  Delete the "id" c_class.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
