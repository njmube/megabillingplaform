package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Type_transaction;
import org.megapractical.billingplatform.service.Type_transactionService;
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
 * REST controller for managing Type_transaction.
 */
@RestController
@RequestMapping("/api")
public class Type_transactionResource {

    private final Logger log = LoggerFactory.getLogger(Type_transactionResource.class);
        
    @Inject
    private Type_transactionService type_transactionService;
    
    /**
     * POST  /type-transactions : Create a new type_transaction.
     *
     * @param type_transaction the type_transaction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new type_transaction, or with status 400 (Bad Request) if the type_transaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/type-transactions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_transaction> createType_transaction(@Valid @RequestBody Type_transaction type_transaction) throws URISyntaxException {
        log.debug("REST request to save Type_transaction : {}", type_transaction);
        if (type_transaction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("type_transaction", "idexists", "A new type_transaction cannot already have an ID")).body(null);
        }
        Type_transaction result = type_transactionService.save(type_transaction);
        return ResponseEntity.created(new URI("/api/type-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("type_transaction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-transactions : Updates an existing type_transaction.
     *
     * @param type_transaction the type_transaction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated type_transaction,
     * or with status 400 (Bad Request) if the type_transaction is not valid,
     * or with status 500 (Internal Server Error) if the type_transaction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/type-transactions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_transaction> updateType_transaction(@Valid @RequestBody Type_transaction type_transaction) throws URISyntaxException {
        log.debug("REST request to update Type_transaction : {}", type_transaction);
        if (type_transaction.getId() == null) {
            return createType_transaction(type_transaction);
        }
        Type_transaction result = type_transactionService.save(type_transaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("type_transaction", type_transaction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-transactions : get all the type_transactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of type_transactions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/type-transactions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Type_transaction>> getAllType_transactions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Type_transactions");
        Page<Type_transaction> page = type_transactionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-transactions/:id : get the "id" type_transaction.
     *
     * @param id the id of the type_transaction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the type_transaction, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/type-transactions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_transaction> getType_transaction(@PathVariable Long id) {
        log.debug("REST request to get Type_transaction : {}", id);
        Type_transaction type_transaction = type_transactionService.findOne(id);
        return Optional.ofNullable(type_transaction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-transactions/:id : delete the "id" type_transaction.
     *
     * @param id the id of the type_transaction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/type-transactions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteType_transaction(@PathVariable Long id) {
        log.debug("REST request to delete Type_transaction : {}", id);
        type_transactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("type_transaction", id.toString())).build();
    }

}
