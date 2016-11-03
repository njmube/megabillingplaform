package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_vehicle_customs_information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_vehicle_customs_information.
 */
public interface Com_vehicle_customs_informationService {

    /**
     * Save a com_vehicle_customs_information.
     * 
     * @param com_vehicle_customs_information the entity to save
     * @return the persisted entity
     */
    Com_vehicle_customs_information save(Com_vehicle_customs_information com_vehicle_customs_information);

    /**
     *  Get all the com_vehicle_customs_informations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_vehicle_customs_information> findAll(Pageable pageable);

    /**
     *  Get the "id" com_vehicle_customs_information.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_vehicle_customs_information findOne(Long id);

    /**
     *  Delete the "id" com_vehicle_customs_information.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
