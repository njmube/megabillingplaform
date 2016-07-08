package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Cfdi_statesService;
import org.megapractical.billingplatform.domain.Cfdi_states;
import org.megapractical.billingplatform.repository.Cfdi_statesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cfdi_states.
 */
@Service
@Transactional
public class Cfdi_statesServiceImpl implements Cfdi_statesService{

    private final Logger log = LoggerFactory.getLogger(Cfdi_statesServiceImpl.class);

    @Inject
    private Cfdi_statesRepository cfdi_statesRepository;

    /**
     * Save a cfdi_states.
     *
     * @param cfdi_states the entity to save
     * @return the persisted entity
     */
    public Cfdi_states save(Cfdi_states cfdi_states) {
        log.debug("Request to save Cfdi_states : {}", cfdi_states);
        Cfdi_states result = cfdi_statesRepository.save(cfdi_states);
        return result;
    }

    /**
     *  Get all the cfdi_states.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Cfdi_states> findAll(Pageable pageable) {
        log.debug("Request to get all Cfdi_states");
        Page<Cfdi_states> result = cfdi_statesRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Cfdi_states> findAllByName(String filtername, Pageable pageable) {
        log.debug("Request to get Request_states whith filtername: {}",filtername);
        Page<Cfdi_states> result = cfdi_statesRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }
    /**
     *  Get one cfdi_states by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Cfdi_states findOne(Long id) {
        log.debug("Request to get Cfdi_states : {}", id);
        Cfdi_states cfdi_states = cfdi_statesRepository.findOne(id);
        return cfdi_states;
    }

    /**
     *  Delete the  cfdi_states by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cfdi_states : {}", id);
        cfdi_statesRepository.delete(id);
    }
}
