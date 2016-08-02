package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Audit_event_type.
 */
public interface Audit_event_typeService {

    /**
     * Save a audit_event_type.
     *
     * @param audit_event_type the entity to save
     * @return the persisted entity
     */
    Audit_event_type save(Audit_event_type audit_event_type);

    /**
     *  Get all the audit_event_types.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Audit_event_type> findAll(Pageable pageable);

    List<Audit_event_type> findAll();

    /**
     *  Get the "id" audit_event_type.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Audit_event_type findOne(Long id);

    Audit_event_type findByName(String name);

    /**
     *  Delete the "id" audit_event_type.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
