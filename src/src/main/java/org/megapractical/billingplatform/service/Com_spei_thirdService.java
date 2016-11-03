package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_spei_third;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_spei_third.
 */
public interface Com_spei_thirdService {

    /**
     * Save a com_spei_third.
     * 
     * @param com_spei_third the entity to save
     * @return the persisted entity
     */
    Com_spei_third save(Com_spei_third com_spei_third);

    /**
     *  Get all the com_spei_thirds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_spei_third> findAll(Pageable pageable);

    /**
     *  Get the "id" com_spei_third.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_spei_third findOne(Long id);

    /**
     *  Delete the "id" com_spei_third.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
