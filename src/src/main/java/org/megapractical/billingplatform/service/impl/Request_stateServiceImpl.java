package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Request_stateService;
import org.megapractical.billingplatform.domain.Request_state;
import org.megapractical.billingplatform.repository.Request_stateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Request_state.
 */
@Service
@Transactional
public class Request_stateServiceImpl implements Request_stateService{

    private final Logger log = LoggerFactory.getLogger(Request_stateServiceImpl.class);

    @Inject
    private Request_stateRepository request_stateRepository;

    /**
     * Save a request_state.
     *
     * @param request_state the entity to save
     * @return the persisted entity
     */
    public Request_state save(Request_state request_state) {
        log.debug("Request to save Request_state : {}", request_state);
        Request_state result = request_stateRepository.save(request_state);
        return result;
    }

    /**
     *  Get all the request_states.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Request_state> findAll(Pageable pageable) {
        log.debug("Request to get all Request_states");
        Page<Request_state> result = request_stateRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Request_state> findAllByName(String filtername, Pageable pageable) {
        log.debug("Request to get Request_states whith filtername: {}",filtername);
        Page<Request_state> result = request_stateRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }

    /**
     *  Get one request_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Request_state findOne(Long id) {
        log.debug("Request to get Request_state : {}", id);
        Request_state request_state = request_stateRepository.findOne(id);
        return request_state;
    }

    /**
     *  Delete the  request_state by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Request_state : {}", id);
        request_stateRepository.delete(id);
    }
}
