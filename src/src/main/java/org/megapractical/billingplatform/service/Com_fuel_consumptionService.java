package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_fuel_consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_fuel_consumption.
 */
public interface Com_fuel_consumptionService {

    /**
     * Save a com_fuel_consumption.
     * 
     * @param com_fuel_consumption the entity to save
     * @return the persisted entity
     */
    Com_fuel_consumption save(Com_fuel_consumption com_fuel_consumption);

    /**
     *  Get all the com_fuel_consumptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_fuel_consumption> findAll(Pageable pageable);

    /**
     *  Get the "id" com_fuel_consumption.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_fuel_consumption findOne(Long id);

    /**
     *  Delete the "id" com_fuel_consumption.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
