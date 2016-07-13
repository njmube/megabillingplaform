package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.ScopeService;
import org.megapractical.billingplatform.domain.Scope;
import org.megapractical.billingplatform.repository.ScopeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Scope.
 */
@Service
@Transactional
public class ScopeServiceImpl implements ScopeService{

    private final Logger log = LoggerFactory.getLogger(ScopeServiceImpl.class);
    
    @Inject
    private ScopeRepository scopeRepository;
    
    /**
     * Save a scope.
     * 
     * @param scope the entity to save
     * @return the persisted entity
     */
    public Scope save(Scope scope) {
        log.debug("Request to save Scope : {}", scope);
        Scope result = scopeRepository.save(scope);
        return result;
    }

    /**
     *  Get all the scopes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Scope> findAll(Pageable pageable) {
        log.debug("Request to get all Scopes");
        Page<Scope> result = scopeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one scope by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Scope findOne(Long id) {
        log.debug("Request to get Scope : {}", id);
        Scope scope = scopeRepository.findOne(id);
        return scope;
    }

    /**
     *  Delete the  scope by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Scope : {}", id);
        scopeRepository.delete(id);
    }
}
