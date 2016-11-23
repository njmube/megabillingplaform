package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.repository.AuthorityRepository;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Taxpayer_account.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_accountResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_accountResource.class);

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    @Inject
    private UserService userService;

    @Inject
    private Taxpayer_certificateService taxpayer_certificateService;

    @Inject
    private Tax_addressService tax_addressService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    @Inject
    Audit_event_typeService audit_event_typeService;

    @Inject
    private Request_taxpayer_accountService request_taxpayer_accountService;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /taxpayer-accounts : Create a new taxpayer_account.
     *
     * @param taxpayer_account the taxpayer_account to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_account, or with status 400 (Bad Request) if the taxpayer_account has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-accounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_account> createTaxpayer_account(@Valid @RequestBody Taxpayer_account taxpayer_account) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_account : {}", taxpayer_account);
        if (taxpayer_account.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_account", "idexists", "A new taxpayer_account cannot already have an ID")).body(null);
        }
        /*Taxpayer_certificate taxpayer_certificate = taxpayer_certificateService.save(taxpayer_account.getTaxpayer_certificate(), taxpayer_account.getRfc());
        taxpayer_account.setTaxpayer_certificate(taxpayer_certificate);*/
        Taxpayer_account result = taxpayer_accountService.save(taxpayer_account);
        //result.setTaxpayer_certificate(taxpayer_certificateService.findOne(taxpayer_account.getTaxpayer_certificate().getId()));

        return ResponseEntity.created(new URI("/api/taxpayer-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_account", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-accounts : Updates an existing taxpayer_account.
     *
     * @param taxpayer_account the taxpayer_account to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_account,
     * or with status 400 (Bad Request) if the taxpayer_account is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_account couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-accounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_account> updateTaxpayer_account(@Valid @RequestBody Taxpayer_account taxpayer_account) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_account : {}", taxpayer_account);
        if(taxpayer_account.getTaxpayer_certificate() != null){
            if(taxpayer_account.getTaxpayer_certificate().getFilecertificate() == null){
                taxpayer_account.setTaxpayer_certificate(null);
                log.debug("Datos del certificado de la cuenta: {}", taxpayer_account.getTaxpayer_certificate());
            }
        }
        Taxpayer_account pre = taxpayer_accountService.findOne(taxpayer_account.getId());
        //Actualizando usuarios
        if(pre.getUsers().size()!=taxpayer_account.getUsers().size()){
            log.debug("Actualizando usuarios : {}", taxpayer_account.getUsers().toString());
            List<Request_taxpayer_account> listrequest_taxpayer_account = request_taxpayer_accountService.findByRfc(taxpayer_account.getRfc());
            boolean isAdmin = false;
            for(Request_taxpayer_account request:listrequest_taxpayer_account){
                if(request.getUser().getLogin().compareTo(SecurityUtils.getCurrentUserLogin())==0){
                    isAdmin = true;
                }
            }
            if(!isAdmin){
                log.debug("No es admin de la cuenta", SecurityUtils.getCurrentUserLogin());
                taxpayer_account.setUsers(pre.getUsers());
            }else {
                if(pre.getUsers().size()< taxpayer_account.getUsers().size()){
                    Set<User> listusers = taxpayer_account.getUsers();
                    for (User user:listusers) {
                        User usercomplete = userService.getUserWithAuthorities(user.getId());
                        Authority authority = authorityRepository.findOne("ROLE_AFILITATED");
                        Set<Authority> authorities = new HashSet<>();
                        authorities.add(authority);
                        usercomplete.setAuthorities(authorities);
                        userRepository.save(usercomplete);
                    }
                }
            }
            //actualizo la cuenta con los usuarios

            Taxpayer_account result = taxpayer_accountService.save(taxpayer_account);

            Long id = new Long("45");
            Long idtypeevent = new Long("1");
            tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_account", taxpayer_account.getId().toString()))
                .body(result);
        }else{
            if(taxpayer_account.getTaxpayer_certificate()!=null){
                //validacion del certificado
                if(taxpayer_account.getTaxpayer_certificate().getPass_certificate()!=null && taxpayer_account.getTaxpayer_certificate().getInfo_certificate()!=null){
                    String [] response = taxpayer_certificateService.validateCertificate(taxpayer_account.getTaxpayer_certificate().getFilecertificate(),
                        taxpayer_account.getTaxpayer_certificate().getFilekey(), taxpayer_account.getTaxpayer_certificate().getPass_certificate());
                    taxpayer_account.getTaxpayer_certificate().setInfo_certificate(response[1]);

                    Taxpayer_certificate taxpayer_certificate = taxpayer_certificateService.InfoCertificate(taxpayer_account.getTaxpayer_certificate());

                    Integer days = Integer.parseInt(taxpayer_certificate.getValid_days_cert());
                    if(days <= 0){
                        Long id = new Long("54");
                        Long idtypeevent = new Long("1");
                        tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));
                    }

                    if(taxpayer_account.getRfc().compareTo(taxpayer_certificate.getRfc_certificate())!=0) {
                        taxpayer_certificate.setInfo_certificate("ERROR: Emitter RFC is diferent to Certificate RFC");
                        taxpayer_certificate.setNumber_certificate(null);
                        taxpayer_certificate.setDate_certificate(null);
                        taxpayer_certificate.setRfc_certificate(null);
                        taxpayer_certificate.setBussines_name_cert(null);
                        taxpayer_certificate.setDate_created_cert(null);
                        taxpayer_certificate.setDate_expiration_cert(null);
                        taxpayer_certificate.setValid_days_cert(null);

                        //Se registra evento de incompatibilidad del RFC y el certificado
                        Long id = new Long("53");
                        Long idtypeevent = new Long("1");
                        tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));
                    }else {
                        if (response[0].compareTo("0") != 0) {
                            taxpayer_certificate.setNumber_certificate(null);
                            taxpayer_certificate.setDate_certificate(null);
                            taxpayer_certificate.setRfc_certificate(null);
                            taxpayer_certificate.setBussines_name_cert(null);
                            taxpayer_certificate.setDate_created_cert(null);
                            taxpayer_certificate.setDate_expiration_cert(null);
                            taxpayer_certificate.setValid_days_cert(null);

                            if(response[0].compareTo("1") == 0){
                                Long id = new Long("55");
                                Long idtypeevent = new Long("1");
                                tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));
                            }
                        } else {
                            taxpayer_certificate.setInfo_certificate(null);
                        }
                    }
                    taxpayer_account.setTaxpayer_certificate(taxpayer_certificate);
                    return new ResponseEntity<Taxpayer_account>(taxpayer_account,HttpStatus.OK);
                }else{
                    //actualizacion del certificado
                    if(taxpayer_account.getTaxpayer_certificate().getFilecertificate() != null) {
                        Taxpayer_certificate taxpayer_certificate = taxpayer_certificateService.InfoCertificate(taxpayer_account.getTaxpayer_certificate());
                        if(taxpayer_account.getRfc().compareTo(taxpayer_certificate.getRfc_certificate())!=0) {
                            taxpayer_certificate.setInfo_certificate("ERROR: Emitter RFC is diferent to Certificate RFC");
                            taxpayer_certificate.setNumber_certificate(null);
                            taxpayer_certificate.setDate_certificate(null);
                            taxpayer_certificate.setRfc_certificate(null);
                            taxpayer_certificate.setBussines_name_cert(null);
                            taxpayer_certificate.setDate_created_cert(null);
                            taxpayer_certificate.setDate_expiration_cert(null);
                            taxpayer_certificate.setValid_days_cert(null);

                            //Se registra evento de incompatibilidad del RFC y el certificado
                            Long id = new Long("53");
                            Long idtypeevent = new Long("1");
                            tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));
                            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_account", "diferentRFC", "ERROR: Taxpayer Account RFC is diferent to Certificate RFC")).body(null);
                        }
                    }
                    if (taxpayer_account.getId() == null) {
                        return createTaxpayer_account(taxpayer_account);
                    }
                    tax_addressService.save(taxpayer_account.getTax_address());

                    if(taxpayer_account.getTaxpayer_certificate() != null){
                        if(taxpayer_account.getTaxpayer_certificate().getPath_certificate()!=null) {
                            Taxpayer_certificate tc = taxpayer_certificateService.save(taxpayer_account.getTaxpayer_certificate(), taxpayer_account.getRfc());
                            taxpayer_account.setTaxpayer_certificate(tc);
                        }
                    }
                    Taxpayer_account result = taxpayer_accountService.save(taxpayer_account);
                    if(taxpayer_account.getTaxpayer_certificate() != null) {
                        if (taxpayer_account.getTaxpayer_certificate().getPath_certificate() != null) {
                            result.setTaxpayer_certificate(taxpayer_certificateService.findOne(taxpayer_account.getTaxpayer_certificate().getId()));
                        }
                    }
                    result.setTax_address(tax_addressService.findOne(result.getTax_address().getId()));

                    Long id = new Long("45");
                    Long idtypeevent = new Long("1");
                    tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));

                    return ResponseEntity.ok()
                        .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_account", taxpayer_account.getId().toString()))
                        .body(result);
                }
            }
            if (taxpayer_account.getId() == null) {
                return createTaxpayer_account(taxpayer_account);
            }

            tax_addressService.save(taxpayer_account.getTax_address());

            Taxpayer_account result = taxpayer_accountService.save(taxpayer_account);

            Long id = new Long("45");
            Long idtypeevent = new Long("1");
            tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_account", taxpayer_account.getId().toString()))
                .body(result);
        }
    }

    /**
     * GET  /taxpayer-accounts : get all the taxpayer_accounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_accounts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-accounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Taxpayer_account>> getAllTaxpayer_accounts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_accounts");

        String login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(login);
        if(user.isPresent()) {
            boolean administrator = false;
            for (Authority item : user.get().getAuthorities()) {
                if (item.getName().compareTo("ROLE_ADMIN") == 0) {
                    administrator = true;
                }
            }
            if (administrator) {
                Page<Taxpayer_account> page = taxpayer_accountService.findAll(pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-accounts");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }
            else {
                Page<Taxpayer_account> page = taxpayer_accountService.findCustom(user.get(),pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-accounts");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }
        }
        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_account", "notfound", "Taxpayer acoount not found")).body(null);

    }

    /**
     * GET  /taxpayer-accounts/:id : get the "id" taxpayer_account.
     *
     * @param id the id of the taxpayer_account to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_account, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-accounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_account> getTaxpayer_account(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_account : {}", id);
        Taxpayer_account taxpayer_account = taxpayer_accountService.findOne(id);
        if(taxpayer_account.getTaxpayer_certificate() == null){
            taxpayer_account.setTaxpayer_certificate(new Taxpayer_certificate());
        }
        else{
            taxpayer_account.setTaxpayer_certificate(taxpayer_certificateService.findOne(taxpayer_account.getTaxpayer_certificate().getId()));
        }
        return Optional.ofNullable(taxpayer_account)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-accounts/:id : delete the "id" taxpayer_account.
     *
     * @param id the id of the taxpayer_account to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-accounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_account(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_account : {}", id);
        taxpayer_accountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_account", id.toString())).build();
    }

}
