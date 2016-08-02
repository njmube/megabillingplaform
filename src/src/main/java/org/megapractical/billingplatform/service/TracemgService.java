package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.domain.Tracemg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing Tracemg.
 */
public interface TracemgService {

    /**
     * Save a tracemg.
     *
     * @param tracemg the entity to save
     * @return the persisted entity
     */
    Tracemg save(Tracemg tracemg);

    /**
     *  Get all the tracemgs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tracemg> findAll(Pageable pageable);

    Tracemg saveTrace(Audit_event_type audit_event_type, C_state_event c_state_event);

    Tracemg saveTraceUser(String user, Audit_event_type audit_event_type, C_state_event c_state_event);

    Page<Tracemg> findAll(ZonedDateTime from, ZonedDateTime to, Pageable pageable);

    Page<Tracemg> findCustom(ZonedDateTime from, ZonedDateTime to,String principal, String auditEventType, String ip, Pageable pageable);

    /**
     *  Get the "id" tracemg.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tracemg findOne(Long id);

    /**
     *  Delete the "id" tracemg.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
