package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Archive_status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Archive_status.
 */
public interface Archive_statusService {

    /**
     * Save a archive_status.
     * 
     * @param archive_status the entity to save
     * @return the persisted entity
     */
    Archive_status save(Archive_status archive_status);

    /**
     *  Get all the archive_statuses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Archive_status> findAll(Pageable pageable);

    /**
     *  Get the "id" archive_status.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Archive_status findOne(Long id);

    /**
     *  Delete the "id" archive_status.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
