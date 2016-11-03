package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_taxlegends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_taxlegends.
 */
public interface Com_taxlegendsService {

    /**
     * Save a com_taxlegends.
     * 
     * @param com_taxlegends the entity to save
     * @return the persisted entity
     */
    Com_taxlegends save(Com_taxlegends com_taxlegends);

    /**
     *  Get all the com_taxlegends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_taxlegends> findAll(Pageable pageable);

    /**
     *  Get the "id" com_taxlegends.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_taxlegends findOne(Long id);

    /**
     *  Delete the "id" com_taxlegends.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
