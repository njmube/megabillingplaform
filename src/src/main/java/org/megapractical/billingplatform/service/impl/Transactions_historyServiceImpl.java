package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Transactions_historyService;
import org.megapractical.billingplatform.domain.Transactions_history;
import org.megapractical.billingplatform.repository.Transactions_historyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Transactions_history.
 */
@Service
@Transactional
public class Transactions_historyServiceImpl implements Transactions_historyService{

    private final Logger log = LoggerFactory.getLogger(Transactions_historyServiceImpl.class);
    
    @Inject
    private Transactions_historyRepository transactions_historyRepository;
    
    /**
     * Save a transactions_history.
     * 
     * @param transactions_history the entity to save
     * @return the persisted entity
     */
    public Transactions_history save(Transactions_history transactions_history) {
        log.debug("Request to save Transactions_history : {}", transactions_history);
        Transactions_history result = transactions_historyRepository.save(transactions_history);
        return result;
    }

    /**
     *  Get all the transactions_histories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Transactions_history> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions_histories");
        Page<Transactions_history> result = transactions_historyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one transactions_history by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Transactions_history findOne(Long id) {
        log.debug("Request to get Transactions_history : {}", id);
        Transactions_history transactions_history = transactions_historyRepository.findOne(id);
        return transactions_history;
    }

    /**
     *  Delete the  transactions_history by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Transactions_history : {}", id);
        transactions_historyRepository.delete(id);
    }
}
