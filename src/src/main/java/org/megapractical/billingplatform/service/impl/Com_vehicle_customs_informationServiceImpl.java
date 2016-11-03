package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_vehicle_customs_informationService;
import org.megapractical.billingplatform.domain.Com_vehicle_customs_information;
import org.megapractical.billingplatform.repository.Com_vehicle_customs_informationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_vehicle_customs_information.
 */
@Service
@Transactional
public class Com_vehicle_customs_informationServiceImpl implements Com_vehicle_customs_informationService{

    private final Logger log = LoggerFactory.getLogger(Com_vehicle_customs_informationServiceImpl.class);
    
    @Inject
    private Com_vehicle_customs_informationRepository com_vehicle_customs_informationRepository;
    
    /**
     * Save a com_vehicle_customs_information.
     * 
     * @param com_vehicle_customs_information the entity to save
     * @return the persisted entity
     */
    public Com_vehicle_customs_information save(Com_vehicle_customs_information com_vehicle_customs_information) {
        log.debug("Request to save Com_vehicle_customs_information : {}", com_vehicle_customs_information);
        Com_vehicle_customs_information result = com_vehicle_customs_informationRepository.save(com_vehicle_customs_information);
        return result;
    }

    /**
     *  Get all the com_vehicle_customs_informations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_vehicle_customs_information> findAll(Pageable pageable) {
        log.debug("Request to get all Com_vehicle_customs_informations");
        Page<Com_vehicle_customs_information> result = com_vehicle_customs_informationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_vehicle_customs_information by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_vehicle_customs_information findOne(Long id) {
        log.debug("Request to get Com_vehicle_customs_information : {}", id);
        Com_vehicle_customs_information com_vehicle_customs_information = com_vehicle_customs_informationRepository.findOne(id);
        return com_vehicle_customs_information;
    }

    /**
     *  Delete the  com_vehicle_customs_information by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_vehicle_customs_information : {}", id);
        com_vehicle_customs_informationRepository.delete(id);
    }
}
