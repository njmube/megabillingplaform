package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Legend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Legend.
 */
public interface LegendService {

    /**
     * Save a legend.
     * 
     * @param legend the entity to save
     * @return the persisted entity
     */
    Legend save(Legend legend);

    /**
     *  Get all the legends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Legend> findAll(Pageable pageable);

    /**
     *  Get the "id" legend.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Legend findOne(Long id);

    /**
     *  Delete the "id" legend.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
