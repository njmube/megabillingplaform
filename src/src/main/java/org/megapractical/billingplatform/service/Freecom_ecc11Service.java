package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_ecc11;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_ecc11.
 */
public interface Freecom_ecc11Service {

    /**
     * Save a freecom_ecc11.
     * 
     * @param freecom_ecc11 the entity to save
     * @return the persisted entity
     */
    Freecom_ecc11 save(Freecom_ecc11 freecom_ecc11);

    /**
     *  Get all the freecom_ecc11S.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_ecc11> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_ecc11.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_ecc11 findOne(Long id);

    /**
     *  Delete the "id" freecom_ecc11.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
