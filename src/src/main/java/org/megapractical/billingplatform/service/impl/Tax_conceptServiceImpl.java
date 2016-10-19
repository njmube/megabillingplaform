package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_conceptService;
import org.megapractical.billingplatform.domain.Tax_concept;
import org.megapractical.billingplatform.repository.Tax_conceptRepository;
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
 * Service Implementation for managing Tax_concept.
 */
@Service
@Transactional
public class Tax_conceptServiceImpl implements Tax_conceptService{

    private final Logger log = LoggerFactory.getLogger(Tax_conceptServiceImpl.class);

    @Inject
    private Tax_conceptRepository tax_conceptRepository;

    /**
     * Save a tax_concept.
     *
     * @param tax_concept the entity to save
     * @return the persisted entity
     */
    public Tax_concept save(Tax_concept tax_concept) {
        log.debug("Request to save Tax_concept : {}", tax_concept);
        Tax_concept result = tax_conceptRepository.save(tax_concept);
        return result;
    }

    /**
     *  Get all the tax_concepts.
     *
     *  @param pageable the pagination information
     *  @param taxpayeraccount
     * @param tax_type
     *@param rate
     * @param concept @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tax_concept> findAll(Pageable pageable, Integer taxpayeraccount, String tax_type, BigDecimal rate, String concept) {
        log.debug("Request to get all Tax_concepts");

        if(taxpayeraccount == 0) {
            List<Tax_concept> emptyList = new ArrayList<>();
            Page<Tax_concept> emptyPage = new PageImpl<Tax_concept>(emptyList, pageable, 0);
            return emptyPage;
        }

        Page<Tax_concept> result = tax_conceptRepository.findAll(pageable);

        if(taxpayeraccount != 0) {
            List<Tax_concept> list = new ArrayList<>();
            Long taxpayer_account_id = new Long(taxpayeraccount.toString());
            BigDecimal noRate = new BigDecimal("-1");

            for (Tax_concept tax_concept : result.getContent()) {
                boolean from_taxpayyer_account = true;
                boolean from_tax_type = true;
                boolean from_rate = true;
                boolean from_concept = true;

                if (tax_concept.getTaxpayer_concept().getTaxpayer_account().getId().compareTo(taxpayer_account_id) != 0) {
                    from_taxpayyer_account = false;
                }

                if(tax_type.compareTo(" ") != 0 && tax_concept.getTax_types().getName().compareTo(tax_type) != 0){
                    from_tax_type = false;
                }

                if(rate.compareTo(noRate) != 0 && tax_concept.getRate().compareTo(rate) != 0){
                    from_rate = false;
                }

                if(concept.compareTo(" ") != 0 && !tax_concept.getTaxpayer_concept().getDescription().toLowerCase().contains(concept.toLowerCase())){
                    from_concept = false;
                }

                if(from_taxpayyer_account && from_tax_type && from_rate && from_concept){
                    list.add(tax_concept);
                }
            }
            Page<Tax_concept> page = new PageImpl<Tax_concept>(list, pageable, result.getTotalElements());
            return page;
        }
        else {
            return result;
        }
    }

    /**
     *  Get one tax_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tax_concept findOne(Long id) {
        log.debug("Request to get Tax_concept : {}", id);
        Tax_concept tax_concept = tax_conceptRepository.findOne(id);
        return tax_concept;
    }

    /**
     *  Delete the  tax_concept by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_concept : {}", id);
        tax_conceptRepository.delete(id);
    }
}
