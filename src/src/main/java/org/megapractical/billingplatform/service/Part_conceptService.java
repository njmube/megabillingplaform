package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Part_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Part_concept.
 */
public interface Part_conceptService {

    /**
     * Save a part_concept.
     *
     * @param part_concept the entity to save
     * @return the persisted entity
     */
    Part_concept save(Part_concept part_concept);

    /**
     *  Get all the part_concepts.
     *
     *  @param pageable the pagination information
     *  @param conceptid
     * @return the list of entities
     */
    Page<Part_concept> findAll(Pageable pageable, Integer conceptid);

    /**
     *  Get the "id" part_concept.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Part_concept findOne(Long id);

    /**
     *  Delete the "id" part_concept.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
