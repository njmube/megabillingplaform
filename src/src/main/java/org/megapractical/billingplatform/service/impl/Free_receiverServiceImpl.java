package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.C_state_eventService;
import org.megapractical.billingplatform.service.Free_receiverService;
import org.megapractical.billingplatform.domain.Free_receiver;
import org.megapractical.billingplatform.repository.Free_receiverRepository;
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
 * Service Implementation for managing Free_receiver.
 */
@Service
@Transactional
public class Free_receiverServiceImpl implements Free_receiverService{

    private final Logger log = LoggerFactory.getLogger(Free_receiverServiceImpl.class);

    @Inject
    private Free_receiverRepository free_receiverRepository;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * Save a free_receiver.
     *
     * @param free_receiver the entity to save
     * @return the persisted entity
     */
    public Free_receiver save(Free_receiver free_receiver) {
        log.debug("Request to save Free_receiver : {}", free_receiver);
        Free_receiver result = free_receiverRepository.save(free_receiver);
        Long idauditevent = new Long("5");
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
     *  Get all the free_receivers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Free_receiver> findAll(Pageable pageable) {
        log.debug("Request to get all Free_receivers");
        Page<Free_receiver> result = free_receiverRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one free_receiver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Free_receiver findOne(Long id) {
        log.debug("Request to get Free_receiver : {}", id);
        Free_receiver free_receiver = free_receiverRepository.findOne(id);
        return free_receiver;
    }

    /**
     *  Delete the  free_receiver by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_receiver : {}", id);
        free_receiverRepository.delete(id);
    }
}
