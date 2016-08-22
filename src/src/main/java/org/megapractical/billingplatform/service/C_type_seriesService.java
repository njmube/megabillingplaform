package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_type_series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_type_series.
 */
public interface C_type_seriesService {

    /**
     * Save a c_type_series.
     * 
     * @param c_type_series the entity to save
     * @return the persisted entity
     */
    C_type_series save(C_type_series c_type_series);

    /**
     *  Get all the c_type_series.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_type_series> findAll(Pageable pageable);

    /**
     *  Get the "id" c_type_series.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_type_series findOne(Long id);

    /**
     *  Delete the "id" c_type_series.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
