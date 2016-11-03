package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_tariff_fraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_tariff_fraction.
 */
public interface Com_tariff_fractionService {

    /**
     * Save a com_tariff_fraction.
     * 
     * @param com_tariff_fraction the entity to save
     * @return the persisted entity
     */
    Com_tariff_fraction save(Com_tariff_fraction com_tariff_fraction);

    /**
     *  Get all the com_tariff_fractions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_tariff_fraction> findAll(Pageable pageable);

    /**
     *  Get the "id" com_tariff_fraction.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_tariff_fraction findOne(Long id);

    /**
     *  Delete the "id" com_tariff_fraction.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
