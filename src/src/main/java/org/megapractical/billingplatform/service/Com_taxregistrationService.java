package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_taxregistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_taxregistration.
 */
public interface Com_taxregistrationService {

    /**
     * Save a com_taxregistration.
     * 
     * @param com_taxregistration the entity to save
     * @return the persisted entity
     */
    Com_taxregistration save(Com_taxregistration com_taxregistration);

    /**
     *  Get all the com_taxregistrations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_taxregistration> findAll(Pageable pageable);

    /**
     *  Get the "id" com_taxregistration.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_taxregistration findOne(Long id);

    /**
     *  Delete the "id" com_taxregistration.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
