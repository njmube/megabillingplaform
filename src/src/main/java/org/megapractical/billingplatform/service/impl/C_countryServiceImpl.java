package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.C_countryService;
import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.repository.C_countryRepository;
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
 * Service Implementation for managing C_country.
 */
@Service
@Transactional
public class C_countryServiceImpl implements C_countryService{

    private final Logger log = LoggerFactory.getLogger(C_countryServiceImpl.class);

    @Inject
    private C_countryRepository c_countryRepository;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * Save a c_country.
     *
     * @param c_country the entity to save
     * @return the persisted entity
     */
    public C_country save(C_country c_country) {
        log.debug("Request to save C_country : {}", c_country);
        C_country result = c_countryRepository.save(c_country);
        Long idauditevent = new Long("14");
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

    public Page<C_country> findAllByName(String filtername, Pageable pageable){
        log.debug("Request to get all C_colonies");
        Page<C_country> result = c_countryRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }

    public List<C_country> findAllByNameL(String filtername){
        log.debug("Request to get all C_colonies");
        List<C_country> result = c_countryRepository.findByNameStartingWith(filtername);
        return result;
    }

    /**
     *  Get all the c_countries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_country> findAll(Pageable pageable) {
        log.debug("Request to get all C_countries");
        Page<C_country> result = c_countryRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public List<C_country> findAll() {
        log.debug("Request to get all C_countries");
        List<C_country> result = c_countryRepository.findAll();
        return result;
    }

    /**
     *  Get one c_country by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_country findOne(Long id) {
        log.debug("Request to get C_country : {}", id);
        C_country c_country = c_countryRepository.findOne(id);
        return c_country;
    }

    /**
     *  Delete the  c_country by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_country : {}", id);
        c_countryRepository.delete(id);
        Long idauditevent = new Long("15");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
    }
}
