package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_spei_third;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_spei_third.
 */
public interface Freecom_spei_thirdService {

    /**
     * Save a freecom_spei_third.
     * 
     * @param freecom_spei_third the entity to save
     * @return the persisted entity
     */
    Freecom_spei_third save(Freecom_spei_third freecom_spei_third);

    /**
     *  Get all the freecom_spei_thirds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_spei_third> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_spei_third.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_spei_third findOne(Long id);

    /**
     *  Delete the "id" freecom_spei_third.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
