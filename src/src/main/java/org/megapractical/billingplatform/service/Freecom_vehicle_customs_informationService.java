package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_vehicle_customs_information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_vehicle_customs_information.
 */
public interface Freecom_vehicle_customs_informationService {

    /**
     * Save a freecom_vehicle_customs_information.
     * 
     * @param freecom_vehicle_customs_information the entity to save
     * @return the persisted entity
     */
    Freecom_vehicle_customs_information save(Freecom_vehicle_customs_information freecom_vehicle_customs_information);

    /**
     *  Get all the freecom_vehicle_customs_informations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_vehicle_customs_information> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_vehicle_customs_information.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_vehicle_customs_information findOne(Long id);

    /**
     *  Delete the "id" freecom_vehicle_customs_information.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
