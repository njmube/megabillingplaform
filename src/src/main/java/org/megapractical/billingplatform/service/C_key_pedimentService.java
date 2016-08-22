package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_key_pediment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_key_pediment.
 */
public interface C_key_pedimentService {

    /**
     * Save a c_key_pediment.
     * 
     * @param c_key_pediment the entity to save
     * @return the persisted entity
     */
    C_key_pediment save(C_key_pediment c_key_pediment);

    /**
     *  Get all the c_key_pediments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_key_pediment> findAll(Pageable pageable);

    /**
     *  Get the "id" c_key_pediment.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_key_pediment findOne(Long id);

    /**
     *  Delete the "id" c_key_pediment.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
