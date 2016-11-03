package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_specific_descriptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_specific_descriptions.
 */
public interface Com_specific_descriptionsService {

    /**
     * Save a com_specific_descriptions.
     * 
     * @param com_specific_descriptions the entity to save
     * @return the persisted entity
     */
    Com_specific_descriptions save(Com_specific_descriptions com_specific_descriptions);

    /**
     *  Get all the com_specific_descriptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_specific_descriptions> findAll(Pageable pageable);

    /**
     *  Get the "id" com_specific_descriptions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_specific_descriptions findOne(Long id);

    /**
     *  Delete the "id" com_specific_descriptions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
