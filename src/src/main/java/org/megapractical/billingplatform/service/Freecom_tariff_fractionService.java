package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_tariff_fraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_tariff_fraction.
 */
public interface Freecom_tariff_fractionService {

    /**
     * Save a freecom_tariff_fraction.
     *
     * @param freecom_tariff_fraction the entity to save
     * @return the persisted entity
     */
    Freecom_tariff_fraction save(Freecom_tariff_fraction freecom_tariff_fraction);

    /**
     *  Get all the freecom_tariff_fractions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_tariff_fraction> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_tariff_fraction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_tariff_fraction findOne(Long id);

    /**
     *  Delete the "id" freecom_tariff_fraction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<Freecom_tariff_fraction> findAll();
}
