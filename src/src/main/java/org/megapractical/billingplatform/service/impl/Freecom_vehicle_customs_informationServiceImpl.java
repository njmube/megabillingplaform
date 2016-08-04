package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_vehicle_customs_informationService;
import org.megapractical.billingplatform.domain.Freecom_vehicle_customs_information;
import org.megapractical.billingplatform.repository.Freecom_vehicle_customs_informationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_vehicle_customs_information.
 */
@Service
@Transactional
public class Freecom_vehicle_customs_informationServiceImpl implements Freecom_vehicle_customs_informationService{

    private final Logger log = LoggerFactory.getLogger(Freecom_vehicle_customs_informationServiceImpl.class);
    
    @Inject
    private Freecom_vehicle_customs_informationRepository freecom_vehicle_customs_informationRepository;
    
    /**
     * Save a freecom_vehicle_customs_information.
     * 
     * @param freecom_vehicle_customs_information the entity to save
     * @return the persisted entity
     */
    public Freecom_vehicle_customs_information save(Freecom_vehicle_customs_information freecom_vehicle_customs_information) {
        log.debug("Request to save Freecom_vehicle_customs_information : {}", freecom_vehicle_customs_information);
        Freecom_vehicle_customs_information result = freecom_vehicle_customs_informationRepository.save(freecom_vehicle_customs_information);
        return result;
    }

    /**
     *  Get all the freecom_vehicle_customs_informations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_vehicle_customs_information> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_vehicle_customs_informations");
        Page<Freecom_vehicle_customs_information> result = freecom_vehicle_customs_informationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_vehicle_customs_information by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_vehicle_customs_information findOne(Long id) {
        log.debug("Request to get Freecom_vehicle_customs_information : {}", id);
        Freecom_vehicle_customs_information freecom_vehicle_customs_information = freecom_vehicle_customs_informationRepository.findOne(id);
        return freecom_vehicle_customs_information;
    }

    /**
     *  Delete the  freecom_vehicle_customs_information by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_vehicle_customs_information : {}", id);
        freecom_vehicle_customs_informationRepository.delete(id);
    }
}
