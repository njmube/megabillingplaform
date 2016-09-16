package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Taxpayer_mail_accounts;
import org.megapractical.billingplatform.service.Taxpayer_mail_accountsService;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Taxpayer_mail_accounts.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_mail_accountsResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_mail_accountsResource.class);
        
    @Inject
    private Taxpayer_mail_accountsService taxpayer_mail_accountsService;
    
    /**
     * POST  /taxpayer-mail-accounts : Create a new taxpayer_mail_accounts.
     *
     * @param taxpayer_mail_accounts the taxpayer_mail_accounts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_mail_accounts, or with status 400 (Bad Request) if the taxpayer_mail_accounts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-mail-accounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_mail_accounts> createTaxpayer_mail_accounts(@Valid @RequestBody Taxpayer_mail_accounts taxpayer_mail_accounts) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_mail_accounts : {}", taxpayer_mail_accounts);
        if (taxpayer_mail_accounts.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_mail_accounts", "idexists", "A new taxpayer_mail_accounts cannot already have an ID")).body(null);
        }
        Taxpayer_mail_accounts result = taxpayer_mail_accountsService.save(taxpayer_mail_accounts);
        return ResponseEntity.created(new URI("/api/taxpayer-mail-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_mail_accounts", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-mail-accounts : Updates an existing taxpayer_mail_accounts.
     *
     * @param taxpayer_mail_accounts the taxpayer_mail_accounts to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_mail_accounts,
     * or with status 400 (Bad Request) if the taxpayer_mail_accounts is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_mail_accounts couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-mail-accounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_mail_accounts> updateTaxpayer_mail_accounts(@Valid @RequestBody Taxpayer_mail_accounts taxpayer_mail_accounts) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_mail_accounts : {}", taxpayer_mail_accounts);
        if (taxpayer_mail_accounts.getId() == null) {
            return createTaxpayer_mail_accounts(taxpayer_mail_accounts);
        }
        Taxpayer_mail_accounts result = taxpayer_mail_accountsService.save(taxpayer_mail_accounts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_mail_accounts", taxpayer_mail_accounts.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-mail-accounts : get all the taxpayer_mail_accounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_mail_accounts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-mail-accounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Taxpayer_mail_accounts>> getAllTaxpayer_mail_accounts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_mail_accounts");
        Page<Taxpayer_mail_accounts> page = taxpayer_mail_accountsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-mail-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-mail-accounts/:id : get the "id" taxpayer_mail_accounts.
     *
     * @param id the id of the taxpayer_mail_accounts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_mail_accounts, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-mail-accounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_mail_accounts> getTaxpayer_mail_accounts(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_mail_accounts : {}", id);
        Taxpayer_mail_accounts taxpayer_mail_accounts = taxpayer_mail_accountsService.findOne(id);
        return Optional.ofNullable(taxpayer_mail_accounts)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-mail-accounts/:id : delete the "id" taxpayer_mail_accounts.
     *
     * @param id the id of the taxpayer_mail_accounts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-mail-accounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_mail_accounts(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_mail_accounts : {}", id);
        taxpayer_mail_accountsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_mail_accounts", id.toString())).build();
    }

}
