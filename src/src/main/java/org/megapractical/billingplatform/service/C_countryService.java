package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_country.
 */
public interface C_countryService {

    /**
     * Save a c_country.
     *
     * @param c_country the entity to save
     * @return the persisted entity
     */
    C_country save(C_country c_country);

    Page<C_country> findAllByName(String filtername, Pageable pageable);
    List<C_country> findAllByNameL(String filtername);

    /**
     *  Get all the c_countries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_country> findAll(Pageable pageable);

    List<C_country> findAll();

    /**
     *  Get the "id" c_country.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    C_country findOne(Long id);

    /**
     *  Delete the "id" c_country.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
