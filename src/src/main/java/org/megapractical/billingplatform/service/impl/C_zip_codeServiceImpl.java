package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_zip_codeService;
import org.megapractical.billingplatform.domain.C_zip_code;
import org.megapractical.billingplatform.repository.C_zip_codeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_zip_code.
 */
@Service
@Transactional
public class C_zip_codeServiceImpl implements C_zip_codeService{

    private final Logger log = LoggerFactory.getLogger(C_zip_codeServiceImpl.class);
    
    @Inject
    private C_zip_codeRepository c_zip_codeRepository;
    
    /**
     * Save a c_zip_code.
     * 
     * @param c_zip_code the entity to save
     * @return the persisted entity
     */
    public C_zip_code save(C_zip_code c_zip_code) {
        log.debug("Request to save C_zip_code : {}", c_zip_code);
        C_zip_code result = c_zip_codeRepository.save(c_zip_code);
        return result;
    }

    /**
     *  Get all the c_zip_codes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_zip_code> findAll(Pageable pageable) {
        log.debug("Request to get all C_zip_codes");
        Page<C_zip_code> result = c_zip_codeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_zip_code by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_zip_code findOne(Long id) {
        log.debug("Request to get C_zip_code : {}", id);
        C_zip_code c_zip_code = c_zip_codeRepository.findOne(id);
        return c_zip_code;
    }

    /**
     *  Delete the  c_zip_code by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_zip_code : {}", id);
        c_zip_codeRepository.delete(id);
    }
}
