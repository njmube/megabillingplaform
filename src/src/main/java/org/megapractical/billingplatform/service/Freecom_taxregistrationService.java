package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_taxregistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_taxregistration.
 */
public interface Freecom_taxregistrationService {

    /**
     * Save a freecom_taxregistration.
     * 
     * @param freecom_taxregistration the entity to save
     * @return the persisted entity
     */
    Freecom_taxregistration save(Freecom_taxregistration freecom_taxregistration);

    /**
     *  Get all the freecom_taxregistrations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_taxregistration> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_taxregistration.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_taxregistration findOne(Long id);

    /**
     *  Delete the "id" freecom_taxregistration.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
