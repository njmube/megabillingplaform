package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_legends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_legends.
 */
public interface Com_legendsService {

    /**
     * Save a com_legends.
     * 
     * @param com_legends the entity to save
     * @return the persisted entity
     */
    Com_legends save(Com_legends com_legends);

    /**
     *  Get all the com_legends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_legends> findAll(Pageable pageable);

    /**
     *  Get the "id" com_legends.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_legends findOne(Long id);

    /**
     *  Delete the "id" com_legends.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
