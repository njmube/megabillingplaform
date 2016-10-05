package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.service.Taxpayer_transactionsService;
import org.megapractical.billingplatform.domain.Taxpayer_transactions;
import org.megapractical.billingplatform.repository.Taxpayer_transactionsRepository;
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
 * Service Implementation for managing Taxpayer_transactions.
 */
@Service
@Transactional
public class Taxpayer_transactionsServiceImpl implements Taxpayer_transactionsService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_transactionsServiceImpl.class);

    @Inject
    private Taxpayer_transactionsRepository taxpayer_transactionsRepository;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    /**
     * Save a taxpayer_transactions.
     *
     * @param taxpayer_transactions the entity to save
     * @return the persisted entity
     */
    public Taxpayer_transactions save(Taxpayer_transactions taxpayer_transactions) {
        log.debug("Request to save Taxpayer_transactions : {}", taxpayer_transactions);
        Taxpayer_transactions result = taxpayer_transactionsRepository.save(taxpayer_transactions);
        return result;
    }

    public Page<Taxpayer_transactions> findByAccount(Integer idaccount, Pageable pageable){
        log.debug("Request to get all Taxpayer_transactions by account");
        Taxpayer_account account = taxpayer_accountService.findOne(new Long(idaccount.toString()));
        Page<Taxpayer_transactions> result = taxpayer_transactionsRepository.findAll(pageable);
        List<Taxpayer_transactions> list = new ArrayList<>();
        for(Taxpayer_transactions tras: result.getContent()){
            if(account.equals(tras.getTaxpayer_account())){
                list.add(tras);
            }
        }
        if(list.size() == 0){
            Taxpayer_transactions newtras = new Taxpayer_transactions();
            newtras.setTaxpayer_account(account);
            newtras.setTransactions_available(0);
            newtras.setTransactions_spent(0);
            list.add(taxpayer_transactionsRepository.save(newtras));
        }
        Page<Taxpayer_transactions> page = new PageImpl<Taxpayer_transactions>(list,pageable,result.getTotalElements());
        return page;
    }

    /**
     *  Get all the taxpayer_transactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_transactions> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_transactions");
        Page<Taxpayer_transactions> result = taxpayer_transactionsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one taxpayer_transactions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Taxpayer_transactions findOne(Long id) {
        log.debug("Request to get Taxpayer_transactions : {}", id);
        Taxpayer_transactions taxpayer_transactions = taxpayer_transactionsRepository.findOne(id);
        return taxpayer_transactions;
    }

    /**
     *  Delete the  taxpayer_transactions by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_transactions : {}", id);
        taxpayer_transactionsRepository.delete(id);
    }
}
