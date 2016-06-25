package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_countryService;
import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.repository.C_countryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_country.
 */
@Service
@Transactional
public class C_countryServiceImpl implements C_countryService{

    private final Logger log = LoggerFactory.getLogger(C_countryServiceImpl.class);

    @Inject
    private C_countryRepository c_countryRepository;

    /**
     * Save a c_country.
     *
     * @param c_country the entity to save
     * @return the persisted entity
     */
    public C_country save(C_country c_country) {
        log.debug("Request to save C_country : {}", c_country);
        C_country result = c_countryRepository.save(c_country);
        return result;
    }

    /**
     *  Get all the c_countries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_country> findAll(Pageable pageable) {
        log.debug("Request to get all C_countries");
        Page<C_country> result = c_countryRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public List<C_country> findAll() {
        log.debug("Request to get all C_countries");
        List<C_country> result = c_countryRepository.findAll();
        return result;
    }

    /**
     *  Get one c_country by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_country findOne(Long id) {
        log.debug("Request to get C_country : {}", id);
        C_country c_country = c_countryRepository.findOne(id);
        return c_country;
    }

    /**
     *  Delete the  c_country by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_country : {}", id);
        c_countryRepository.delete(id);
    }
}
