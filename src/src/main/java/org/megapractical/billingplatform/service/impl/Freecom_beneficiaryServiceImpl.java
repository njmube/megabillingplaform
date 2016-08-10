package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_beneficiaryService;
import org.megapractical.billingplatform.domain.Freecom_beneficiary;
import org.megapractical.billingplatform.repository.Freecom_beneficiaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_beneficiary.
 */
@Service
@Transactional
public class Freecom_beneficiaryServiceImpl implements Freecom_beneficiaryService{

    private final Logger log = LoggerFactory.getLogger(Freecom_beneficiaryServiceImpl.class);
    
    @Inject
    private Freecom_beneficiaryRepository freecom_beneficiaryRepository;
    
    /**
     * Save a freecom_beneficiary.
     * 
     * @param freecom_beneficiary the entity to save
     * @return the persisted entity
     */
    public Freecom_beneficiary save(Freecom_beneficiary freecom_beneficiary) {
        log.debug("Request to save Freecom_beneficiary : {}", freecom_beneficiary);
        Freecom_beneficiary result = freecom_beneficiaryRepository.save(freecom_beneficiary);
        return result;
    }

    /**
     *  Get all the freecom_beneficiaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_beneficiary> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_beneficiaries");
        Page<Freecom_beneficiary> result = freecom_beneficiaryRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_beneficiary by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_beneficiary findOne(Long id) {
        log.debug("Request to get Freecom_beneficiary : {}", id);
        Freecom_beneficiary freecom_beneficiary = freecom_beneficiaryRepository.findOne(id);
        return freecom_beneficiary;
    }

    /**
     *  Delete the  freecom_beneficiary by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_beneficiary : {}", id);
        freecom_beneficiaryRepository.delete(id);
    }
}
