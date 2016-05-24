package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Services.
 */
public interface ServicesService {

    /**
     * Save a services.
     * 
     * @param services the entity to save
     * @return the persisted entity
     */
    Services save(Services services);

    /**
     *  Get all the services.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Services> findAll(Pageable pageable);

    /**
     *  Get the "id" services.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Services findOne(Long id);

    /**
     *  Delete the "id" services.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
