package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Cfdi_states;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cfdi_states.
 */
public interface Cfdi_statesService {

    /**
     * Save a cfdi_states.
     * 
     * @param cfdi_states the entity to save
     * @return the persisted entity
     */
    Cfdi_states save(Cfdi_states cfdi_states);

    /**
     *  Get all the cfdi_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cfdi_states> findAll(Pageable pageable);

    /**
     *  Get the "id" cfdi_states.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Cfdi_states findOne(Long id);

    /**
     *  Delete the "id" cfdi_states.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
