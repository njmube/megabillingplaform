package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_airline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_airline.
 */
public interface Com_airlineService {

    /**
     * Save a com_airline.
     * 
     * @param com_airline the entity to save
     * @return the persisted entity
     */
    Com_airline save(Com_airline com_airline);

    /**
     *  Get all the com_airlines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_airline> findAll(Pageable pageable);

    /**
     *  Get the "id" com_airline.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_airline findOne(Long id);

    /**
     *  Delete the "id" com_airline.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
