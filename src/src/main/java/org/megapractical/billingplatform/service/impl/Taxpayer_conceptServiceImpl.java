package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.service.Taxpayer_conceptService;
import org.megapractical.billingplatform.repository.Taxpayer_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing Taxpayer_concept.
 */
@Service
@Transactional
public class Taxpayer_conceptServiceImpl implements Taxpayer_conceptService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_conceptServiceImpl.class);

    @Inject
    private Taxpayer_conceptRepository taxpayer_conceptRepository;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    /**
     * Save a taxpayer_concept.
     *
     * @param taxpayer_concept the entity to save
     * @return the persisted entity
     */
    public Taxpayer_concept save(Taxpayer_concept taxpayer_concept) {
        log.debug("Request to save Taxpayer_concept : {}", taxpayer_concept);
        Taxpayer_concept result = taxpayer_conceptRepository.save(taxpayer_concept);
        return result;
    }

    /**
     *  Get all the taxpayer_concepts.
     *
     *  @param pageable the pagination information
     *  @param taxpayeraccount
     *@param no_identification
     * @param description
     * @param measure_unit
     * @param unit_value @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_concept> findAll(Pageable pageable, Integer taxpayeraccount, String no_identification, String description, String measure_unit, BigDecimal unit_value) {
        log.debug("Request to get all Taxpayer_concepts");

        if(taxpayeraccount == 0){
            List<Taxpayer_concept> emptyList = new ArrayList<>();
            Page<Taxpayer_concept> emptyPage = new PageImpl<Taxpayer_concept>(emptyList, pageable, 0);
            return emptyPage;
        }

        Page<Taxpayer_concept> result = taxpayer_conceptRepository.findAll(pageable);

        if(taxpayeraccount != 0) {
            List<Taxpayer_concept> list = new ArrayList<>();
            Long id = new Long(taxpayeraccount.toString());
            Taxpayer_account taxpayer_account = taxpayer_accountService.findOne(id);
            BigDecimal no_unit_value = new BigDecimal("-1");

            for (Taxpayer_concept taxpayer_concept : result.getContent()) {
                boolean from_taxpayyer_account = true;
                boolean from_no_identification = true;
                boolean from_description = true;
                boolean from_measure_unit = true;
                boolean from_unit_value = true;

                if (taxpayer_concept.getTaxpayer_account().getId().compareTo(taxpayer_account.getId()) != 0) {
                    from_taxpayyer_account = false;
                }

                if(no_identification.compareTo(" ") != 0 && taxpayer_concept.getNo_identification().compareTo(no_identification) != 0){
                    from_no_identification = false;
                }

                if(description.compareTo(" ") != 0 && !taxpayer_concept.getDescription().toLowerCase().contains(description.toLowerCase())){
                    from_description = false;
                }

                if(measure_unit.compareTo(" ") != 0){
                    Set<Measure_unit_concept> measure_unit_concepts = taxpayer_concept.getMeasure_unit_concepts();
                    boolean found_match = false;
                    for (Measure_unit_concept measure_unit_concept: measure_unit_concepts){
                        if(measure_unit_concept.getMeasure_unit().getName().compareTo(measure_unit) == 0){
                            found_match = true;
                            break;
                        }
                    }

                    from_measure_unit = found_match;
                }

                if(unit_value.compareTo(no_unit_value) != 0){
                    Set<Price_concept> price_concepts = taxpayer_concept.getPrice_concepts();
                    boolean found_match = false;
                    for (Price_concept price_concept: price_concepts){
                        if(price_concept.getValue().compareTo(unit_value) == 0){
                            found_match = true;
                            break;
                        }
                    }
                    from_unit_value = found_match;
                }

                if(from_taxpayyer_account && from_no_identification && from_description && from_measure_unit && from_unit_value){
                   list.add(taxpayer_concept);
                }
            }

            Page<Taxpayer_concept> page = new PageImpl<Taxpayer_concept>(list, pageable, result.getTotalElements());
            return page;
        }
        else {
            return result;
        }
    }

    /**
     *  Get one taxpayer_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Taxpayer_concept findOne(Long id) {
        log.debug("Request to get Taxpayer_concept : {}", id);
        Taxpayer_concept taxpayer_concept = taxpayer_conceptRepository.findOne(id);
        return taxpayer_concept;
    }

    /**
     *  Delete the  taxpayer_concept by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_concept : {}", id);
        taxpayer_conceptRepository.delete(id);
    }
}
