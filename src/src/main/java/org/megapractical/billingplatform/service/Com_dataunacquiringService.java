package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_dataunacquiring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_dataunacquiring.
 */
public interface Com_dataunacquiringService {

    /**
     * Save a com_dataunacquiring.
     * 
     * @param com_dataunacquiring the entity to save
     * @return the persisted entity
     */
    Com_dataunacquiring save(Com_dataunacquiring com_dataunacquiring);

    /**
     *  Get all the com_dataunacquirings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_dataunacquiring> findAll(Pageable pageable);

    /**
     *  Get the "id" com_dataunacquiring.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_dataunacquiring findOne(Long id);

    /**
     *  Delete the "id" com_dataunacquiring.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
