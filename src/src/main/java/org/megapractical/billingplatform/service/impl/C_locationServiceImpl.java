package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_locationService;
import org.megapractical.billingplatform.domain.C_location;
import org.megapractical.billingplatform.repository.C_locationRepository;
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
 * Service Implementation for managing C_location.
 */
@Service
@Transactional
public class C_locationServiceImpl implements C_locationService{

    private final Logger log = LoggerFactory.getLogger(C_locationServiceImpl.class);

    @Inject
    private C_locationRepository c_locationRepository;

    /**
     * Save a c_location.
     *
     * @param c_location the entity to save
     * @return the persisted entity
     */
    public C_location save(C_location c_location) {
        log.debug("Request to save C_location : {}", c_location);
        C_location result = c_locationRepository.save(c_location);
        return result;
    }

    public List<C_location> findByMunicipality(Long municipalityId){
        log.debug("Request to get all C_states");
        List<C_location> result = c_locationRepository.findAll();
        List<C_location> res = new ArrayList<>();
        for(int i = 0; i < result.size();i++){
            if(result.get(i).getC_municipality().getId() == municipalityId){
                res.add(result.get(i));
            }
        }
        return res;
    }

    /**
     *  Get all the c_locations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<C_location> findAll(Pageable pageable) {
        log.debug("Request to get all C_locations");
        Page<C_location> result = c_locationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one c_location by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public C_location findOne(Long id) {
        log.debug("Request to get C_location : {}", id);
        C_location c_location = c_locationRepository.findOne(id);
        return c_location;
    }

    /**
     *  Delete the  c_location by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_location : {}", id);
        c_locationRepository.delete(id);
    }
}
