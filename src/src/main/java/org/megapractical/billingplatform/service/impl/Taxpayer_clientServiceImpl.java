package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.service.Taxpayer_clientService;
import org.megapractical.billingplatform.domain.Taxpayer_client;
import org.megapractical.billingplatform.repository.Taxpayer_clientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_client.
 */
@Service
@Transactional
public class Taxpayer_clientServiceImpl implements Taxpayer_clientService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_clientServiceImpl.class);

    @Inject
    private Taxpayer_clientRepository taxpayer_clientRepository;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    /**
     * Save a taxpayer_client.
     *
     * @param taxpayer_client the entity to save
     * @return the persisted entity
     */
    public Taxpayer_client save(Taxpayer_client taxpayer_client) {
        log.debug("Request to save Taxpayer_client : {}", taxpayer_client);
        Taxpayer_client result = taxpayer_clientRepository.save(taxpayer_client);
        return result;
    }

    /**
     *  Get all the taxpayer_client.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_client> findAll(Pageable pageable, Integer taxpayeraccount) {
        log.debug("Request to get all Taxpayer_clients");

        if(taxpayeraccount == 0) {
            List<Taxpayer_client> emptyList = new ArrayList<>();
            Page<Taxpayer_client> emptyPage = new PageImpl<Taxpayer_client>(emptyList, pageable, 0);
            return emptyPage;
        }

        Page<Taxpayer_client> result = taxpayer_clientRepository.findAll(pageable);
        if(taxpayeraccount != 0) {
            List<Taxpayer_client> list = new ArrayList<>();
            Long id = new Long(taxpayeraccount.toString());
            Taxpayer_account taxpayer_account = taxpayer_accountService.findOne(id);

            for (Taxpayer_client taxpayer_client : result.getContent()) {
                if (taxpayer_client.getTaxpayer_account().getId().compareTo(taxpayer_account.getId()) == 0) {
                    list.add(taxpayer_client);
                }
            }
            Page<Taxpayer_client> page = new PageImpl<Taxpayer_client>(list, pageable, result.getTotalElements());
            return page;
        }
        else {
            return result;
        }
    }

    /**
     *  Get all the taxpayer_clients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_client> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_clients");
        Page<Taxpayer_client> result = taxpayer_clientRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one taxpayer_client by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Taxpayer_client findOne(Long id) {
        log.debug("Request to get Taxpayer_client : {}", id);
        Taxpayer_client taxpayer_client = taxpayer_clientRepository.findOne(id);
        return taxpayer_client;
    }

    /**
     *  Delete the  taxpayer_client by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_client : {}", id);

        taxpayer_clientRepository.delete(id);
    }
}
