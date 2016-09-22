package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Request_state;
import org.megapractical.billingplatform.domain.Request_taxpayer_account;
import org.megapractical.billingplatform.domain.Tax_address_request;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Request_taxpayer_account.
 */
@RestController
@RequestMapping("/api")
public class Request_taxpayer_accountResource {

    private final Logger log = LoggerFactory.getLogger(Request_taxpayer_accountResource.class);

    @Inject
    private Request_taxpayer_accountService request_taxpayer_accountService;

    @Inject
    private Request_stateService request_stateService;

    @Inject
    UserService userService;

    @Inject
    Tax_address_requestService tax_address_requestService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    @Inject
    Audit_event_typeService audit_event_typeService;

    @Inject
    MailService mailService;

    /**
     * POST  /request-taxpayer-accounts : Create a new request_taxpayer_account.
     *
     * @param request_taxpayer_account the request_taxpayer_account to create
     * @return the ResponseEntity with status 201 (Created) and with body the new request_taxpayer_account, or with status 400 (Bad Request) if the request_taxpayer_account has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/request-taxpayer-accounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Request_taxpayer_account> createRequest_taxpayer_account(@Valid @RequestBody Request_taxpayer_account request_taxpayer_account) throws URISyntaxException {
        log.debug("REST request to save a new Request_taxpayer_account : {}", request_taxpayer_account);
        if (request_taxpayer_account.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("request_taxpayer_account", "idexists", "A new request_taxpayer_account cannot already have an ID")).body(null);
        }
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get();
        request_taxpayer_account.setUser(user);
        request_taxpayer_account.setDaterequest(ZonedDateTime.now());
        request_taxpayer_account.setRequest_state(request_stateService.findOne(new Long("1")));

        log.debug("Salvando direccion : {}", request_taxpayer_account.getTax_address_request());
        Tax_address_request tax_address_request = tax_address_requestService.save(request_taxpayer_account.getTax_address_request());
        request_taxpayer_account.setTax_address_request(tax_address_request);
        request_taxpayer_account.setRequest_state(request_stateService.findOne(new Long("2")));

        Request_taxpayer_account result = request_taxpayer_accountService.save(request_taxpayer_account);
        SendEmailRequest(user, result);

        Long id = new Long("38");
        Long idtypeevent = new Long("1");
        tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));

        request_taxpayer_accountService.acceptedRequest(result);

        return ResponseEntity.created(new URI("/api/request-taxpayer-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("request_taxpayer_account", result.getId().toString()))
            .body(result);
    }

    private void SendEmailRequest(User user, Request_taxpayer_account request_taxpayer_account){
        mailService.sendRequestMail(user,request_taxpayer_account);
    }

    /**
     * PUT  /request-taxpayer-accounts : Updates an existing request_taxpayer_account.
     *
     * @param request_taxpayer_account the request_taxpayer_account to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated request_taxpayer_account,
     * or with status 400 (Bad Request) if the request_taxpayer_account is not valid,
     * or with status 500 (Internal Server Error) if the request_taxpayer_account couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/request-taxpayer-accounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Request_taxpayer_account> updateRequest_taxpayer_account(@Valid @RequestBody Request_taxpayer_account request_taxpayer_account) throws URISyntaxException {
        log.debug("REST request to update Request_taxpayer_account : {}", request_taxpayer_account);
        if (request_taxpayer_account.getId() == null) {
            return createRequest_taxpayer_account(request_taxpayer_account);
        }
        Request_taxpayer_account pre = request_taxpayer_accountService.findOne(request_taxpayer_account.getId());
        /*log.debug("REST PRE Request_taxpayer_account state: {}", pre.getRequest_state().getId());
        log.debug("REST Request_taxpayer_account state: {}", request_taxpayer_account.getRequest_state().getId());
        if(pre.getRequest_state().getId() == 1){
            Request_taxpayer_account result = request_taxpayer_accountService.save(request_taxpayer_account);
            if(result.getRequest_state().getId()==2){
                //Aprobada la solicitud
                request_taxpayer_accountService.acceptedRequest(result);
            }
            if(result.getRequest_state().getId()==3){
                //Rechazada la solicitud
                request_taxpayer_accountService.rejectRequest(result);
            }
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("request_taxpayer_account", request_taxpayer_account.getId().toString()))
                .body(result);
        }
        if(pre.getRequest_state().getId() == 3 && request_taxpayer_account.getRequest_state().getId()==2){
            Request_taxpayer_account result = request_taxpayer_accountService.save(request_taxpayer_account);
                //Aprobada la solicitud
                request_taxpayer_accountService.acceptedRequest(result);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert("request_taxpayer_account", request_taxpayer_account.getId().toString()))
                    .body(result);
        }*/
        return ResponseEntity.badRequest().
            headers(HeaderUtil.createFailureAlert("request_taxpayer_account", "operationerror", "Esta operación no es aceptada")).body(pre);
    }

    /**
     * GET  /request-taxpayer-accounts : get all the request_taxpayer_accounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of request_taxpayer_accounts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/request-taxpayer-accounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"datefrom", "dateto","request_state"})
    @Timed
    public ResponseEntity<List<Request_taxpayer_account>> getAllRequest_taxpayer_accounts(@RequestParam(value = "datefrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datefrom,
                                                                                          @RequestParam(value = "dateto") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateto,
                                                                                          @RequestParam(value = "request_state") Integer request_state,
                                                                                          Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Request_taxpayer_accounts");
        if(datefrom.toString().compareTo("0001-01-01") == 0 &&
            dateto.toString().compareTo("0001-01-01") == 0 && request_state == -1) {
            ZonedDateTime from = datefrom.atStartOfDay(ZoneId.systemDefault());
            ZonedDateTime to = ZonedDateTime.now();

            Page<Request_taxpayer_account> page = request_taxpayer_accountService.findByDaterequestBetweenOrderByIdDesc(from, to, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/request-taxpayer-accounts");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

        }else
        {
            ZonedDateTime from = datefrom.atStartOfDay(ZoneId.systemDefault());
            ZonedDateTime to = ZonedDateTime.now();
            if(dateto.toString().compareTo("0001-01-01") != 0) {
                dateto = dateto.plusDays(1);
                to = dateto.atStartOfDay(ZoneId.systemDefault());
            }
            if(from.isAfter(to)){
                to = from;
            }
            Request_state request_state1 = null;
            if(request_state != -1) {
                request_state1 = request_stateService.findOne(new Long(request_state.toString()));
            }
            Page<Request_taxpayer_account> page = request_taxpayer_accountService.findByDaterequestBetweenAndRequest_StateOrderByIdDesc(from, to, request_state1, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/request-taxpayer-accounts");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /request-taxpayer-accounts/:id : get the "id" request_taxpayer_account.
     *
     * @param id the id of the request_taxpayer_account to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the request_taxpayer_account, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/request-taxpayer-accounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Request_taxpayer_account> getRequest_taxpayer_account(@PathVariable Long id) {
        log.debug("REST request to get Request_taxpayer_account : {}", id);
        Request_taxpayer_account request_taxpayer_account = request_taxpayer_accountService.findOne(id);
        return Optional.ofNullable(request_taxpayer_account)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /request-taxpayer-accounts/:id : delete the "id" request_taxpayer_account.
     *
     * @param id the id of the request_taxpayer_account to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/request-taxpayer-accounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRequest_taxpayer_account(@PathVariable Long id) {
        log.debug("REST request to delete Request_taxpayer_account : {}", id);
        request_taxpayer_accountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("request_taxpayer_account", id.toString())).build();
    }

}
