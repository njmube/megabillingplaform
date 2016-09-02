package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_addressService;
import org.megapractical.billingplatform.domain.Tax_address;
import org.megapractical.billingplatform.repository.Tax_addressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tax_address.
 */
@Service
@Transactional
public class Tax_addressServiceImpl implements Tax_addressService{

    private final Logger log = LoggerFactory.getLogger(Tax_addressServiceImpl.class);
    
    @Inject
    private Tax_addressRepository tax_addressRepository;
    
    /**
     * Save a tax_address.
     * 
     * @param tax_address the entity to save
     * @return the persisted entity
     */
    public Tax_address save(Tax_address tax_address) {
        log.debug("Request to save Tax_address : {}", tax_address);
        Tax_address result = tax_addressRepository.save(tax_address);
        return result;
    }

    /**
     *  Get all the tax_addresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tax_address> findAll(Pageable pageable) {
        log.debug("Request to get all Tax_addresses");
        Page<Tax_address> result = tax_addressRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one tax_address by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tax_address findOne(Long id) {
        log.debug("Request to get Tax_address : {}", id);
        Tax_address tax_address = tax_addressRepository.findOne(id);
        return tax_address;
    }

    /**
     *  Delete the  tax_address by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_address : {}", id);
        tax_addressRepository.delete(id);
    }
}
