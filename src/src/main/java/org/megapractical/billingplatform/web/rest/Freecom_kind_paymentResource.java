package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_kind_payment;
import org.megapractical.billingplatform.service.Freecom_kind_paymentService;
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
 * REST controller for managing Freecom_kind_payment.
 */
@RestController
@RequestMapping("/api")
public class Freecom_kind_paymentResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_kind_paymentResource.class);
        
    @Inject
    private Freecom_kind_paymentService freecom_kind_paymentService;
    
    /**
     * POST  /freecom-kind-payments : Create a new freecom_kind_payment.
     *
     * @param freecom_kind_payment the freecom_kind_payment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_kind_payment, or with status 400 (Bad Request) if the freecom_kind_payment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-kind-payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_kind_payment> createFreecom_kind_payment(@Valid @RequestBody Freecom_kind_payment freecom_kind_payment) throws URISyntaxException {
        log.debug("REST request to save Freecom_kind_payment : {}", freecom_kind_payment);
        if (freecom_kind_payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_kind_payment", "idexists", "A new freecom_kind_payment cannot already have an ID")).body(null);
        }
        Freecom_kind_payment result = freecom_kind_paymentService.save(freecom_kind_payment);
        return ResponseEntity.created(new URI("/api/freecom-kind-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_kind_payment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-kind-payments : Updates an existing freecom_kind_payment.
     *
     * @param freecom_kind_payment the freecom_kind_payment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_kind_payment,
     * or with status 400 (Bad Request) if the freecom_kind_payment is not valid,
     * or with status 500 (Internal Server Error) if the freecom_kind_payment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-kind-payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_kind_payment> updateFreecom_kind_payment(@Valid @RequestBody Freecom_kind_payment freecom_kind_payment) throws URISyntaxException {
        log.debug("REST request to update Freecom_kind_payment : {}", freecom_kind_payment);
        if (freecom_kind_payment.getId() == null) {
            return createFreecom_kind_payment(freecom_kind_payment);
        }
        Freecom_kind_payment result = freecom_kind_paymentService.save(freecom_kind_payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_kind_payment", freecom_kind_payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-kind-payments : get all the freecom_kind_payments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_kind_payments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-kind-payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_kind_payment>> getAllFreecom_kind_payments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_kind_payments");
        Page<Freecom_kind_payment> page = freecom_kind_paymentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-kind-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-kind-payments/:id : get the "id" freecom_kind_payment.
     *
     * @param id the id of the freecom_kind_payment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_kind_payment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-kind-payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_kind_payment> getFreecom_kind_payment(@PathVariable Long id) {
        log.debug("REST request to get Freecom_kind_payment : {}", id);
        Freecom_kind_payment freecom_kind_payment = freecom_kind_paymentService.findOne(id);
        return Optional.ofNullable(freecom_kind_payment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-kind-payments/:id : delete the "id" freecom_kind_payment.
     *
     * @param id the id of the freecom_kind_payment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-kind-payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_kind_payment(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_kind_payment : {}", id);
        freecom_kind_paymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_kind_payment", id.toString())).build();
    }

}
