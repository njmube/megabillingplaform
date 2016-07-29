package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_partial_construction_servicesService;
import org.megapractical.billingplatform.domain.Freecom_partial_construction_services;
import org.megapractical.billingplatform.repository.Freecom_partial_construction_servicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_partial_construction_services.
 */
@Service
@Transactional
public class Freecom_partial_construction_servicesServiceImpl implements Freecom_partial_construction_servicesService{

    private final Logger log = LoggerFactory.getLogger(Freecom_partial_construction_servicesServiceImpl.class);
    
    @Inject
    private Freecom_partial_construction_servicesRepository freecom_partial_construction_servicesRepository;
    
    /**
     * Save a freecom_partial_construction_services.
     * 
     * @param freecom_partial_construction_services the entity to save
     * @return the persisted entity
     */
    public Freecom_partial_construction_services save(Freecom_partial_construction_services freecom_partial_construction_services) {
        log.debug("Request to save Freecom_partial_construction_services : {}", freecom_partial_construction_services);
        Freecom_partial_construction_services result = freecom_partial_construction_servicesRepository.save(freecom_partial_construction_services);
        return result;
    }

    /**
     *  Get all the freecom_partial_construction_services.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_partial_construction_services> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_partial_construction_services");
        Page<Freecom_partial_construction_services> result = freecom_partial_construction_servicesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_partial_construction_services by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_partial_construction_services findOne(Long id) {
        log.debug("Request to get Freecom_partial_construction_services : {}", id);
        Freecom_partial_construction_services freecom_partial_construction_services = freecom_partial_construction_servicesRepository.findOne(id);
        return freecom_partial_construction_services;
    }

    /**
     *  Delete the  freecom_partial_construction_services by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_partial_construction_services : {}", id);
        freecom_partial_construction_servicesRepository.delete(id);
    }
}
