package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.School_level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing School_level.
 */
public interface School_levelService {

    /**
     * Save a school_level.
     * 
     * @param school_level the entity to save
     * @return the persisted entity
     */
    School_level save(School_level school_level);

    /**
     *  Get all the school_levels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<School_level> findAll(Pageable pageable);

    /**
     *  Get the "id" school_level.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    School_level findOne(Long id);

    /**
     *  Delete the "id" school_level.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
