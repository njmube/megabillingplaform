package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_municipality;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.repository.C_municipalityRepository;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.C_colonyService;
import org.megapractical.billingplatform.domain.C_colony;
import org.megapractical.billingplatform.repository.C_colonyRepository;
import org.megapractical.billingplatform.service.C_state_eventService;
import org.megapractical.billingplatform.service.TracemgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing C_colony.
 */
@Service
@Transactional
public class C_colonyServiceImpl implements C_colonyService{

    private final Logger log = LoggerFactory.getLogger(C_colonyServiceImpl.class);

    @Inject
    private C_colonyRepository c_colonyRepository;

    @Inject
    private C_municipalityRepository c_municipalityRepository;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * Save a c_colony.
     *
     * @param c_colony the entity to save
     * @return the persisted entity
     */
    public C_colony save(C_colony c_colony) {
        log.debug("Request to save C_colony : {}", c_colony);
        C_colony result = c_colonyRepository.save(c_colony);
        Long idauditevent = new Long("10");
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

    public Page<C_colony> findAllByName(String filtername, Pageable pageable){
        log.debug("Request to get all C_colonies");
        Page<C_colony> result = c_colonyRepository.findByCodeStartingWith(filtername, pageable);
        return result;
    }

    public List<C_colony> findAllByNameL(String filtername){
        log.debug("Request to get all C_colonies");
        List<C_colony> result = c_colonyRepository.findByCodeStartingWith(filtername);
        return result;
    }

    /**
     *  Get all the c_colonies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_colony> findAll(Pageable pageable) {
        log.debug("Request to get all C_colonies");
        Page<C_colony> result = c_colonyRepository.findAll(pageable);
        return result;
    }

    public List<C_colony> findByMunicipality(long municipalityId){
        log.debug("Request to get all C_Colonys");

        if (municipalityId == -1)
            return c_colonyRepository.findAll();

        C_municipality municipality=  c_municipalityRepository.findOne(municipalityId);
        return c_colonyRepository.findByM(municipality);
    }

    /**
     *  Get one c_colony by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_colony findOne(Long id) {
        log.debug("Request to get C_colony : {}", id);
        C_colony c_colony = c_colonyRepository.findOne(id);
        return c_colony;
    }

    /**
     *  Delete the  c_colony by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_colony : {}", id);
        c_colonyRepository.delete(id);
        Long idauditevent = new Long("11");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
    }
}
