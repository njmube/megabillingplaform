package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_foreign_tourist_passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_foreign_tourist_passenger.
 */
public interface Freecom_foreign_tourist_passengerService {

    /**
     * Save a freecom_foreign_tourist_passenger.
     * 
     * @param freecom_foreign_tourist_passenger the entity to save
     * @return the persisted entity
     */
    Freecom_foreign_tourist_passenger save(Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger);

    /**
     *  Get all the freecom_foreign_tourist_passengers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_foreign_tourist_passenger> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_foreign_tourist_passenger.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_foreign_tourist_passenger findOne(Long id);

    /**
     *  Delete the "id" freecom_foreign_tourist_passenger.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
