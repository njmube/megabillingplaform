package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_cfdiService;
import org.megapractical.billingplatform.domain.Free_cfdi;
import org.megapractical.billingplatform.repository.Free_cfdiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Free_cfdi.
 */
@Service
@Transactional
public class Free_cfdiServiceImpl implements Free_cfdiService{

    private final Logger log = LoggerFactory.getLogger(Free_cfdiServiceImpl.class);

    @Inject
    private Free_cfdiRepository free_cfdiRepository;

    /**
     * Save a free_cfdi.
     *
     * @param free_cfdi the entity to save
     * @return the persisted entity
     */
    public Free_cfdi save(Free_cfdi free_cfdi) {
        log.debug("Request to save Free_cfdi : {}", free_cfdi);
        Free_cfdi result = free_cfdiRepository.save(free_cfdi);
        return result;
    }

    public Page<Free_cfdi> findCustom(Integer idFree_cfdi, String folio_fiscal, String rfc_receiver,
                                      LocalDate fromDate, LocalDate toDate,Integer idState,
                                      String serie,String folio, Pageable pageable){

        Page<Free_cfdi> page = null;
        List<Free_cfdi> all;
        if(idFree_cfdi != 0)
        {
            Free_cfdi free_cfdi= free_cfdiRepository.findOne(new Long(idFree_cfdi));
            all = new ArrayList<>();
            all.add(free_cfdi);
            page = new PageImpl<Free_cfdi>(all,pageable,1);
            return page;
        }
        else {

        }

        return page;
    }
    /**
     *  Get all the free_cfdis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Free_cfdi> findAll(Pageable pageable) {
        log.debug("Request to get all Free_cfdis");
        Page<Free_cfdi> result = free_cfdiRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one free_cfdi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Free_cfdi findOne(Long id) {
        log.debug("Request to get Free_cfdi : {}", id);
        Free_cfdi free_cfdi = free_cfdiRepository.findOne(id);
        return free_cfdi;
    }

    /**
     *  Delete the  free_cfdi by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_cfdi : {}", id);
        free_cfdiRepository.delete(id);
    }
}
