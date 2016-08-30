package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_address_requestService;
import org.megapractical.billingplatform.domain.Tax_address_request;
import org.megapractical.billingplatform.repository.Tax_address_requestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tax_address_request.
 */
@Service
@Transactional
public class Tax_address_requestServiceImpl implements Tax_address_requestService{

    private final Logger log = LoggerFactory.getLogger(Tax_address_requestServiceImpl.class);
    
    @Inject
    private Tax_address_requestRepository tax_address_requestRepository;
    
    /**
     * Save a tax_address_request.
     * 
     * @param tax_address_request the entity to save
     * @return the persisted entity
     */
    public Tax_address_request save(Tax_address_request tax_address_request) {
        log.debug("Request to save Tax_address_request : {}", tax_address_request);
        Tax_address_request result = tax_address_requestRepository.save(tax_address_request);
        return result;
    }

    /**
     *  Get all the tax_address_requests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tax_address_request> findAll(Pageable pageable) {
        log.debug("Request to get all Tax_address_requests");
        Page<Tax_address_request> result = tax_address_requestRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one tax_address_request by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tax_address_request findOne(Long id) {
        log.debug("Request to get Tax_address_request : {}", id);
        Tax_address_request tax_address_request = tax_address_requestRepository.findOne(id);
        return tax_address_request;
    }

    /**
     *  Delete the  tax_address_request by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_address_request : {}", id);
        tax_address_requestRepository.delete(id);
    }
}
