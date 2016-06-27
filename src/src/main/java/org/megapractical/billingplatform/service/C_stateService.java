package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.domain.C_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_state.
 */
public interface C_stateService {

    /**
     * Save a c_state.
     *
     * @param c_state the entity to save
     * @return the persisted entity
     */
    C_state save(C_state c_state);

    /**
     *  Get all the c_states.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_state> findAll(Pageable pageable);

    List<C_state> findByCountry(long countryId);

    /**
     *  Get the "id" c_state.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    C_state findOne(Long id);

    /**
     *  Delete the "id" c_state.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
