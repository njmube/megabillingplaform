package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Cfdi_types;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cfdi_types.
 */
public interface Cfdi_typesService {

    /**
     * Save a cfdi_types.
     * 
     * @param cfdi_types the entity to save
     * @return the persisted entity
     */
    Cfdi_types save(Cfdi_types cfdi_types);

    /**
     *  Get all the cfdi_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cfdi_types> findAll(Pageable pageable);

    /**
     *  Get the "id" cfdi_types.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Cfdi_types findOne(Long id);

    /**
     *  Delete the "id" cfdi_types.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
