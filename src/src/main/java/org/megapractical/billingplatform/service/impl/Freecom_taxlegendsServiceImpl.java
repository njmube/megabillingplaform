package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_taxlegendsService;
import org.megapractical.billingplatform.domain.Freecom_taxlegends;
import org.megapractical.billingplatform.repository.Freecom_taxlegendsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_taxlegends.
 */
@Service
@Transactional
public class Freecom_taxlegendsServiceImpl implements Freecom_taxlegendsService{

    private final Logger log = LoggerFactory.getLogger(Freecom_taxlegendsServiceImpl.class);
    
    @Inject
    private Freecom_taxlegendsRepository freecom_taxlegendsRepository;
    
    /**
     * Save a freecom_taxlegends.
     * 
     * @param freecom_taxlegends the entity to save
     * @return the persisted entity
     */
    public Freecom_taxlegends save(Freecom_taxlegends freecom_taxlegends) {
        log.debug("Request to save Freecom_taxlegends : {}", freecom_taxlegends);
        Freecom_taxlegends result = freecom_taxlegendsRepository.save(freecom_taxlegends);
        return result;
    }

    /**
     *  Get all the freecom_taxlegends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_taxlegends> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_taxlegends");
        Page<Freecom_taxlegends> result = freecom_taxlegendsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_taxlegends by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_taxlegends findOne(Long id) {
        log.debug("Request to get Freecom_taxlegends : {}", id);
        Freecom_taxlegends freecom_taxlegends = freecom_taxlegendsRepository.findOne(id);
        return freecom_taxlegends;
    }

    /**
     *  Delete the  freecom_taxlegends by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_taxlegends : {}", id);
        freecom_taxlegendsRepository.delete(id);
    }
}
