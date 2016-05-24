package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Tax_regime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tax_regime.
 */
public interface Tax_regimeService {

    /**
     * Save a tax_regime.
     * 
     * @param tax_regime the entity to save
     * @return the persisted entity
     */
    Tax_regime save(Tax_regime tax_regime);

    /**
     *  Get all the tax_regimes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tax_regime> findAll(Pageable pageable);

    /**
     *  Get the "id" tax_regime.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Tax_regime findOne(Long id);

    /**
     *  Delete the "id" tax_regime.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
