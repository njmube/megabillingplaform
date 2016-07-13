package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_tarService;
import org.megapractical.billingplatform.domain.C_tar;
import org.megapractical.billingplatform.repository.C_tarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_tar.
 */
@Service
@Transactional
public class C_tarServiceImpl implements C_tarService{

    private final Logger log = LoggerFactory.getLogger(C_tarServiceImpl.class);
    
    @Inject
    private C_tarRepository c_tarRepository;
    
    /**
     * Save a c_tar.
     * 
     * @param c_tar the entity to save
     * @return the persisted entity
     */
    public C_tar save(C_tar c_tar) {
        log.debug("Request to save C_tar : {}", c_tar);
        C_tar result = c_tarRepository.save(c_tar);
        return result;
    }

    /**
     *  Get all the c_tars.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_tar> findAll(Pageable pageable) {
        log.debug("Request to get all C_tars");
        Page<C_tar> result = c_tarRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_tar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_tar findOne(Long id) {
        log.debug("Request to get C_tar : {}", id);
        C_tar c_tar = c_tarRepository.findOne(id);
        return c_tar;
    }

    /**
     *  Delete the  c_tar by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_tar : {}", id);
        c_tarRepository.delete(id);
    }
}
