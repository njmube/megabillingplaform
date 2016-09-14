package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.service.Taxpayer_accountService;
import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.repository.Taxpayer_accountRepository;
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
 * Service Implementation for managing Taxpayer_account.
 */
@Service
@Transactional
public class Taxpayer_accountServiceImpl implements Taxpayer_accountService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_accountServiceImpl.class);

    @Inject
    private Taxpayer_accountRepository taxpayer_accountRepository;

    /**
     * Save a taxpayer_account.
     *
     * @param taxpayer_account the entity to save
     * @return the persisted entity
     */
    public Taxpayer_account save(Taxpayer_account taxpayer_account) {
        log.debug("Request to save Taxpayer_account : {}", taxpayer_account);
        Taxpayer_account result = taxpayer_accountRepository.save(taxpayer_account);
        return result;
    }

    public Page<Taxpayer_account> findCustom(User user,Pageable pageable){
        Page<Taxpayer_account> result = taxpayer_accountRepository.findAll(pageable);
        List<Taxpayer_account> list = new ArrayList<>();
        for(Taxpayer_account taxpayer_account: result.getContent()){
            boolean existe = false;
            for (User item : taxpayer_account.getUsers()) {
                if (item.getLogin().compareTo(user.getLogin()) == 0) {
                    existe = true;
                }
            }
            if(existe){
                list.add(taxpayer_account);
            }
        }
        Page<Taxpayer_account> page = new PageImpl<Taxpayer_account>(list,pageable,result.getTotalElements());
        return page;
    }

    /**
     *  Get all the taxpayer_accounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Taxpayer_account> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_accounts");
        Page<Taxpayer_account> result = taxpayer_accountRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one taxpayer_account by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Taxpayer_account findOne(Long id) {
        log.debug("Request to get Taxpayer_account : {}", id);
        Taxpayer_account taxpayer_account = taxpayer_accountRepository.findOneWithEagerRelationships(id);
        return taxpayer_account;
    }

    /**
     *  Delete the  taxpayer_account by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_account : {}", id);
        taxpayer_accountRepository.delete(id);
    }
}
