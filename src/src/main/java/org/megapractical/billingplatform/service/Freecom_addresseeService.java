package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_addressee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_addressee.
 */
public interface Freecom_addresseeService {

    /**
     * Save a freecom_addressee.
     * 
     * @param freecom_addressee the entity to save
     * @return the persisted entity
     */
    Freecom_addressee save(Freecom_addressee freecom_addressee);

    /**
     *  Get all the freecom_addressees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_addressee> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_addressee.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_addressee findOne(Long id);

    /**
     *  Delete the "id" freecom_addressee.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
