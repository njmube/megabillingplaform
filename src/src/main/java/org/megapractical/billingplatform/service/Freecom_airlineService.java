package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_airline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_airline.
 */
public interface Freecom_airlineService {

    /**
     * Save a freecom_airline.
     * 
     * @param freecom_airline the entity to save
     * @return the persisted entity
     */
    Freecom_airline save(Freecom_airline freecom_airline);

    /**
     *  Get all the freecom_airlines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_airline> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_airline.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_airline findOne(Long id);

    /**
     *  Delete the "id" freecom_airline.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
