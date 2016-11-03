package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_taxlegendsService;
import org.megapractical.billingplatform.domain.Com_taxlegends;
import org.megapractical.billingplatform.repository.Com_taxlegendsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_taxlegends.
 */
@Service
@Transactional
public class Com_taxlegendsServiceImpl implements Com_taxlegendsService{

    private final Logger log = LoggerFactory.getLogger(Com_taxlegendsServiceImpl.class);
    
    @Inject
    private Com_taxlegendsRepository com_taxlegendsRepository;
    
    /**
     * Save a com_taxlegends.
     * 
     * @param com_taxlegends the entity to save
     * @return the persisted entity
     */
    public Com_taxlegends save(Com_taxlegends com_taxlegends) {
        log.debug("Request to save Com_taxlegends : {}", com_taxlegends);
        Com_taxlegends result = com_taxlegendsRepository.save(com_taxlegends);
        return result;
    }

    /**
     *  Get all the com_taxlegends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_taxlegends> findAll(Pageable pageable) {
        log.debug("Request to get all Com_taxlegends");
        Page<Com_taxlegends> result = com_taxlegendsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_taxlegends by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_taxlegends findOne(Long id) {
        log.debug("Request to get Com_taxlegends : {}", id);
        Com_taxlegends com_taxlegends = com_taxlegendsRepository.findOne(id);
        return com_taxlegends;
    }

    /**
     *  Delete the  com_taxlegends by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_taxlegends : {}", id);
        com_taxlegendsRepository.delete(id);
    }
}
