package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_taxregistrationService;
import org.megapractical.billingplatform.domain.Com_taxregistration;
import org.megapractical.billingplatform.repository.Com_taxregistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_taxregistration.
 */
@Service
@Transactional
public class Com_taxregistrationServiceImpl implements Com_taxregistrationService{

    private final Logger log = LoggerFactory.getLogger(Com_taxregistrationServiceImpl.class);
    
    @Inject
    private Com_taxregistrationRepository com_taxregistrationRepository;
    
    /**
     * Save a com_taxregistration.
     * 
     * @param com_taxregistration the entity to save
     * @return the persisted entity
     */
    public Com_taxregistration save(Com_taxregistration com_taxregistration) {
        log.debug("Request to save Com_taxregistration : {}", com_taxregistration);
        Com_taxregistration result = com_taxregistrationRepository.save(com_taxregistration);
        return result;
    }

    /**
     *  Get all the com_taxregistrations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_taxregistration> findAll(Pageable pageable) {
        log.debug("Request to get all Com_taxregistrations");
        Page<Com_taxregistration> result = com_taxregistrationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_taxregistration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_taxregistration findOne(Long id) {
        log.debug("Request to get Com_taxregistration : {}", id);
        Com_taxregistration com_taxregistration = com_taxregistrationRepository.findOne(id);
        return com_taxregistration;
    }

    /**
     *  Delete the  com_taxregistration by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_taxregistration : {}", id);
        com_taxregistrationRepository.delete(id);
    }
}
