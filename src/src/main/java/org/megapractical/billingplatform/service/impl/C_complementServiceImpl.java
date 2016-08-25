package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_complementService;
import org.megapractical.billingplatform.domain.C_complement;
import org.megapractical.billingplatform.repository.C_complementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_complement.
 */
@Service
@Transactional
public class C_complementServiceImpl implements C_complementService{

    private final Logger log = LoggerFactory.getLogger(C_complementServiceImpl.class);

    @Inject
    private C_complementRepository c_complementRepository;

    /**
     * Save a c_complement.
     *
     * @param c_complement the entity to save
     * @return the persisted entity
     */
    public C_complement save(C_complement c_complement) {
        log.debug("Request to save C_complement : {}", c_complement);
        C_complement result = c_complementRepository.save(c_complement);
        return result;
    }

    /**
     *  Get all the c_complements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_complement> findAll(Pageable pageable) {
        log.debug("Request to get all C_complements");
        Page<C_complement> result = c_complementRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public List<C_complement> findAll() {
        log.debug("Request to get all C_complements");
        List<C_complement> result = c_complementRepository.findAll();
        return result;
    }

    public Page<C_complement> findAllByName(String filtername, Pageable pageable){
        log.debug("Request to get C_complements by name");
        Page<C_complement> result = c_complementRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }

    public List<C_complement> findAllByNameL(String filtername){
        log.debug("Request to get C_complements by name");
        List<C_complement> result = c_complementRepository.findByNameStartingWith(filtername);
        return result;
    }

    /**
     *  Get one c_complement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_complement findOne(Long id) {
        log.debug("Request to get C_complement : {}", id);
        C_complement c_complement = c_complementRepository.findOne(id);
        return c_complement;
    }

    /**
     *  Delete the  c_complement by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_complement : {}", id);
        c_complementRepository.delete(id);
    }
}
