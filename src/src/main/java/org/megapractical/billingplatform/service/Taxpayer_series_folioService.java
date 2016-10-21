package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_series_folio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_series_folio.
 */
public interface Taxpayer_series_folioService {

    /**
     * Save a taxpayer_series_folio.
     * 
     * @param taxpayer_series_folio the entity to save
     * @return the persisted entity
     */
    Taxpayer_series_folio save(Taxpayer_series_folio taxpayer_series_folio);

    /**
     *  Get all the taxpayer_series_folios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_series_folio> findAll(Pageable pageable);

    /**
     *  Get the "id" taxpayer_series_folio.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_series_folio findOne(Long id);

    /**
     *  Delete the "id" taxpayer_series_folio.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
