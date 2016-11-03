package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_product_key;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_product_key.
 */
public interface Com_product_keyService {

    /**
     * Save a com_product_key.
     * 
     * @param com_product_key the entity to save
     * @return the persisted entity
     */
    Com_product_key save(Com_product_key com_product_key);

    /**
     *  Get all the com_product_keys.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_product_key> findAll(Pageable pageable);

    /**
     *  Get the "id" com_product_key.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_product_key findOne(Long id);

    /**
     *  Delete the "id" com_product_key.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
