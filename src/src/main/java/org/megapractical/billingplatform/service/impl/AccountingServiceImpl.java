package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.service.AccountingService;
import org.megapractical.billingplatform.domain.Accounting;
import org.megapractical.billingplatform.repository.AccountingRepository;
import org.megapractical.billingplatform.service.Audit_event_typeService;
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
 * Service Implementation for managing Accounting.
 */
@Service
@Transactional
public class AccountingServiceImpl implements AccountingService{

    private final Logger log = LoggerFactory.getLogger(AccountingServiceImpl.class);

    @Inject
    private AccountingRepository accountingRepository;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * Save a accounting.
     *
     * @param accounting the entity to save
     * @return the persisted entity
     */
    public Accounting save(Accounting accounting) {
        log.debug("Request to save Accounting : {}", accounting);
        Accounting result = accountingRepository.save(accounting);
        Long idauditevent = new Long("18");
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
     *  Get all the accountings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Accounting> findAll(Pageable pageable) {
        log.debug("Request to get all Accountings");
        Page<Accounting> result = accountingRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one accounting by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Accounting findOne(Long id) {
        log.debug("Request to get Accounting : {}", id);
        Accounting accounting = accountingRepository.findOne(id);
        return accounting;
    }

    /**
     *  Delete the  accounting by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Accounting : {}", id);
        accountingRepository.delete(id);
        Long idauditevent = new Long("19");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
    }
}
