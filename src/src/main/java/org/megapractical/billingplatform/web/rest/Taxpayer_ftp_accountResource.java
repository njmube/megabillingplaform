package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Taxpayer_ftp_account;
import org.megapractical.billingplatform.service.Taxpayer_ftp_accountService;
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
 * REST controller for managing Taxpayer_ftp_account.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_ftp_accountResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_ftp_accountResource.class);
        
    @Inject
    private Taxpayer_ftp_accountService taxpayer_ftp_accountService;
    
    /**
     * POST  /taxpayer-ftp-accounts : Create a new taxpayer_ftp_account.
     *
     * @param taxpayer_ftp_account the taxpayer_ftp_account to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_ftp_account, or with status 400 (Bad Request) if the taxpayer_ftp_account has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-ftp-accounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_ftp_account> createTaxpayer_ftp_account(@Valid @RequestBody Taxpayer_ftp_account taxpayer_ftp_account) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_ftp_account : {}", taxpayer_ftp_account);
        if (taxpayer_ftp_account.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_ftp_account", "idexists", "A new taxpayer_ftp_account cannot already have an ID")).body(null);
        }
        Taxpayer_ftp_account result = taxpayer_ftp_accountService.save(taxpayer_ftp_account);
        return ResponseEntity.created(new URI("/api/taxpayer-ftp-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_ftp_account", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-ftp-accounts : Updates an existing taxpayer_ftp_account.
     *
     * @param taxpayer_ftp_account the taxpayer_ftp_account to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_ftp_account,
     * or with status 400 (Bad Request) if the taxpayer_ftp_account is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_ftp_account couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-ftp-accounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_ftp_account> updateTaxpayer_ftp_account(@Valid @RequestBody Taxpayer_ftp_account taxpayer_ftp_account) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_ftp_account : {}", taxpayer_ftp_account);
        if (taxpayer_ftp_account.getId() == null) {
            return createTaxpayer_ftp_account(taxpayer_ftp_account);
        }
        Taxpayer_ftp_account result = taxpayer_ftp_accountService.save(taxpayer_ftp_account);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_ftp_account", taxpayer_ftp_account.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-ftp-accounts : get all the taxpayer_ftp_accounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_ftp_accounts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-ftp-accounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Taxpayer_ftp_account>> getAllTaxpayer_ftp_accounts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_ftp_accounts");
        Page<Taxpayer_ftp_account> page = taxpayer_ftp_accountService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-ftp-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-ftp-accounts/:id : get the "id" taxpayer_ftp_account.
     *
     * @param id the id of the taxpayer_ftp_account to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_ftp_account, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-ftp-accounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_ftp_account> getTaxpayer_ftp_account(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_ftp_account : {}", id);
        Taxpayer_ftp_account taxpayer_ftp_account = taxpayer_ftp_accountService.findOne(id);
        return Optional.ofNullable(taxpayer_ftp_account)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-ftp-accounts/:id : delete the "id" taxpayer_ftp_account.
     *
     * @param id the id of the taxpayer_ftp_account to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-ftp-accounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_ftp_account(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_ftp_account : {}", id);
        taxpayer_ftp_accountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_ftp_account", id.toString())).build();
    }

}
