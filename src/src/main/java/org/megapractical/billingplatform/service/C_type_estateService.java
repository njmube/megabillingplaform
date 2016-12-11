package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_type_estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_type_estate.
 */
public interface C_type_estateService {

    /**
     * Save a c_type_estate.
     * 
     * @param c_type_estate the entity to save
     * @return the persisted entity
     */
    C_type_estate save(C_type_estate c_type_estate);

    /**
     *  Get all the c_type_estates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_type_estate> findAll(Pageable pageable);

    /**
     *  Get the "id" c_type_estate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_type_estate findOne(Long id);

    /**
     *  Delete the "id" c_type_estate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
