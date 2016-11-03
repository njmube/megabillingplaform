package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_used_vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_used_vehicle.
 */
public interface Com_used_vehicleService {

    /**
     * Save a com_used_vehicle.
     * 
     * @param com_used_vehicle the entity to save
     * @return the persisted entity
     */
    Com_used_vehicle save(Com_used_vehicle com_used_vehicle);

    /**
     *  Get all the com_used_vehicles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_used_vehicle> findAll(Pageable pageable);

    /**
     *  Get the "id" com_used_vehicle.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_used_vehicle findOne(Long id);

    /**
     *  Delete the "id" com_used_vehicle.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
