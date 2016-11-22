package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_tariff_fractionService;
import org.megapractical.billingplatform.domain.Freecom_tariff_fraction;
import org.megapractical.billingplatform.repository.Freecom_tariff_fractionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_tariff_fraction.
 */
@Service
@Transactional
public class Freecom_tariff_fractionServiceImpl implements Freecom_tariff_fractionService{

    private final Logger log = LoggerFactory.getLogger(Freecom_tariff_fractionServiceImpl.class);

    @Inject
    private Freecom_tariff_fractionRepository freecom_tariff_fractionRepository;

    /**
     * Save a freecom_tariff_fraction.
     *
     * @param freecom_tariff_fraction the entity to save
     * @return the persisted entity
     */
    public Freecom_tariff_fraction save(Freecom_tariff_fraction freecom_tariff_fraction) {
        log.debug("Request to save Freecom_tariff_fraction : {}", freecom_tariff_fraction);
        Freecom_tariff_fraction result = freecom_tariff_fractionRepository.save(freecom_tariff_fraction);
        return result;
    }

    /**
     *  Get all the freecom_tariff_fractions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Freecom_tariff_fraction> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_tariff_fractions");
        Page<Freecom_tariff_fraction> result = freecom_tariff_fractionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one freecom_tariff_fraction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Freecom_tariff_fraction findOne(Long id) {
        log.debug("Request to get Freecom_tariff_fraction : {}", id);
        Freecom_tariff_fraction freecom_tariff_fraction = freecom_tariff_fractionRepository.findOne(id);
        return freecom_tariff_fraction;
    }

    /**
     *  Delete the  freecom_tariff_fraction by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_tariff_fraction : {}", id);
        freecom_tariff_fractionRepository.delete(id);
    }

    @Override
    public List<Freecom_tariff_fraction> findAll() {
        log.debug("Request to get all Freecom_tariff_fractions");
        List<Freecom_tariff_fraction> result = freecom_tariff_fractionRepository.findAll();
        return result;
    }
}
