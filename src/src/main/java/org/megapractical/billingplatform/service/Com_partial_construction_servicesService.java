package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_partial_construction_services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_partial_construction_services.
 */
public interface Com_partial_construction_servicesService {

    /**
     * Save a com_partial_construction_services.
     * 
     * @param com_partial_construction_services the entity to save
     * @return the persisted entity
     */
    Com_partial_construction_services save(Com_partial_construction_services com_partial_construction_services);

    /**
     *  Get all the com_partial_construction_services.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_partial_construction_services> findAll(Pageable pageable);

    /**
     *  Get the "id" com_partial_construction_services.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_partial_construction_services findOne(Long id);

    /**
     *  Delete the "id" com_partial_construction_services.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
