package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.ServicesService;
import org.megapractical.billingplatform.domain.Services;
import org.megapractical.billingplatform.repository.ServicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Services.
 */
@Service
@Transactional
public class ServicesServiceImpl implements ServicesService{

    private final Logger log = LoggerFactory.getLogger(ServicesServiceImpl.class);
    
    @Inject
    private ServicesRepository servicesRepository;
    
    /**
     * Save a services.
     * 
     * @param services the entity to save
     * @return the persisted entity
     */
    public Services save(Services services) {
        log.debug("Request to save Services : {}", services);
        Services result = servicesRepository.save(services);
        return result;
    }

    /**
     *  Get all the services.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Services> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        Page<Services> result = servicesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one services by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Services findOne(Long id) {
        log.debug("Request to get Services : {}", id);
        Services services = servicesRepository.findOne(id);
        return services;
    }

    /**
     *  Delete the  services by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Services : {}", id);
        servicesRepository.delete(id);
    }
}
