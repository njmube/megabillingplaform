package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_commodity.
 */
public interface Com_commodityService {

    /**
     * Save a com_commodity.
     * 
     * @param com_commodity the entity to save
     * @return the persisted entity
     */
    Com_commodity save(Com_commodity com_commodity);

    /**
     *  Get all the com_commodities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_commodity> findAll(Pageable pageable);

    /**
     *  Get the "id" com_commodity.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_commodity findOne(Long id);

    /**
     *  Delete the "id" com_commodity.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
