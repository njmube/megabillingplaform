package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_foreign_tourist_passengerService;
import org.megapractical.billingplatform.domain.Freecom_foreign_tourist_passenger;
import org.megapractical.billingplatform.repository.Freecom_foreign_tourist_passengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_foreign_tourist_passenger.
 */
@Service
@Transactional
public class Freecom_foreign_tourist_passengerServiceImpl implements Freecom_foreign_tourist_passengerService{

    private final Logger log = LoggerFactory.getLogger(Freecom_foreign_tourist_passengerServiceImpl.class);
    
    @Inject
    private Freecom_foreign_tourist_passengerRepository freecom_foreign_tourist_passengerRepository;
    
    /**
     * Save a freecom_foreign_tourist_passenger.
     * 
     * @param freecom_foreign_tourist_passenger the entity to save
     * @return the persisted entity
     */
    public Freecom_foreign_tourist_passenger save(Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger) {
        log.debug("Request to save Freecom_foreign_tourist_passenger : {}", freecom_foreign_tourist_passenger);
        Freecom_foreign_tourist_passenger result = freecom_foreign_tourist_passengerRepository.save(freecom_foreign_tourist_passenger);
        return result;
    }

    /**
     *  Get all the freecom_foreign_tourist_passengers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_foreign_tourist_passenger> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_foreign_tourist_passengers");
        Page<Freecom_foreign_tourist_passenger> result = freecom_foreign_tourist_passengerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_foreign_tourist_passenger by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_foreign_tourist_passenger findOne(Long id) {
        log.debug("Request to get Freecom_foreign_tourist_passenger : {}", id);
        Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger = freecom_foreign_tourist_passengerRepository.findOne(id);
        return freecom_foreign_tourist_passenger;
    }

    /**
     *  Delete the  freecom_foreign_tourist_passenger by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_foreign_tourist_passenger : {}", id);
        freecom_foreign_tourist_passengerRepository.delete(id);
    }
}
