package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_data_enajenanteService;
import org.megapractical.billingplatform.domain.Freecom_data_enajenante;
import org.megapractical.billingplatform.repository.Freecom_data_enajenanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_data_enajenante.
 */
@Service
@Transactional
public class Freecom_data_enajenanteServiceImpl implements Freecom_data_enajenanteService{

    private final Logger log = LoggerFactory.getLogger(Freecom_data_enajenanteServiceImpl.class);
    
    @Inject
    private Freecom_data_enajenanteRepository freecom_data_enajenanteRepository;
    
    /**
     * Save a freecom_data_enajenante.
     * 
     * @param freecom_data_enajenante the entity to save
     * @return the persisted entity
     */
    public Freecom_data_enajenante save(Freecom_data_enajenante freecom_data_enajenante) {
        log.debug("Request to save Freecom_data_enajenante : {}", freecom_data_enajenante);
        Freecom_data_enajenante result = freecom_data_enajenanteRepository.save(freecom_data_enajenante);
        return result;
    }

    /**
     *  Get all the freecom_data_enajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_data_enajenante> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_data_enajenantes");
        Page<Freecom_data_enajenante> result = freecom_data_enajenanteRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_data_enajenante by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_data_enajenante findOne(Long id) {
        log.debug("Request to get Freecom_data_enajenante : {}", id);
        Freecom_data_enajenante freecom_data_enajenante = freecom_data_enajenanteRepository.findOne(id);
        return freecom_data_enajenante;
    }

    /**
     *  Delete the  freecom_data_enajenante by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_data_enajenante : {}", id);
        freecom_data_enajenanteRepository.delete(id);
    }
}
