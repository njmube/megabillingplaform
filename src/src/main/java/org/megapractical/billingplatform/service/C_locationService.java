package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_location.
 */
public interface C_locationService {

    /**
     * Save a c_location.
     *
     * @param c_location the entity to save
     * @return the persisted entity
     */
    C_location save(C_location c_location);

    /**
     *  Get all the c_locations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_location> findAll(Pageable pageable);

    List<C_location> findByMunicipality(long municipalityId);

    /**
     *  Get the "id" c_location.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    C_location findOne(Long id);

    /**
     *  Delete the "id" c_location.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
