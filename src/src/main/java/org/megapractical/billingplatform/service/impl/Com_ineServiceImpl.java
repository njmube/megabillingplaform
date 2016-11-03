package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_ineService;
import org.megapractical.billingplatform.domain.Com_ine;
import org.megapractical.billingplatform.repository.Com_ineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_ine.
 */
@Service
@Transactional
public class Com_ineServiceImpl implements Com_ineService{

    private final Logger log = LoggerFactory.getLogger(Com_ineServiceImpl.class);
    
    @Inject
    private Com_ineRepository com_ineRepository;
    
    /**
     * Save a com_ine.
     * 
     * @param com_ine the entity to save
     * @return the persisted entity
     */
    public Com_ine save(Com_ine com_ine) {
        log.debug("Request to save Com_ine : {}", com_ine);
        Com_ine result = com_ineRepository.save(com_ine);
        return result;
    }

    /**
     *  Get all the com_ines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_ine> findAll(Pageable pageable) {
        log.debug("Request to get all Com_ines");
        Page<Com_ine> result = com_ineRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_ine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_ine findOne(Long id) {
        log.debug("Request to get Com_ine : {}", id);
        Com_ine com_ine = com_ineRepository.findOne(id);
        return com_ine;
    }

    /**
     *  Delete the  com_ine by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_ine : {}", id);
        com_ineRepository.delete(id);
    }
}
