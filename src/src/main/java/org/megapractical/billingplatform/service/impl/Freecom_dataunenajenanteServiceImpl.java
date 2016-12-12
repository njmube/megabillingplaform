package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_dataunenajenanteService;
import org.megapractical.billingplatform.domain.Freecom_dataunenajenante;
import org.megapractical.billingplatform.repository.Freecom_dataunenajenanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_dataunenajenante.
 */
@Service
@Transactional
public class Freecom_dataunenajenanteServiceImpl implements Freecom_dataunenajenanteService{

    private final Logger log = LoggerFactory.getLogger(Freecom_dataunenajenanteServiceImpl.class);
    
    @Inject
    private Freecom_dataunenajenanteRepository freecom_dataunenajenanteRepository;
    
    /**
     * Save a freecom_dataunenajenante.
     * 
     * @param freecom_dataunenajenante the entity to save
     * @return the persisted entity
     */
    public Freecom_dataunenajenante save(Freecom_dataunenajenante freecom_dataunenajenante) {
        log.debug("Request to save Freecom_dataunenajenante : {}", freecom_dataunenajenante);
        Freecom_dataunenajenante result = freecom_dataunenajenanteRepository.save(freecom_dataunenajenante);
        return result;
    }

    /**
     *  Get all the freecom_dataunenajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_dataunenajenante> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_dataunenajenantes");
        Page<Freecom_dataunenajenante> result = freecom_dataunenajenanteRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_dataunenajenante by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_dataunenajenante findOne(Long id) {
        log.debug("Request to get Freecom_dataunenajenante : {}", id);
        Freecom_dataunenajenante freecom_dataunenajenante = freecom_dataunenajenanteRepository.findOne(id);
        return freecom_dataunenajenante;
    }

    /**
     *  Delete the  freecom_dataunenajenante by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_dataunenajenante : {}", id);
        freecom_dataunenajenanteRepository.delete(id);
    }
}
