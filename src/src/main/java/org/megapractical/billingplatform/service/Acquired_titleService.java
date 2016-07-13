package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Acquired_title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Acquired_title.
 */
public interface Acquired_titleService {

    /**
     * Save a acquired_title.
     * 
     * @param acquired_title the entity to save
     * @return the persisted entity
     */
    Acquired_title save(Acquired_title acquired_title);

    /**
     *  Get all the acquired_titles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Acquired_title> findAll(Pageable pageable);

    /**
     *  Get the "id" acquired_title.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Acquired_title findOne(Long id);

    /**
     *  Delete the "id" acquired_title.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
