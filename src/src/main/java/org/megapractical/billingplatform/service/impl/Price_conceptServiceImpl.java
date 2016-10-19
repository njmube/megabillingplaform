package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Taxpayer_concept;
import org.megapractical.billingplatform.service.Price_conceptService;
import org.megapractical.billingplatform.domain.Price_concept;
import org.megapractical.billingplatform.repository.Price_conceptRepository;
import org.megapractical.billingplatform.service.Taxpayer_conceptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Price_concept.
 */
@Service
@Transactional
public class Price_conceptServiceImpl implements Price_conceptService{

    private final Logger log = LoggerFactory.getLogger(Price_conceptServiceImpl.class);

    @Inject
    private Price_conceptRepository price_conceptRepository;

    @Inject
    private Taxpayer_conceptService taxpayer_conceptService;

    /**
     * Save a price_concept.
     *
     * @param price_concept the entity to save
     * @return the persisted entity
     */
    public Price_concept save(Price_concept price_concept) {
        log.debug("Request to save Price_concept : {}", price_concept);
        Price_concept result = price_conceptRepository.save(price_concept);
        return result;
    }

    /**
     *  Get all the price_concepts.
     *
     *  @return the list of entities
     * @param taxpayerconcept
     */
    @Transactional(readOnly = true)
    public List<Price_concept> findAll(Long taxpayerconcept) {
        log.debug("Request to get all Price_concepts");
        List<Price_concept> result = price_conceptRepository.findAll();
        if(taxpayerconcept != 0){
            List<Price_concept> filtered_result = new ArrayList<>();
            for (Price_concept price_concept: result){
                if(price_concept.getTaxpayer_concept().getId().compareTo(taxpayerconcept) == 0){
                    filtered_result.add(price_concept);
                }
            }
            return filtered_result;
        }
        else {
            return result;
        }
    }

    /**
     *  Get one price_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Price_concept findOne(Long id) {
        log.debug("Request to get Price_concept : {}", id);
        Price_concept price_concept = price_conceptRepository.findOne(id);
        return price_concept;
    }

    /**
     *  Delete the  price_concept by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Price_concept : {}", id);
        price_conceptRepository.delete(id);
    }
}
