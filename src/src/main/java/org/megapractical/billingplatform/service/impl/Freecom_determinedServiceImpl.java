package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_determinedService;
import org.megapractical.billingplatform.domain.Freecom_determined;
import org.megapractical.billingplatform.repository.Freecom_determinedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_determined.
 */
@Service
@Transactional
public class Freecom_determinedServiceImpl implements Freecom_determinedService{

    private final Logger log = LoggerFactory.getLogger(Freecom_determinedServiceImpl.class);
    
    @Inject
    private Freecom_determinedRepository freecom_determinedRepository;
    
    /**
     * Save a freecom_determined.
     * 
     * @param freecom_determined the entity to save
     * @return the persisted entity
     */
    public Freecom_determined save(Freecom_determined freecom_determined) {
        log.debug("Request to save Freecom_determined : {}", freecom_determined);
        Freecom_determined result = freecom_determinedRepository.save(freecom_determined);
        return result;
    }

    /**
     *  Get all the freecom_determineds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_determined> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_determineds");
        Page<Freecom_determined> result = freecom_determinedRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_determined by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_determined findOne(Long id) {
        log.debug("Request to get Freecom_determined : {}", id);
        Freecom_determined freecom_determined = freecom_determinedRepository.findOne(id);
        return freecom_determined;
    }

    /**
     *  Delete the  freecom_determined by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_determined : {}", id);
        freecom_determinedRepository.delete(id);
    }
}
