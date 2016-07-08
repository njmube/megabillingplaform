package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Rate_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Rate_type.
 */
public interface Rate_typeService {

    /**
     * Save a rate_type.
     *
     * @param rate_type the entity to save
     * @return the persisted entity
     */
    Rate_type save(Rate_type rate_type);

    /**
     *  Get all the rate_types.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Rate_type> findAll(Pageable pageable);

    Page<Rate_type> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" rate_type.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Rate_type findOne(Long id);

    /**
     *  Delete the "id" rate_type.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
