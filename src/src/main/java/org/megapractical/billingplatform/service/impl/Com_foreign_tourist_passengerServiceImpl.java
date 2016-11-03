package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_foreign_tourist_passengerService;
import org.megapractical.billingplatform.domain.Com_foreign_tourist_passenger;
import org.megapractical.billingplatform.repository.Com_foreign_tourist_passengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_foreign_tourist_passenger.
 */
@Service
@Transactional
public class Com_foreign_tourist_passengerServiceImpl implements Com_foreign_tourist_passengerService{

    private final Logger log = LoggerFactory.getLogger(Com_foreign_tourist_passengerServiceImpl.class);
    
    @Inject
    private Com_foreign_tourist_passengerRepository com_foreign_tourist_passengerRepository;
    
    /**
     * Save a com_foreign_tourist_passenger.
     * 
     * @param com_foreign_tourist_passenger the entity to save
     * @return the persisted entity
     */
    public Com_foreign_tourist_passenger save(Com_foreign_tourist_passenger com_foreign_tourist_passenger) {
        log.debug("Request to save Com_foreign_tourist_passenger : {}", com_foreign_tourist_passenger);
        Com_foreign_tourist_passenger result = com_foreign_tourist_passengerRepository.save(com_foreign_tourist_passenger);
        return result;
    }

    /**
     *  Get all the com_foreign_tourist_passengers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_foreign_tourist_passenger> findAll(Pageable pageable) {
        log.debug("Request to get all Com_foreign_tourist_passengers");
        Page<Com_foreign_tourist_passenger> result = com_foreign_tourist_passengerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_foreign_tourist_passenger by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_foreign_tourist_passenger findOne(Long id) {
        log.debug("Request to get Com_foreign_tourist_passenger : {}", id);
        Com_foreign_tourist_passenger com_foreign_tourist_passenger = com_foreign_tourist_passengerRepository.findOne(id);
        return com_foreign_tourist_passenger;
    }

    /**
     *  Delete the  com_foreign_tourist_passenger by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_foreign_tourist_passenger : {}", id);
        com_foreign_tourist_passengerRepository.delete(id);
    }
}
