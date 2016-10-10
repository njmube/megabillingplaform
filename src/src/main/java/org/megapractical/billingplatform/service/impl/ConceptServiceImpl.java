package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.service.ConceptService;
import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.repository.ConceptRepository;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
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

/**
 * Service Implementation for managing Concept.
 */
@Service
@Transactional
public class ConceptServiceImpl implements ConceptService{

    private final Logger log = LoggerFactory.getLogger(ConceptServiceImpl.class);

    @Inject
    private ConceptRepository conceptRepository;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    /**
     * Save a concept.
     *
     * @param concept the entity to save
     * @return the persisted entity
     */
    public Concept save(Concept concept) {
        log.debug("Request to save Concept : {}", concept);
        Concept result = conceptRepository.save(concept);
        return result;
    }

    /**
     *  Get all the concepts.
     *
     *  @param pageable the pagination information
     * @param taxpayeraccount
     * @param no_identification
     * @param description
     * @param measure_unit
     * @param unit_value @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Concept> findAll(Pageable pageable, Integer taxpayeraccount, String no_identification, String description, String measure_unit, BigDecimal unit_value) {
        log.debug("Request to get all Concepts");

        if(taxpayeraccount == 0){
            List<Concept> emptyList = new ArrayList<>();
            Page<Concept> emptyPage = new PageImpl<Concept>(emptyList, pageable, 0);
            return emptyPage;
        }

        Page<Concept> result = conceptRepository.findAll(pageable);

        if(taxpayeraccount != 0) {
            List<Concept> list = new ArrayList<>();
            Long id = new Long(taxpayeraccount.toString());
            Taxpayer_account taxpayer_account = taxpayer_accountService.findOne(id);
            BigDecimal no_unit_value = new BigDecimal("-1");

            for (Concept concept : result.getContent()) {
                boolean from_taxpayyer_account = true;
                boolean from_no_identification = true;
                boolean from_description = true;
                boolean from_measure_unit = true;
                boolean from_unit_value = true;

                if (concept.getTaxpayer_account().getId().compareTo(taxpayer_account.getId()) != 0) {
                    from_taxpayyer_account = false;
                }

                if(no_identification.compareTo(" ") != 0 && concept.getNo_identification().compareTo(no_identification) != 0){
                    from_no_identification = false;
                }

                if(description.compareTo(" ") != 0 && !concept.getDescription().toLowerCase().contains(description.toLowerCase())){
                    from_description = false;
                }

                if(measure_unit.compareTo(" ") != 0 && concept.getMeasure_unit().getName().compareTo(measure_unit) != 0){
                    from_measure_unit = false;
                }

                if(unit_value.compareTo(no_unit_value) != 0 && concept.getUnit_value().compareTo(unit_value) != 0){
                    from_unit_value = false;
                }

                if(from_taxpayyer_account && from_no_identification && from_description && from_measure_unit && from_unit_value){
                    list.add(concept);
                }
            }

            Page<Concept> page = new PageImpl<Concept>(list, pageable, result.getTotalElements());
            return page;
        }
        else {
            return result;
        }
    }

    /**
     *  Get one concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Concept findOne(Long id) {
        log.debug("Request to get Concept : {}", id);
        Concept concept = conceptRepository.findOne(id);
        return concept;
    }

    /**
     *  Delete the  concept by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Concept : {}", id);
        conceptRepository.delete(id);
    }
}
