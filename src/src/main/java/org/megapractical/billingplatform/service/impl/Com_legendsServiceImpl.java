package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_legendsService;
import org.megapractical.billingplatform.domain.Com_legends;
import org.megapractical.billingplatform.repository.Com_legendsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_legends.
 */
@Service
@Transactional
public class Com_legendsServiceImpl implements Com_legendsService{

    private final Logger log = LoggerFactory.getLogger(Com_legendsServiceImpl.class);
    
    @Inject
    private Com_legendsRepository com_legendsRepository;
    
    /**
     * Save a com_legends.
     * 
     * @param com_legends the entity to save
     * @return the persisted entity
     */
    public Com_legends save(Com_legends com_legends) {
        log.debug("Request to save Com_legends : {}", com_legends);
        Com_legends result = com_legendsRepository.save(com_legends);
        return result;
    }

    /**
     *  Get all the com_legends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_legends> findAll(Pageable pageable) {
        log.debug("Request to get all Com_legends");
        Page<Com_legends> result = com_legendsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_legends by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_legends findOne(Long id) {
        log.debug("Request to get Com_legends : {}", id);
        Com_legends com_legends = com_legendsRepository.findOne(id);
        return com_legends;
    }

    /**
     *  Delete the  com_legends by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_legends : {}", id);
        com_legendsRepository.delete(id);
    }
}
