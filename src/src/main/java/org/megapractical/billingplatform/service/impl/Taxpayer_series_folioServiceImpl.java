package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.service.Taxpayer_series_folioService;
import org.megapractical.billingplatform.domain.Taxpayer_series_folio;
import org.megapractical.billingplatform.repository.Taxpayer_series_folioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_series_folio.
 */
@Service
@Transactional
public class Taxpayer_series_folioServiceImpl implements Taxpayer_series_folioService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_series_folioServiceImpl.class);

    @Inject
    private Taxpayer_series_folioRepository taxpayer_series_folioRepository;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    /**
     * Save a taxpayer_series_folio.
     *
     * @param taxpayer_series_folio the entity to save
     * @return the persisted entity
     */
    public Taxpayer_series_folio save(Taxpayer_series_folio taxpayer_series_folio) {
        log.debug("Request to save Taxpayer_series_folio : {}", taxpayer_series_folio);
        Taxpayer_series_folio result = taxpayer_series_folioRepository.save(taxpayer_series_folio);
        return result;
    }

    /**
     *  Get all the taxpayer_series_folios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_series_folio> findAll(Pageable pageable, Integer idtaxpayer_account) {
        log.debug("Request to get all Taxpayer_series_folios");
        Page<Taxpayer_series_folio> result = taxpayer_series_folioRepository.findAll(pageable);
        if(idtaxpayer_account != 0) {
            List<Taxpayer_series_folio> list = new ArrayList<>();
            Long id = new Long(idtaxpayer_account.toString());
            Taxpayer_account taxpayer_account = taxpayer_accountService.findOne(id);
            for (Taxpayer_series_folio taxpayer_series_folio : result.getContent()) {
                if (taxpayer_series_folio.getTaxpayer_account().getId().compareTo(taxpayer_account.getId()) == 0) {
                    list.add(taxpayer_series_folio);
                }
            }
            Page<Taxpayer_series_folio> page = new PageImpl<Taxpayer_series_folio>(list, pageable, result.getTotalElements());
            return page;
        }else
        {
            return result;
        }
    }

    /**
     *  Get one taxpayer_series_folio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Taxpayer_series_folio findOne(Long id) {
        log.debug("Request to get Taxpayer_series_folio : {}", id);
        Taxpayer_series_folio taxpayer_series_folio = taxpayer_series_folioRepository.findOne(id);
        return taxpayer_series_folio;
    }

    /**
     *  Delete the  taxpayer_series_folio by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_series_folio : {}", id);
        taxpayer_series_folioRepository.delete(id);
    }
}
