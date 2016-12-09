package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_data_enajenanteService;
import org.megapractical.billingplatform.domain.Com_data_enajenante;
import org.megapractical.billingplatform.repository.Com_data_enajenanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_data_enajenante.
 */
@Service
@Transactional
public class Com_data_enajenanteServiceImpl implements Com_data_enajenanteService{

    private final Logger log = LoggerFactory.getLogger(Com_data_enajenanteServiceImpl.class);
    
    @Inject
    private Com_data_enajenanteRepository com_data_enajenanteRepository;
    
    /**
     * Save a com_data_enajenante.
     * 
     * @param com_data_enajenante the entity to save
     * @return the persisted entity
     */
    public Com_data_enajenante save(Com_data_enajenante com_data_enajenante) {
        log.debug("Request to save Com_data_enajenante : {}", com_data_enajenante);
        Com_data_enajenante result = com_data_enajenanteRepository.save(com_data_enajenante);
        return result;
    }

    /**
     *  Get all the com_data_enajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_data_enajenante> findAll(Pageable pageable) {
        log.debug("Request to get all Com_data_enajenantes");
        Page<Com_data_enajenante> result = com_data_enajenanteRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_data_enajenante by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_data_enajenante findOne(Long id) {
        log.debug("Request to get Com_data_enajenante : {}", id);
        Com_data_enajenante com_data_enajenante = com_data_enajenanteRepository.findOne(id);
        return com_data_enajenante;
    }

    /**
     *  Delete the  com_data_enajenante by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_data_enajenante : {}", id);
        com_data_enajenanteRepository.delete(id);
    }
}
