package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.repository.C_countryRepository;
import org.megapractical.billingplatform.service.C_stateService;
import org.megapractical.billingplatform.domain.C_state;
import org.megapractical.billingplatform.repository.C_stateRepository;
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
 * Service Implementation for managing C_state.
 */
@Service
@Transactional
public class C_stateServiceImpl implements C_stateService{

    private final Logger log = LoggerFactory.getLogger(C_stateServiceImpl.class);

    @Inject
    private C_stateRepository c_stateRepository;

    @Inject
    private C_countryRepository c_countryRepository;

    /**
     * Save a c_state.
     *
     * @param c_state the entity to save
     * @return the persisted entity
     */
    public C_state save(C_state c_state) {
        log.debug("Request to save C_state : {}", c_state);
        C_state result = c_stateRepository.save(c_state);
        return result;
    }

    public Page<C_state> findAllByName(String filtername, Pageable pageable){
        log.debug("Request to get all C_colonies");
        Page<C_state> result = c_stateRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }

    public List<C_state> findAllByNameL(String filtername){
        log.debug("Request to get all C_colonies");
        List<C_state> result = c_stateRepository.findByNameStartingWith(filtername);
        return result;
    }

    /**
     *  Get all the c_states.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_state> findAll(Pageable pageable) {
        log.debug("Request to get all C_states");
        Page<C_state> result = c_stateRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public List<C_state> findByCountry(long countryId){
        log.debug("Request to get all C_states");

        if (countryId == -1)
            return c_stateRepository.findAll();

        return c_stateRepository.findByC(c_countryRepository.findOne(countryId));
    }

    /**
     *  Get one c_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_state findOne(Long id) {
        log.debug("Request to get C_state : {}", id);
        C_state c_state = c_stateRepository.findOne(id);
        return c_state;
    }

    /**
     *  Delete the  c_state by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_state : {}", id);
        c_stateRepository.delete(id);
    }
}
