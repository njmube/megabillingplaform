package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_specific_descriptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_specific_descriptions.
 */
public interface Freecom_specific_descriptionsService {

    /**
     * Save a freecom_specific_descriptions.
     * 
     * @param freecom_specific_descriptions the entity to save
     * @return the persisted entity
     */
    Freecom_specific_descriptions save(Freecom_specific_descriptions freecom_specific_descriptions);

    /**
     *  Get all the freecom_specific_descriptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_specific_descriptions> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_specific_descriptions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_specific_descriptions findOne(Long id);

    /**
     *  Delete the "id" freecom_specific_descriptions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
