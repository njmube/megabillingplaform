package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_info_customs_destructionService;
import org.megapractical.billingplatform.domain.Com_info_customs_destruction;
import org.megapractical.billingplatform.repository.Com_info_customs_destructionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_info_customs_destruction.
 */
@Service
@Transactional
public class Com_info_customs_destructionServiceImpl implements Com_info_customs_destructionService{

    private final Logger log = LoggerFactory.getLogger(Com_info_customs_destructionServiceImpl.class);
    
    @Inject
    private Com_info_customs_destructionRepository com_info_customs_destructionRepository;
    
    /**
     * Save a com_info_customs_destruction.
     * 
     * @param com_info_customs_destruction the entity to save
     * @return the persisted entity
     */
    public Com_info_customs_destruction save(Com_info_customs_destruction com_info_customs_destruction) {
        log.debug("Request to save Com_info_customs_destruction : {}", com_info_customs_destruction);
        Com_info_customs_destruction result = com_info_customs_destructionRepository.save(com_info_customs_destruction);
        return result;
    }

    /**
     *  Get all the com_info_customs_destructions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_info_customs_destruction> findAll(Pageable pageable) {
        log.debug("Request to get all Com_info_customs_destructions");
        Page<Com_info_customs_destruction> result = com_info_customs_destructionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_info_customs_destruction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_info_customs_destruction findOne(Long id) {
        log.debug("Request to get Com_info_customs_destruction : {}", id);
        Com_info_customs_destruction com_info_customs_destruction = com_info_customs_destructionRepository.findOne(id);
        return com_info_customs_destruction;
    }

    /**
     *  Delete the  com_info_customs_destruction by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_info_customs_destruction : {}", id);
        com_info_customs_destructionRepository.delete(id);
    }
}
