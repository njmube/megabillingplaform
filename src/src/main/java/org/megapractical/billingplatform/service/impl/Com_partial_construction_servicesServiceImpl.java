package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_partial_construction_servicesService;
import org.megapractical.billingplatform.domain.Com_partial_construction_services;
import org.megapractical.billingplatform.repository.Com_partial_construction_servicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_partial_construction_services.
 */
@Service
@Transactional
public class Com_partial_construction_servicesServiceImpl implements Com_partial_construction_servicesService{

    private final Logger log = LoggerFactory.getLogger(Com_partial_construction_servicesServiceImpl.class);
    
    @Inject
    private Com_partial_construction_servicesRepository com_partial_construction_servicesRepository;
    
    /**
     * Save a com_partial_construction_services.
     * 
     * @param com_partial_construction_services the entity to save
     * @return the persisted entity
     */
    public Com_partial_construction_services save(Com_partial_construction_services com_partial_construction_services) {
        log.debug("Request to save Com_partial_construction_services : {}", com_partial_construction_services);
        Com_partial_construction_services result = com_partial_construction_servicesRepository.save(com_partial_construction_services);
        return result;
    }

    /**
     *  Get all the com_partial_construction_services.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_partial_construction_services> findAll(Pageable pageable) {
        log.debug("Request to get all Com_partial_construction_services");
        Page<Com_partial_construction_services> result = com_partial_construction_servicesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_partial_construction_services by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_partial_construction_services findOne(Long id) {
        log.debug("Request to get Com_partial_construction_services : {}", id);
        Com_partial_construction_services com_partial_construction_services = com_partial_construction_servicesRepository.findOne(id);
        return com_partial_construction_services;
    }

    /**
     *  Delete the  com_partial_construction_services by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_partial_construction_services : {}", id);
        com_partial_construction_servicesRepository.delete(id);
    }
}
