package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_municipalityService;
import org.megapractical.billingplatform.domain.C_municipality;
import org.megapractical.billingplatform.repository.C_municipalityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing C_municipality.
 */
@Service
@Transactional
public class C_municipalityServiceImpl implements C_municipalityService{

    private final Logger log = LoggerFactory.getLogger(C_municipalityServiceImpl.class);

    @Inject
    private C_municipalityRepository c_municipalityRepository;

    /**
     * Save a c_municipality.
     *
     * @param c_municipality the entity to save
     * @return the persisted entity
     */
    public C_municipality save(C_municipality c_municipality) {
        log.debug("Request to save C_municipality : {}", c_municipality);
        C_municipality result = c_municipalityRepository.save(c_municipality);
        return result;
    }

    /**
     *  Get all the c_municipalities.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_municipality> findAll(Pageable pageable) {
        log.debug("Request to get all C_municipalities");
        Page<C_municipality> result = c_municipalityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one c_municipality by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_municipality findOne(Long id) {
        log.debug("Request to get C_municipality : {}", id);
        C_municipality c_municipality = c_municipalityRepository.findOne(id);
        return c_municipality;
    }

    @Transactional(readOnly = true)
    public List<C_municipality> findByState(long stateId){
        log.debug("Request to get all C_municipality");
        List<C_municipality> result = c_municipalityRepository.findAll();
        if (stateId == -1)
            return result;
        List<C_municipality> res = new ArrayList<>();
        for(int i = 0; i < result.size();i++){
            if(result.get(i).getC_state().getId() == stateId){
                res.add(result.get(i));
            }
        }
        return res;
    }

    /**
     *  Delete the  c_municipality by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_municipality : {}", id);
        c_municipalityRepository.delete(id);
    }
}
