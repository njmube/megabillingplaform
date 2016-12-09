package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_dataunenajenanteService;
import org.megapractical.billingplatform.domain.Com_dataunenajenante;
import org.megapractical.billingplatform.repository.Com_dataunenajenanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_dataunenajenante.
 */
@Service
@Transactional
public class Com_dataunenajenanteServiceImpl implements Com_dataunenajenanteService{

    private final Logger log = LoggerFactory.getLogger(Com_dataunenajenanteServiceImpl.class);
    
    @Inject
    private Com_dataunenajenanteRepository com_dataunenajenanteRepository;
    
    /**
     * Save a com_dataunenajenante.
     * 
     * @param com_dataunenajenante the entity to save
     * @return the persisted entity
     */
    public Com_dataunenajenante save(Com_dataunenajenante com_dataunenajenante) {
        log.debug("Request to save Com_dataunenajenante : {}", com_dataunenajenante);
        Com_dataunenajenante result = com_dataunenajenanteRepository.save(com_dataunenajenante);
        return result;
    }

    /**
     *  Get all the com_dataunenajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_dataunenajenante> findAll(Pageable pageable) {
        log.debug("Request to get all Com_dataunenajenantes");
        Page<Com_dataunenajenante> result = com_dataunenajenanteRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_dataunenajenante by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_dataunenajenante findOne(Long id) {
        log.debug("Request to get Com_dataunenajenante : {}", id);
        Com_dataunenajenante com_dataunenajenante = com_dataunenajenanteRepository.findOne(id);
        return com_dataunenajenante;
    }

    /**
     *  Delete the  com_dataunenajenante by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_dataunenajenante : {}", id);
        com_dataunenajenanteRepository.delete(id);
    }
}
