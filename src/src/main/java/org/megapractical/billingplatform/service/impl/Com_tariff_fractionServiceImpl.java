package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_tariff_fractionService;
import org.megapractical.billingplatform.domain.Com_tariff_fraction;
import org.megapractical.billingplatform.repository.Com_tariff_fractionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_tariff_fraction.
 */
@Service
@Transactional
public class Com_tariff_fractionServiceImpl implements Com_tariff_fractionService{

    private final Logger log = LoggerFactory.getLogger(Com_tariff_fractionServiceImpl.class);

    @Inject
    private Com_tariff_fractionRepository com_tariff_fractionRepository;

    /**
     * Save a com_tariff_fraction.
     *
     * @param com_tariff_fraction the entity to save
     * @return the persisted entity
     */
    public Com_tariff_fraction save(Com_tariff_fraction com_tariff_fraction) {
        log.debug("Request to save Com_tariff_fraction : {}", com_tariff_fraction);
        Com_tariff_fraction result = com_tariff_fractionRepository.save(com_tariff_fraction);
        return result;
    }

    /**
     *  Get all the com_tariff_fractions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Com_tariff_fraction> findAll(Pageable pageable) {
        log.debug("Request to get all Com_tariff_fractions");
        Page<Com_tariff_fraction> result = com_tariff_fractionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one com_tariff_fraction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Com_tariff_fraction findOne(Long id) {
        log.debug("Request to get Com_tariff_fraction : {}", id);
        Com_tariff_fraction com_tariff_fraction = com_tariff_fractionRepository.findOne(id);
        return com_tariff_fraction;
    }

    /**
     *  Delete the  com_tariff_fraction by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_tariff_fraction : {}", id);
        com_tariff_fractionRepository.delete(id);
    }

    @Override
    public List<Com_tariff_fraction> findAll() {
        log.debug("Request to get all Com_tariff_fractions");
        List<Com_tariff_fraction> result = com_tariff_fractionRepository.findAll();
        return result;
    }
}
