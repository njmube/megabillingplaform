package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_complement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_complement.
 */
public interface C_complementService {

    /**
     * Save a c_complement.
     *
     * @param c_complement the entity to save
     * @return the persisted entity
     */
    C_complement save(C_complement c_complement);

    /**
     *  Get all the c_complements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_complement> findAll(Pageable pageable);

    Page<C_complement> findAllByName(String filtername, Pageable pageable);
    List<C_complement> findAllByNameL(String filtername);
    List<C_complement> findAll();

    /**
     *  Get the "id" c_complement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    C_complement findOne(Long id);

    /**
     *  Delete the "id" c_complement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
