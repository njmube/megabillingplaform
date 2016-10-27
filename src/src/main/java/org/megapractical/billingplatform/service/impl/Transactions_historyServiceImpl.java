package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.service.Transactions_historyService;
import org.megapractical.billingplatform.domain.Transactions_history;
import org.megapractical.billingplatform.repository.Transactions_historyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
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

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

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

    public Page<Transactions_history> findByAccount(Integer idaccount, Integer month, Pageable pageable){
        Taxpayer_account taxpayer_account = taxpayer_accountService.findOne(new Long(idaccount.toString()));

        Page<Transactions_history> result = transactions_historyRepository.findAll(pageable);
        List<Transactions_history> list = new ArrayList<>();
        for(Transactions_history transactions_history:result.getContent()){
            boolean a = true;

            if(month == 1){
                //Busqueda en el mes actual
                if(transactions_history.getDate_transaction().getYear()!= ZonedDateTime.now().getYear() ||
                    transactions_history.getDate_transaction().getMonthValue() != ZonedDateTime.now().getMonthValue()){
                    a = false;
                }
            }
            if(month == 2){
                //Busqueda en la semana
                ZonedDateTime ahora = ZonedDateTime.now();
                int diasemana = ahora.getDayOfWeek().getValue();
                if(transactions_history.getDate_transaction().isBefore(ahora.minusDays(diasemana))){
                    a = false;
                }
            }

            if(a){
                if(transactions_history.getTaxpayer_account().getRfc().compareTo(taxpayer_account.getRfc())==0){
                    list.add(transactions_history);
                }
            }
        }

        return new PageImpl<Transactions_history>(list, pageable,list.size());
    }


    public Page<Transactions_history> findByUser(User user, Integer month, Pageable pageable){
        Page<Transactions_history> result = transactions_historyRepository.findAll(pageable);
        List<Transactions_history> list = new ArrayList<>();
        for(Transactions_history transactions_history:result.getContent()){
            boolean a = true;

            if(month == 1){
                //Busqueda en el mes actual
                if(transactions_history.getDate_transaction().getYear()!= ZonedDateTime.now().getYear() ||
                    transactions_history.getDate_transaction().getMonthValue() != ZonedDateTime.now().getMonthValue()){
                    a = false;
                }
            }
            if(month == 2){
                //Busqueda en la semana
                ZonedDateTime ahora = ZonedDateTime.now();
                int diasemana = ahora.getDayOfWeek().getValue();
                if(transactions_history.getDate_transaction().isBefore(ahora.minusDays(diasemana))){
                    a = false;
                }
            }

            if(a){
                if(transactions_history.getUser().getLogin().compareTo(user.getLogin())==0){
                    list.add(transactions_history);
                }
            }
        }

        return new PageImpl<Transactions_history>(list, pageable,list.size());
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
