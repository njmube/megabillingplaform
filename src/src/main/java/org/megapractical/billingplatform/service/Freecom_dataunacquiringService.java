package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_dataunacquiring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_dataunacquiring.
 */
public interface Freecom_dataunacquiringService {

    /**
     * Save a freecom_dataunacquiring.
     * 
     * @param freecom_dataunacquiring the entity to save
     * @return the persisted entity
     */
    Freecom_dataunacquiring save(Freecom_dataunacquiring freecom_dataunacquiring);

    /**
     *  Get all the freecom_dataunacquirings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_dataunacquiring> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_dataunacquiring.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_dataunacquiring findOne(Long id);

    /**
     *  Delete the "id" freecom_dataunacquiring.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
