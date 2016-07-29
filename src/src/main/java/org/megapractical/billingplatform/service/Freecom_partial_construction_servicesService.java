package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_partial_construction_services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_partial_construction_services.
 */
public interface Freecom_partial_construction_servicesService {

    /**
     * Save a freecom_partial_construction_services.
     * 
     * @param freecom_partial_construction_services the entity to save
     * @return the persisted entity
     */
    Freecom_partial_construction_services save(Freecom_partial_construction_services freecom_partial_construction_services);

    /**
     *  Get all the freecom_partial_construction_services.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_partial_construction_services> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_partial_construction_services.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_partial_construction_services findOne(Long id);

    /**
     *  Delete the "id" freecom_partial_construction_services.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
