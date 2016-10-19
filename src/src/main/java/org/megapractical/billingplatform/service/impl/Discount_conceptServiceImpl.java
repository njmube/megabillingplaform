package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Discount_conceptService;
import org.megapractical.billingplatform.domain.Discount_concept;
import org.megapractical.billingplatform.repository.Discount_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Discount_concept.
 */
@Service
@Transactional
public class Discount_conceptServiceImpl implements Discount_conceptService{

    private final Logger log = LoggerFactory.getLogger(Discount_conceptServiceImpl.class);

    @Inject
    private Discount_conceptRepository discount_conceptRepository;

    /**
     * Save a discount_concept.
     *
     * @param discount_concept the entity to save
     * @return the persisted entity
     */
    public Discount_concept save(Discount_concept discount_concept) {
        log.debug("Request to save Discount_concept : {}", discount_concept);
        Discount_concept result = discount_conceptRepository.save(discount_concept);
        return result;
    }

    /**
     *  Get all the discount_concepts.
     *
     *  @return the list of entities
     * @param taxpayerconcept
     */
    @Transactional(readOnly = true)
    public List<Discount_concept> findAll(Long taxpayerconcept) {
        log.debug("Request to get all Discount_concepts");
        List<Discount_concept> result = discount_conceptRepository.findAll();
        if(taxpayerconcept != 0){
            List<Discount_concept> filtered_result = new ArrayList<>();
            for (Discount_concept discount_concept: result){
                if(discount_concept.getTaxpayer_concept().getId().compareTo(taxpayerconcept) == 0){
                    filtered_result.add(discount_concept);
                }
            }
            return filtered_result;
        }
        else {
            return result;
        }
    }

    /**
     *  Get one discount_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Discount_concept findOne(Long id) {
        log.debug("Request to get Discount_concept : {}", id);
        Discount_concept discount_concept = discount_conceptRepository.findOne(id);
        return discount_concept;
    }

    /**
     *  Delete the  discount_concept by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Discount_concept : {}", id);
        discount_conceptRepository.delete(id);
    }
}
