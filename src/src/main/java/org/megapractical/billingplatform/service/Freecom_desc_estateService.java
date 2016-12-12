package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_desc_estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_desc_estate.
 */
public interface Freecom_desc_estateService {

    /**
     * Save a freecom_desc_estate.
     * 
     * @param freecom_desc_estate the entity to save
     * @return the persisted entity
     */
    Freecom_desc_estate save(Freecom_desc_estate freecom_desc_estate);

    /**
     *  Get all the freecom_desc_estates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_desc_estate> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_desc_estate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_desc_estate findOne(Long id);

    /**
     *  Delete the "id" freecom_desc_estate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
