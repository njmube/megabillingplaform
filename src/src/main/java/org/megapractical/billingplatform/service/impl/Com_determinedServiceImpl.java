package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_determinedService;
import org.megapractical.billingplatform.domain.Com_determined;
import org.megapractical.billingplatform.repository.Com_determinedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_determined.
 */
@Service
@Transactional
public class Com_determinedServiceImpl implements Com_determinedService{

    private final Logger log = LoggerFactory.getLogger(Com_determinedServiceImpl.class);
    
    @Inject
    private Com_determinedRepository com_determinedRepository;
    
    /**
     * Save a com_determined.
     * 
     * @param com_determined the entity to save
     * @return the persisted entity
     */
    public Com_determined save(Com_determined com_determined) {
        log.debug("Request to save Com_determined : {}", com_determined);
        Com_determined result = com_determinedRepository.save(com_determined);
        return result;
    }

    /**
     *  Get all the com_determineds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_determined> findAll(Pageable pageable) {
        log.debug("Request to get all Com_determineds");
        Page<Com_determined> result = com_determinedRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_determined by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_determined findOne(Long id) {
        log.debug("Request to get Com_determined : {}", id);
        Com_determined com_determined = com_determinedRepository.findOne(id);
        return com_determined;
    }

    /**
     *  Delete the  com_determined by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_determined : {}", id);
        com_determinedRepository.delete(id);
    }
}
