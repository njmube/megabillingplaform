package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_desc_estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_desc_estate.
 */
public interface Com_desc_estateService {

    /**
     * Save a com_desc_estate.
     * 
     * @param com_desc_estate the entity to save
     * @return the persisted entity
     */
    Com_desc_estate save(Com_desc_estate com_desc_estate);

    /**
     *  Get all the com_desc_estates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_desc_estate> findAll(Pageable pageable);

    /**
     *  Get the "id" com_desc_estate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_desc_estate findOne(Long id);

    /**
     *  Delete the "id" com_desc_estate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
