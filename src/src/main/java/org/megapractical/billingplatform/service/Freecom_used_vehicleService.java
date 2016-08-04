package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_used_vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_used_vehicle.
 */
public interface Freecom_used_vehicleService {

    /**
     * Save a freecom_used_vehicle.
     * 
     * @param freecom_used_vehicle the entity to save
     * @return the persisted entity
     */
    Freecom_used_vehicle save(Freecom_used_vehicle freecom_used_vehicle);

    /**
     *  Get all the freecom_used_vehicles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_used_vehicle> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_used_vehicle.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_used_vehicle findOne(Long id);

    /**
     *  Delete the "id" freecom_used_vehicle.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
