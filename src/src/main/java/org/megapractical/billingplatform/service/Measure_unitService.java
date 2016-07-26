package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Measure_unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Measure_unit.
 */
public interface Measure_unitService {

    /**
     * Save a measure_unit.
     *
     * @param measure_unit the entity to save
     * @return the persisted entity
     */
    Measure_unit save(Measure_unit measure_unit);

    /**
     *  Get all the measure_units.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Measure_unit> findAll(Pageable pageable);

    Page<Measure_unit> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" measure_unit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Measure_unit findOne(Long id);

    /**
     *  Delete the "id" measure_unit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get all the measure_units.
     *
     *  @return the list of entities
     */
    List<Measure_unit> findAll();
}
