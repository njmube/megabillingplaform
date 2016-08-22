package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_commodity.
 */
public interface Freecom_commodityService {

    /**
     * Save a freecom_commodity.
     * 
     * @param freecom_commodity the entity to save
     * @return the persisted entity
     */
    Freecom_commodity save(Freecom_commodity freecom_commodity);

    /**
     *  Get all the freecom_commodities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_commodity> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_commodity.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_commodity findOne(Long id);

    /**
     *  Delete the "id" freecom_commodity.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
