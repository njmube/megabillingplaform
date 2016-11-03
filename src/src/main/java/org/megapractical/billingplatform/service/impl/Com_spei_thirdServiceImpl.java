package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_spei_thirdService;
import org.megapractical.billingplatform.domain.Com_spei_third;
import org.megapractical.billingplatform.repository.Com_spei_thirdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_spei_third.
 */
@Service
@Transactional
public class Com_spei_thirdServiceImpl implements Com_spei_thirdService{

    private final Logger log = LoggerFactory.getLogger(Com_spei_thirdServiceImpl.class);
    
    @Inject
    private Com_spei_thirdRepository com_spei_thirdRepository;
    
    /**
     * Save a com_spei_third.
     * 
     * @param com_spei_third the entity to save
     * @return the persisted entity
     */
    public Com_spei_third save(Com_spei_third com_spei_third) {
        log.debug("Request to save Com_spei_third : {}", com_spei_third);
        Com_spei_third result = com_spei_thirdRepository.save(com_spei_third);
        return result;
    }

    /**
     *  Get all the com_spei_thirds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_spei_third> findAll(Pageable pageable) {
        log.debug("Request to get all Com_spei_thirds");
        Page<Com_spei_third> result = com_spei_thirdRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_spei_third by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_spei_third findOne(Long id) {
        log.debug("Request to get Com_spei_third : {}", id);
        Com_spei_third com_spei_third = com_spei_thirdRepository.findOne(id);
        return com_spei_third;
    }

    /**
     *  Delete the  com_spei_third by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_spei_third : {}", id);
        com_spei_thirdRepository.delete(id);
    }
}
