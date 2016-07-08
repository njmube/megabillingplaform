package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Package_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Package_state.
 */
public interface Package_stateService {

    /**
     * Save a package_state.
     *
     * @param package_state the entity to save
     * @return the persisted entity
     */
    Package_state save(Package_state package_state);

    /**
     *  Get all the package_states.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Package_state> findAll(Pageable pageable);

    Page<Package_state> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" package_state.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Package_state findOne(Long id);

    /**
     *  Delete the "id" package_state.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
