package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Cfdi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cfdi.
 */
public interface CfdiService {

    /**
     * Save a cfdi.
     * 
     * @param cfdi the entity to save
     * @return the persisted entity
     */
    Cfdi save(Cfdi cfdi);

    /**
     *  Get all the cfdis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cfdi> findAll(Pageable pageable);

    /**
     *  Get the "id" cfdi.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Cfdi findOne(Long id);

    /**
     *  Delete the "id" cfdi.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
