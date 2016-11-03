package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_foreign_tourist_passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_foreign_tourist_passenger.
 */
public interface Com_foreign_tourist_passengerService {

    /**
     * Save a com_foreign_tourist_passenger.
     * 
     * @param com_foreign_tourist_passenger the entity to save
     * @return the persisted entity
     */
    Com_foreign_tourist_passenger save(Com_foreign_tourist_passenger com_foreign_tourist_passenger);

    /**
     *  Get all the com_foreign_tourist_passengers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_foreign_tourist_passenger> findAll(Pageable pageable);

    /**
     *  Get the "id" com_foreign_tourist_passenger.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_foreign_tourist_passenger findOne(Long id);

    /**
     *  Delete the "id" com_foreign_tourist_passenger.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
