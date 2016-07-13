package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_taxlegends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_taxlegends.
 */
public interface Freecom_taxlegendsService {

    /**
     * Save a freecom_taxlegends.
     * 
     * @param freecom_taxlegends the entity to save
     * @return the persisted entity
     */
    Freecom_taxlegends save(Freecom_taxlegends freecom_taxlegends);

    /**
     *  Get all the freecom_taxlegends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_taxlegends> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_taxlegends.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_taxlegends findOne(Long id);

    /**
     *  Delete the "id" freecom_taxlegends.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
