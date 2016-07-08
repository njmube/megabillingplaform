package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.File_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing File_type.
 */
public interface File_typeService {

    /**
     * Save a file_type.
     *
     * @param file_type the entity to save
     * @return the persisted entity
     */
    File_type save(File_type file_type);

    /**
     *  Get all the file_types.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<File_type> findAll(Pageable pageable);

    Page<File_type> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" file_type.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    File_type findOne(Long id);

    /**
     *  Delete the "id" file_type.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
