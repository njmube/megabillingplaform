package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_municipality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_municipality.
 */
public interface C_municipalityService {

    /**
     * Save a c_municipality.
     *
     * @param c_municipality the entity to save
     * @return the persisted entity
     */
    C_municipality save(C_municipality c_municipality);

    /**
     *  Get all the c_municipalities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_municipality> findAll(Pageable pageable);

    List<C_municipality> findByState(Long stateId);

    /**
     *  Get the "id" c_municipality.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    C_municipality findOne(Long id);

    /**
     *  Delete the "id" c_municipality.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
