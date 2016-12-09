package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_notary_data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_notary_data.
 */
public interface Com_notary_dataService {

    /**
     * Save a com_notary_data.
     * 
     * @param com_notary_data the entity to save
     * @return the persisted entity
     */
    Com_notary_data save(Com_notary_data com_notary_data);

    /**
     *  Get all the com_notary_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_notary_data> findAll(Pageable pageable);

    /**
     *  Get the "id" com_notary_data.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_notary_data findOne(Long id);

    /**
     *  Delete the "id" com_notary_data.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
