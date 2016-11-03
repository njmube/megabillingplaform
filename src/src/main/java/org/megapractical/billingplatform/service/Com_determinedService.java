package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_determined;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_determined.
 */
public interface Com_determinedService {

    /**
     * Save a com_determined.
     * 
     * @param com_determined the entity to save
     * @return the persisted entity
     */
    Com_determined save(Com_determined com_determined);

    /**
     *  Get all the com_determineds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_determined> findAll(Pageable pageable);

    /**
     *  Get the "id" com_determined.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_determined findOne(Long id);

    /**
     *  Delete the "id" com_determined.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
