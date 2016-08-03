package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.C_committee_typeService;
import org.megapractical.billingplatform.domain.C_committee_type;
import org.megapractical.billingplatform.repository.C_committee_typeRepository;
import org.megapractical.billingplatform.service.C_state_eventService;
import org.megapractical.billingplatform.service.TracemgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_committee_type.
 */
@Service
@Transactional
public class C_committee_typeServiceImpl implements C_committee_typeService{

    private final Logger log = LoggerFactory.getLogger(C_committee_typeServiceImpl.class);

    @Inject
    private C_committee_typeRepository c_committee_typeRepository;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * Save a c_committee_type.
     *
     * @param c_committee_type the entity to save
     * @return the persisted entity
     */
    public C_committee_type save(C_committee_type c_committee_type) {
        log.debug("Request to save C_committee_type : {}", c_committee_type);
        C_committee_type result = c_committee_typeRepository.save(c_committee_type);
        Long idauditevent = new Long("12");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        if(result != null){
            Long idstate = new Long("1");
            c_state_event = c_state_eventService.findOne(idstate);
        }
        else
        {
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);
        }
        tracemgService.saveTrace(audit_event_type, c_state_event);
        return result;
    }

    /**
     *  Get all the c_committee_types.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_committee_type> findAll(Pageable pageable) {
        log.debug("Request to get all C_committee_types");
        Page<C_committee_type> result = c_committee_typeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one c_committee_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_committee_type findOne(Long id) {
        log.debug("Request to get C_committee_type : {}", id);
        C_committee_type c_committee_type = c_committee_typeRepository.findOne(id);
        return c_committee_type;
    }

    /**
     *  Delete the  c_committee_type by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_committee_type : {}", id);
        c_committee_typeRepository.delete(id);
        Long idauditevent = new Long("13");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
    }
}
