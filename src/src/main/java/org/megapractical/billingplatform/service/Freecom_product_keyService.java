package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_product_key;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_product_key.
 */
public interface Freecom_product_keyService {

    /**
     * Save a freecom_product_key.
     * 
     * @param freecom_product_key the entity to save
     * @return the persisted entity
     */
    Freecom_product_key save(Freecom_product_key freecom_product_key);

    /**
     *  Get all the freecom_product_keys.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_product_key> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_product_key.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_product_key findOne(Long id);

    /**
     *  Delete the "id" freecom_product_key.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
