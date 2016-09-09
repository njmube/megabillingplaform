package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.repository.AuthorityRepository;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.repository.Request_taxpayer_accountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing Request_taxpayer_account.
 */
@Service
@Transactional
public class Request_taxpayer_accountServiceImpl implements Request_taxpayer_accountService{

    private final Logger log = LoggerFactory.getLogger(Request_taxpayer_accountServiceImpl.class);

    @Inject
    private Request_taxpayer_accountRepository request_taxpayer_accountRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    @Inject
    private Type_taxpayerService type_taxpayerService;

    @Inject
    private Tax_addressService tax_addressService;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    @Inject
    Audit_event_typeService audit_event_typeService;

    @Inject
    MailService mailService;


    /**
     * Save a request_taxpayer_account.
     *
     * @param request_taxpayer_account the entity to save
     * @return the persisted entity
     */
    public Request_taxpayer_account save(Request_taxpayer_account request_taxpayer_account) {
        log.debug("Request to save Request_taxpayer_account : {}", request_taxpayer_account);
        Request_taxpayer_account result = request_taxpayer_accountRepository.save(request_taxpayer_account);
        return result;
    }

    /*public Page<Request_taxpayer_account> findAllOrderByIdDesc(Pageable pageable){
        log.debug("Request to get all Request_taxpayer_accounts");
        return request_taxpayer_accountRepository.findAllOrderByIdDesc(pageable);
    }*/

    /**
     *  Get all the request_taxpayer_accounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Request_taxpayer_account> findAll(Pageable pageable) {
        log.debug("Request to get all Request_taxpayer_accounts");
        Page<Request_taxpayer_account> result = request_taxpayer_accountRepository.findAll(pageable);
        return result;
    }

    public Page<Request_taxpayer_account> findByDaterequestBetweenOrderByIdDesc(ZonedDateTime from, ZonedDateTime to, Pageable pageable){
        return request_taxpayer_accountRepository.findByDaterequestBetweenOrderByIdDesc(from,to, pageable);
    }

    public Page<Request_taxpayer_account> findByDaterequestBetweenAndRequest_StateOrderByIdDesc(ZonedDateTime from, ZonedDateTime to, Request_state request_state, Pageable pageable){
        if(request_state == null) {
            log.debug("Request to get all Request_taxpayer_accounts not request_state");
            return request_taxpayer_accountRepository.findByDaterequestBetweenOrderByIdDesc(from, to, pageable);
        }
        else {
            log.debug("Request to get all Request_taxpayer_accounts whit request_state: "+ request_state.getId().toString());
            Page<Request_taxpayer_account> result = request_taxpayer_accountRepository.findByDaterequestBetweenOrderByIdDesc(from, to, pageable);
            List<Request_taxpayer_account> list = new ArrayList<>();
            for(Request_taxpayer_account request_taxpayer_account: result.getContent()){
                if(request_taxpayer_account.getRequest_state().getId().compareTo(request_state.getId())==0){
                    list.add(request_taxpayer_account);
                }
            }
            Page<Request_taxpayer_account> page = new PageImpl<Request_taxpayer_account>(list,pageable,result.getTotalElements());
            return page;
        }
    }

    public void acceptedRequest(Request_taxpayer_account request_taxpayer_account){
        Taxpayer_account taxpayer_account = new Taxpayer_account();
        taxpayer_account.setRfc(request_taxpayer_account.getRfc());
        taxpayer_account.setBussines_name(request_taxpayer_account.getBussinesname());
        taxpayer_account.setEmail(request_taxpayer_account.getAccountemail());
        taxpayer_account.setAccuracy(2);
        if(taxpayer_account.getRfc().length() == 12){
            taxpayer_account.setType_taxpayer(type_taxpayerService.findOne(new Long("1")));
        }else {
            taxpayer_account.setType_taxpayer(type_taxpayerService.findOne(new Long("2")));
        }
        Tax_address tax_address = new Tax_address();
        tax_address.setC_country(request_taxpayer_account.getTax_address_request().getC_country());
        tax_address.setC_state(request_taxpayer_account.getTax_address_request().getC_state());
        tax_address.setC_municipality(request_taxpayer_account.getTax_address_request().getC_municipality());
        tax_address.setC_colony(request_taxpayer_account.getTax_address_request().getC_colony());
        tax_address.setC_zip_code(request_taxpayer_account.getTax_address_request().getC_zip_code());
        tax_address.setStreet(request_taxpayer_account.getTax_address_request().getStreet());
        tax_address.setNo_ext(request_taxpayer_account.getTax_address_request().getNo_ext());
        tax_address.setNo_int(request_taxpayer_account.getTax_address_request().getNo_int());
        tax_address.setReference(request_taxpayer_account.getTax_address_request().getReference());
        tax_address.setIntersection(request_taxpayer_account.getTax_address_request().getIntersection());
        tax_address.setLocation(request_taxpayer_account.getTax_address_request().getLocation());
        Tax_address tax_addressResult = tax_addressService.save(tax_address);
        if(tax_addressResult!=null){
            taxpayer_account.setTax_address(tax_addressResult);
        }
        User usercomplete = userService.getUserWithAuthorities(request_taxpayer_account.getUser().getId());
        Set<User> users = new HashSet<>();
        users.add(usercomplete);
        taxpayer_account.setUsers(users);

        Taxpayer_account taxpayer_accountResult = taxpayer_accountService.save(taxpayer_account);
        if(taxpayer_accountResult != null) {
            boolean afilitated = false;
            boolean user = false;

            for (Authority item : usercomplete.getAuthorities()) {
                if (item.getName().compareTo("ROLE_AFILITATED") == 0) {
                    afilitated = true;
                }
                if (item.getName().compareTo("ROLE_USER") == 0) {
                    user = true;
                }
            }
            if (!afilitated && user) {
                Authority authority = authorityRepository.findOne("ROLE_AFILITATED");
                Set<Authority> authorities = new HashSet<>();
                authorities.add(authority);
                usercomplete.setAuthorities(authorities);
                userRepository.save(usercomplete);
            }

            mailService.sendCreatedAccountMail(usercomplete,taxpayer_accountResult);
            Long id = new Long("42");
            Long idtypeevent = new Long("1");
            tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));
        }
    }

    public void rejectRequest(Request_taxpayer_account request_taxpayer_account){
        mailService.sendRejectAccountMail(request_taxpayer_account.getUser(), request_taxpayer_account);
        Long id = new Long("41");
        Long idtypeevent = new Long("1");
        tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));
    }


    /**
     *  Get one request_taxpayer_account by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Request_taxpayer_account findOne(Long id) {
        log.debug("Request to get Request_taxpayer_account : {}", id);
        Request_taxpayer_account request_taxpayer_account = request_taxpayer_accountRepository.findOne(id);
        return request_taxpayer_account;
    }

    /**
     *  Delete the  request_taxpayer_account by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Request_taxpayer_account : {}", id);
        request_taxpayer_accountRepository.delete(id);
    }
}
