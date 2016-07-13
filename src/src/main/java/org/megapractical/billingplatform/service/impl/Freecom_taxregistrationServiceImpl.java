package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_taxregistrationService;
import org.megapractical.billingplatform.domain.Freecom_taxregistration;
import org.megapractical.billingplatform.repository.Freecom_taxregistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_taxregistration.
 */
@Service
@Transactional
public class Freecom_taxregistrationServiceImpl implements Freecom_taxregistrationService{

    private final Logger log = LoggerFactory.getLogger(Freecom_taxregistrationServiceImpl.class);
    
    @Inject
    private Freecom_taxregistrationRepository freecom_taxregistrationRepository;
    
    /**
     * Save a freecom_taxregistration.
     * 
     * @param freecom_taxregistration the entity to save
     * @return the persisted entity
     */
    public Freecom_taxregistration save(Freecom_taxregistration freecom_taxregistration) {
        log.debug("Request to save Freecom_taxregistration : {}", freecom_taxregistration);
        Freecom_taxregistration result = freecom_taxregistrationRepository.save(freecom_taxregistration);
        return result;
    }

    /**
     *  Get all the freecom_taxregistrations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_taxregistration> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_taxregistrations");
        Page<Freecom_taxregistration> result = freecom_taxregistrationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_taxregistration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_taxregistration findOne(Long id) {
        log.debug("Request to get Freecom_taxregistration : {}", id);
        Freecom_taxregistration freecom_taxregistration = freecom_taxregistrationRepository.findOne(id);
        return freecom_taxregistration;
    }

    /**
     *  Delete the  freecom_taxregistration by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_taxregistration : {}", id);
        freecom_taxregistrationRepository.delete(id);
    }
}
