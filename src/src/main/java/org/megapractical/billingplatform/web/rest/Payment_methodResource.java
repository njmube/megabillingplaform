package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Payment_method;
import org.megapractical.billingplatform.service.Payment_methodService;
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
 * REST controller for managing Payment_method.
 */
@RestController
@RequestMapping("/api")
public class Payment_methodResource {

    private final Logger log = LoggerFactory.getLogger(Payment_methodResource.class);
        
    @Inject
    private Payment_methodService payment_methodService;
    
    /**
     * POST  /payment-methods : Create a new payment_method.
     *
     * @param payment_method the payment_method to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payment_method, or with status 400 (Bad Request) if the payment_method has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/payment-methods",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment_method> createPayment_method(@Valid @RequestBody Payment_method payment_method) throws URISyntaxException {
        log.debug("REST request to save Payment_method : {}", payment_method);
        if (payment_method.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("payment_method", "idexists", "A new payment_method cannot already have an ID")).body(null);
        }
        Payment_method result = payment_methodService.save(payment_method);
        return ResponseEntity.created(new URI("/api/payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("payment_method", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-methods : Updates an existing payment_method.
     *
     * @param payment_method the payment_method to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payment_method,
     * or with status 400 (Bad Request) if the payment_method is not valid,
     * or with status 500 (Internal Server Error) if the payment_method couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/payment-methods",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment_method> updatePayment_method(@Valid @RequestBody Payment_method payment_method) throws URISyntaxException {
        log.debug("REST request to update Payment_method : {}", payment_method);
        if (payment_method.getId() == null) {
            return createPayment_method(payment_method);
        }
        Payment_method result = payment_methodService.save(payment_method);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("payment_method", payment_method.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-methods : get all the payment_methods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of payment_methods in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/payment-methods",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Payment_method>> getAllPayment_methods(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Payment_methods");
        Page<Payment_method> page = payment_methodService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-methods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment-methods/:id : get the "id" payment_method.
     *
     * @param id the id of the payment_method to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payment_method, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/payment-methods/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment_method> getPayment_method(@PathVariable Long id) {
        log.debug("REST request to get Payment_method : {}", id);
        Payment_method payment_method = payment_methodService.findOne(id);
        return Optional.ofNullable(payment_method)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payment-methods/:id : delete the "id" payment_method.
     *
     * @param id the id of the payment_method to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/payment-methods/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePayment_method(@PathVariable Long id) {
        log.debug("REST request to delete Payment_method : {}", id);
        payment_methodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("payment_method", id.toString())).build();
    }

}
