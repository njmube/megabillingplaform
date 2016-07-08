package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_typesService;
import org.megapractical.billingplatform.domain.Tax_types;
import org.megapractical.billingplatform.repository.Tax_typesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tax_types.
 */
@Service
@Transactional
public class Tax_typesServiceImpl implements Tax_typesService{

    private final Logger log = LoggerFactory.getLogger(Tax_typesServiceImpl.class);

    @Inject
    private Tax_typesRepository tax_typesRepository;

    /**
     * Save a tax_types.
     *
     * @param tax_types the entity to save
     * @return the persisted entity
     */
    public Tax_types save(Tax_types tax_types) {
        log.debug("Request to save Tax_types : {}", tax_types);
        Tax_types result = tax_typesRepository.save(tax_types);
        return result;
    }

    /**
     *  Get all the tax_types.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tax_types> findAll(Pageable pageable) {
        log.debug("Request to get all Tax_types");
        Page<Tax_types> result = tax_typesRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Tax_types> findAllByName(String filtername, Pageable pageable) {
        log.debug("Request to get Request_states whith filtername: {}",filtername);
        Page<Tax_types> result = tax_typesRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }

    /**
     *  Get one tax_types by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tax_types findOne(Long id) {
        log.debug("Request to get Tax_types : {}", id);
        Tax_types tax_types = tax_typesRepository.findOne(id);
        return tax_types;
    }

    /**
     *  Delete the  tax_types by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_types : {}", id);
        tax_typesRepository.delete(id);
    }
}
