package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_beneficiaryService;
import org.megapractical.billingplatform.domain.Com_beneficiary;
import org.megapractical.billingplatform.repository.Com_beneficiaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_beneficiary.
 */
@Service
@Transactional
public class Com_beneficiaryServiceImpl implements Com_beneficiaryService{

    private final Logger log = LoggerFactory.getLogger(Com_beneficiaryServiceImpl.class);
    
    @Inject
    private Com_beneficiaryRepository com_beneficiaryRepository;
    
    /**
     * Save a com_beneficiary.
     * 
     * @param com_beneficiary the entity to save
     * @return the persisted entity
     */
    public Com_beneficiary save(Com_beneficiary com_beneficiary) {
        log.debug("Request to save Com_beneficiary : {}", com_beneficiary);
        Com_beneficiary result = com_beneficiaryRepository.save(com_beneficiary);
        return result;
    }

    /**
     *  Get all the com_beneficiaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_beneficiary> findAll(Pageable pageable) {
        log.debug("Request to get all Com_beneficiaries");
        Page<Com_beneficiary> result = com_beneficiaryRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_beneficiary by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_beneficiary findOne(Long id) {
        log.debug("Request to get Com_beneficiary : {}", id);
        Com_beneficiary com_beneficiary = com_beneficiaryRepository.findOne(id);
        return com_beneficiary;
    }

    /**
     *  Delete the  com_beneficiary by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_beneficiary : {}", id);
        com_beneficiaryRepository.delete(id);
    }
}
