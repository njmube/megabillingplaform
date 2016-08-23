package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_tfdService;
import org.megapractical.billingplatform.domain.Freecom_tfd;
import org.megapractical.billingplatform.repository.Freecom_tfdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_tfd.
 */
@Service
@Transactional
public class Freecom_tfdServiceImpl implements Freecom_tfdService{

    private final Logger log = LoggerFactory.getLogger(Freecom_tfdServiceImpl.class);
    
    @Inject
    private Freecom_tfdRepository freecom_tfdRepository;
    
    /**
     * Save a freecom_tfd.
     * 
     * @param freecom_tfd the entity to save
     * @return the persisted entity
     */
    public Freecom_tfd save(Freecom_tfd freecom_tfd) {
        log.debug("Request to save Freecom_tfd : {}", freecom_tfd);
        Freecom_tfd result = freecom_tfdRepository.save(freecom_tfd);
        return result;
    }

    /**
     *  Get all the freecom_tfds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_tfd> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_tfds");
        Page<Freecom_tfd> result = freecom_tfdRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_tfd by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_tfd findOne(Long id) {
        log.debug("Request to get Freecom_tfd : {}", id);
        Freecom_tfd freecom_tfd = freecom_tfdRepository.findOne(id);
        return freecom_tfd;
    }

    /**
     *  Delete the  freecom_tfd by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_tfd : {}", id);
        freecom_tfdRepository.delete(id);
    }
}
