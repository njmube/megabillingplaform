package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_colony;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_colony.
 */
public interface C_colonyService {

    /**
     * Save a c_colony.
     *
     * @param c_colony the entity to save
     * @return the persisted entity
     */
    C_colony save(C_colony c_colony);

    /**
     *  Get all the c_colonies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_colony> findAll(Pageable pageable);

    List<C_colony> findByMunicipality(long municipalityId);

    /**
     *  Get the "id" c_colony.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    C_colony findOne(Long id);

    /**
     *  Delete the "id" c_colony.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
