package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_specific_descriptionsService;
import org.megapractical.billingplatform.domain.Freecom_specific_descriptions;
import org.megapractical.billingplatform.repository.Freecom_specific_descriptionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_specific_descriptions.
 */
@Service
@Transactional
public class Freecom_specific_descriptionsServiceImpl implements Freecom_specific_descriptionsService{

    private final Logger log = LoggerFactory.getLogger(Freecom_specific_descriptionsServiceImpl.class);
    
    @Inject
    private Freecom_specific_descriptionsRepository freecom_specific_descriptionsRepository;
    
    /**
     * Save a freecom_specific_descriptions.
     * 
     * @param freecom_specific_descriptions the entity to save
     * @return the persisted entity
     */
    public Freecom_specific_descriptions save(Freecom_specific_descriptions freecom_specific_descriptions) {
        log.debug("Request to save Freecom_specific_descriptions : {}", freecom_specific_descriptions);
        Freecom_specific_descriptions result = freecom_specific_descriptionsRepository.save(freecom_specific_descriptions);
        return result;
    }

    /**
     *  Get all the freecom_specific_descriptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_specific_descriptions> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_specific_descriptions");
        Page<Freecom_specific_descriptions> result = freecom_specific_descriptionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_specific_descriptions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_specific_descriptions findOne(Long id) {
        log.debug("Request to get Freecom_specific_descriptions : {}", id);
        Freecom_specific_descriptions freecom_specific_descriptions = freecom_specific_descriptionsRepository.findOne(id);
        return freecom_specific_descriptions;
    }

    /**
     *  Delete the  freecom_specific_descriptions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_specific_descriptions : {}", id);
        freecom_specific_descriptionsRepository.delete(id);
    }
}
