package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Billing_account_state;
import org.megapractical.billingplatform.service.Billing_account_stateService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Billing_account_state.
 */
@RestController
@RequestMapping("/api")
public class Billing_account_stateResource {

    private final Logger log = LoggerFactory.getLogger(Billing_account_stateResource.class);
        
    @Inject
    private Billing_account_stateService billing_account_stateService;
    
    /**
     * POST  /billing-account-states : Create a new billing_account_state.
     *
     * @param billing_account_state the billing_account_state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billing_account_state, or with status 400 (Bad Request) if the billing_account_state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/billing-account-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Billing_account_state> createBilling_account_state(@RequestBody Billing_account_state billing_account_state) throws URISyntaxException {
        log.debug("REST request to save Billing_account_state : {}", billing_account_state);
        if (billing_account_state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("billing_account_state", "idexists", "A new billing_account_state cannot already have an ID")).body(null);
        }
        Billing_account_state result = billing_account_stateService.save(billing_account_state);
        return ResponseEntity.created(new URI("/api/billing-account-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("billing_account_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-account-states : Updates an existing billing_account_state.
     *
     * @param billing_account_state the billing_account_state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billing_account_state,
     * or with status 400 (Bad Request) if the billing_account_state is not valid,
     * or with status 500 (Internal Server Error) if the billing_account_state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/billing-account-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Billing_account_state> updateBilling_account_state(@RequestBody Billing_account_state billing_account_state) throws URISyntaxException {
        log.debug("REST request to update Billing_account_state : {}", billing_account_state);
        if (billing_account_state.getId() == null) {
            return createBilling_account_state(billing_account_state);
        }
        Billing_account_state result = billing_account_stateService.save(billing_account_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("billing_account_state", billing_account_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-account-states : get all the billing_account_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of billing_account_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/billing-account-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Billing_account_state>> getAllBilling_account_states(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Billing_account_states");
        Page<Billing_account_state> page = billing_account_stateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/billing-account-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /billing-account-states/:id : get the "id" billing_account_state.
     *
     * @param id the id of the billing_account_state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billing_account_state, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/billing-account-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Billing_account_state> getBilling_account_state(@PathVariable Long id) {
        log.debug("REST request to get Billing_account_state : {}", id);
        Billing_account_state billing_account_state = billing_account_stateService.findOne(id);
        return Optional.ofNullable(billing_account_state)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /billing-account-states/:id : delete the "id" billing_account_state.
     *
     * @param id the id of the billing_account_state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/billing-account-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBilling_account_state(@PathVariable Long id) {
        log.debug("REST request to delete Billing_account_state : {}", id);
        billing_account_stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("billing_account_state", id.toString())).build();
    }

}
