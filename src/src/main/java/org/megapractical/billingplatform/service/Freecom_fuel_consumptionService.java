package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_fuel_consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_fuel_consumption.
 */
public interface Freecom_fuel_consumptionService {

    /**
     * Save a freecom_fuel_consumption.
     * 
     * @param freecom_fuel_consumption the entity to save
     * @return the persisted entity
     */
    Freecom_fuel_consumption save(Freecom_fuel_consumption freecom_fuel_consumption);

    /**
     *  Get all the freecom_fuel_consumptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_fuel_consumption> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_fuel_consumption.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_fuel_consumption findOne(Long id);

    /**
     *  Delete the "id" freecom_fuel_consumption.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
