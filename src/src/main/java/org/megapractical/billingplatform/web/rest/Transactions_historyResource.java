package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Transactions_history;
import org.megapractical.billingplatform.service.Transactions_historyService;
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
 * REST controller for managing Transactions_history.
 */
@RestController
@RequestMapping("/api")
public class Transactions_historyResource {

    private final Logger log = LoggerFactory.getLogger(Transactions_historyResource.class);
        
    @Inject
    private Transactions_historyService transactions_historyService;
    
    /**
     * POST  /transactions-histories : Create a new transactions_history.
     *
     * @param transactions_history the transactions_history to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactions_history, or with status 400 (Bad Request) if the transactions_history has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/transactions-histories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transactions_history> createTransactions_history(@Valid @RequestBody Transactions_history transactions_history) throws URISyntaxException {
        log.debug("REST request to save Transactions_history : {}", transactions_history);
        if (transactions_history.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("transactions_history", "idexists", "A new transactions_history cannot already have an ID")).body(null);
        }
        Transactions_history result = transactions_historyService.save(transactions_history);
        return ResponseEntity.created(new URI("/api/transactions-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("transactions_history", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transactions-histories : Updates an existing transactions_history.
     *
     * @param transactions_history the transactions_history to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactions_history,
     * or with status 400 (Bad Request) if the transactions_history is not valid,
     * or with status 500 (Internal Server Error) if the transactions_history couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/transactions-histories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transactions_history> updateTransactions_history(@Valid @RequestBody Transactions_history transactions_history) throws URISyntaxException {
        log.debug("REST request to update Transactions_history : {}", transactions_history);
        if (transactions_history.getId() == null) {
            return createTransactions_history(transactions_history);
        }
        Transactions_history result = transactions_historyService.save(transactions_history);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("transactions_history", transactions_history.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transactions-histories : get all the transactions_histories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transactions_histories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/transactions-histories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Transactions_history>> getAllTransactions_histories(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Transactions_histories");
        Page<Transactions_history> page = transactions_historyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transactions-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transactions-histories/:id : get the "id" transactions_history.
     *
     * @param id the id of the transactions_history to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactions_history, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/transactions-histories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transactions_history> getTransactions_history(@PathVariable Long id) {
        log.debug("REST request to get Transactions_history : {}", id);
        Transactions_history transactions_history = transactions_historyService.findOne(id);
        return Optional.ofNullable(transactions_history)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /transactions-histories/:id : delete the "id" transactions_history.
     *
     * @param id the id of the transactions_history to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/transactions-histories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTransactions_history(@PathVariable Long id) {
        log.debug("REST request to delete Transactions_history : {}", id);
        transactions_historyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("transactions_history", id.toString())).build();
    }

}
