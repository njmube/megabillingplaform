package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.File_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing File_state.
 */
public interface File_stateService {

    /**
     * Save a file_state.
     *
     * @param file_state the entity to save
     * @return the persisted entity
     */
    File_state save(File_state file_state);

    /**
     *  Get all the file_states.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<File_state> findAll(Pageable pageable);

    Page<File_state> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" file_state.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    File_state findOne(Long id);

    /**
     *  Delete the "id" file_state.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
