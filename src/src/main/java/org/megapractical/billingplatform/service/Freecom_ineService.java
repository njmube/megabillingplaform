package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_ine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_ine.
 */
public interface Freecom_ineService {

    /**
     * Save a freecom_ine.
     * 
     * @param freecom_ine the entity to save
     * @return the persisted entity
     */
    Freecom_ine save(Freecom_ine freecom_ine);

    /**
     *  Get all the freecom_ines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_ine> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_ine.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_ine findOne(Long id);

    /**
     *  Delete the "id" freecom_ine.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
