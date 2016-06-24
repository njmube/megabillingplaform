package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_moneyService;
import org.megapractical.billingplatform.domain.C_money;
import org.megapractical.billingplatform.repository.C_moneyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_money.
 */
@Service
@Transactional
public class C_moneyServiceImpl implements C_moneyService{

    private final Logger log = LoggerFactory.getLogger(C_moneyServiceImpl.class);
    
    @Inject
    private C_moneyRepository c_moneyRepository;
    
    /**
     * Save a c_money.
     * 
     * @param c_money the entity to save
     * @return the persisted entity
     */
    public C_money save(C_money c_money) {
        log.debug("Request to save C_money : {}", c_money);
        C_money result = c_moneyRepository.save(c_money);
        return result;
    }

    /**
     *  Get all the c_monies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_money> findAll(Pageable pageable) {
        log.debug("Request to get all C_monies");
        Page<C_money> result = c_moneyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_money by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_money findOne(Long id) {
        log.debug("Request to get C_money : {}", id);
        C_money c_money = c_moneyRepository.findOne(id);
        return c_money;
    }

    /**
     *  Delete the  c_money by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_money : {}", id);
        c_moneyRepository.delete(id);
    }
}
