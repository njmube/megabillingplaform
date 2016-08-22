package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_legend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_legend.
 */
public interface Freecom_legendService {

    /**
     * Save a freecom_legend.
     * 
     * @param freecom_legend the entity to save
     * @return the persisted entity
     */
    Freecom_legend save(Freecom_legend freecom_legend);

    /**
     *  Get all the freecom_legends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_legend> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_legend.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_legend findOne(Long id);

    /**
     *  Delete the "id" freecom_legend.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
