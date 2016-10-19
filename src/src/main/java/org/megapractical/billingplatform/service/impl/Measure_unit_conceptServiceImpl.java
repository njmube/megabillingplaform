package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Measure_unit_conceptService;
import org.megapractical.billingplatform.domain.Measure_unit_concept;
import org.megapractical.billingplatform.repository.Measure_unit_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Measure_unit_concept.
 */
@Service
@Transactional
public class Measure_unit_conceptServiceImpl implements Measure_unit_conceptService{

    private final Logger log = LoggerFactory.getLogger(Measure_unit_conceptServiceImpl.class);

    @Inject
    private Measure_unit_conceptRepository measure_unit_conceptRepository;

    /**
     * Save a measure_unit_concept.
     *
     * @param measure_unit_concept the entity to save
     * @return the persisted entity
     */
    public Measure_unit_concept save(Measure_unit_concept measure_unit_concept) {
        log.debug("Request to save Measure_unit_concept : {}", measure_unit_concept);
        Measure_unit_concept result = measure_unit_conceptRepository.save(measure_unit_concept);
        return result;
    }

    /**
     *  Get all the measure_unit_concepts.
     *
     *  @return the list of entities
     * @param taxpayerconcept
     */
    @Transactional(readOnly = true)
    public List<Measure_unit_concept> findAll(Long taxpayerconcept) {
        log.debug("Request to get all Measure_unit_concepts");
        List<Measure_unit_concept> result = measure_unit_conceptRepository.findAll();
        if(taxpayerconcept != 0){
            List<Measure_unit_concept> filtered_result = new ArrayList<>();
            for (Measure_unit_concept measure_unit_concept: result){
                if(measure_unit_concept.getTaxpayer_concept().getId().compareTo(taxpayerconcept) == 0){
                    filtered_result.add(measure_unit_concept);
                }
            }
            return filtered_result;
        }
        else {
            return result;
        }
    }

    /**
     *  Get one measure_unit_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Measure_unit_concept findOne(Long id) {
        log.debug("Request to get Measure_unit_concept : {}", id);
        Measure_unit_concept measure_unit_concept = measure_unit_conceptRepository.findOne(id);
        return measure_unit_concept;
    }

    /**
     *  Delete the  measure_unit_concept by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Measure_unit_concept : {}", id);
        measure_unit_conceptRepository.delete(id);
    }
}
