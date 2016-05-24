package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_regimeService;
import org.megapractical.billingplatform.domain.Tax_regime;
import org.megapractical.billingplatform.repository.Tax_regimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tax_regime.
 */
@Service
@Transactional
public class Tax_regimeServiceImpl implements Tax_regimeService{

    private final Logger log = LoggerFactory.getLogger(Tax_regimeServiceImpl.class);
    
    @Inject
    private Tax_regimeRepository tax_regimeRepository;
    
    /**
     * Save a tax_regime.
     * 
     * @param tax_regime the entity to save
     * @return the persisted entity
     */
    public Tax_regime save(Tax_regime tax_regime) {
        log.debug("Request to save Tax_regime : {}", tax_regime);
        Tax_regime result = tax_regimeRepository.save(tax_regime);
        return result;
    }

    /**
     *  Get all the tax_regimes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tax_regime> findAll(Pageable pageable) {
        log.debug("Request to get all Tax_regimes");
        Page<Tax_regime> result = tax_regimeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one tax_regime by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tax_regime findOne(Long id) {
        log.debug("Request to get Tax_regime : {}", id);
        Tax_regime tax_regime = tax_regimeRepository.findOne(id);
        return tax_regime;
    }

    /**
     *  Delete the  tax_regime by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_regime : {}", id);
        tax_regimeRepository.delete(id);
    }
}
