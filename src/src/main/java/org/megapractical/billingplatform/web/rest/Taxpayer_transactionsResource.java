package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Taxpayer_transactions;
import org.megapractical.billingplatform.service.Taxpayer_transactionsService;
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
 * REST controller for managing Taxpayer_transactions.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_transactionsResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_transactionsResource.class);

    @Inject
    private Taxpayer_transactionsService taxpayer_transactionsService;

    /**
     * POST  /taxpayer-transactions : Create a new taxpayer_transactions.
     *
     * @param taxpayer_transactions the taxpayer_transactions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_transactions, or with status 400 (Bad Request) if the taxpayer_transactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-transactions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_transactions> createTaxpayer_transactions(@Valid @RequestBody Taxpayer_transactions taxpayer_transactions) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_transactions : {}", taxpayer_transactions);
        if (taxpayer_transactions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_transactions", "idexists", "A new taxpayer_transactions cannot already have an ID")).body(null);
        }
        Taxpayer_transactions result = taxpayer_transactionsService.save(taxpayer_transactions);
        return ResponseEntity.created(new URI("/api/taxpayer-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_transactions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-transactions : Updates an existing taxpayer_transactions.
     *
     * @param taxpayer_transactions the taxpayer_transactions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_transactions,
     * or with status 400 (Bad Request) if the taxpayer_transactions is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_transactions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-transactions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_transactions> updateTaxpayer_transactions(@Valid @RequestBody Taxpayer_transactions taxpayer_transactions) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_transactions : {}", taxpayer_transactions);
        if (taxpayer_transactions.getId() == null) {
            return createTaxpayer_transactions(taxpayer_transactions);
        }
        Taxpayer_transactions result = taxpayer_transactionsService.save(taxpayer_transactions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_transactions", taxpayer_transactions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-transactions : get all the taxpayer_transactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_transactions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-transactions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"idaccount"})
    @Timed
    public ResponseEntity<List<Taxpayer_transactions>> getAllTaxpayer_transactions(@RequestParam(value = "idaccount") Integer idaccount,
                                                                                   Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_transactions");
        if(idaccount == 0) {
            Page<Taxpayer_transactions> page = taxpayer_transactionsService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-transactions");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else{
            Page<Taxpayer_transactions> page = taxpayer_transactionsService.findByAccount(idaccount,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-transactions");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /taxpayer-transactions/:id : get the "id" taxpayer_transactions.
     *
     * @param id the id of the taxpayer_transactions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_transactions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-transactions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_transactions> getTaxpayer_transactions(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_transactions : {}", id);
        Taxpayer_transactions taxpayer_transactions = taxpayer_transactionsService.findOne(id);
        return Optional.ofNullable(taxpayer_transactions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-transactions/:id : delete the "id" taxpayer_transactions.
     *
     * @param id the id of the taxpayer_transactions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-transactions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_transactions(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_transactions : {}", id);
        taxpayer_transactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_transactions", id.toString())).build();
    }

}
