package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_kind_payment;
import org.megapractical.billingplatform.service.Com_kind_paymentService;
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
 * REST controller for managing Com_kind_payment.
 */
@RestController
@RequestMapping("/api")
public class Com_kind_paymentResource {

    private final Logger log = LoggerFactory.getLogger(Com_kind_paymentResource.class);
        
    @Inject
    private Com_kind_paymentService com_kind_paymentService;
    
    /**
     * POST  /com-kind-payments : Create a new com_kind_payment.
     *
     * @param com_kind_payment the com_kind_payment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_kind_payment, or with status 400 (Bad Request) if the com_kind_payment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-kind-payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_kind_payment> createCom_kind_payment(@Valid @RequestBody Com_kind_payment com_kind_payment) throws URISyntaxException {
        log.debug("REST request to save Com_kind_payment : {}", com_kind_payment);
        if (com_kind_payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_kind_payment", "idexists", "A new com_kind_payment cannot already have an ID")).body(null);
        }
        Com_kind_payment result = com_kind_paymentService.save(com_kind_payment);
        return ResponseEntity.created(new URI("/api/com-kind-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_kind_payment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-kind-payments : Updates an existing com_kind_payment.
     *
     * @param com_kind_payment the com_kind_payment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_kind_payment,
     * or with status 400 (Bad Request) if the com_kind_payment is not valid,
     * or with status 500 (Internal Server Error) if the com_kind_payment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-kind-payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_kind_payment> updateCom_kind_payment(@Valid @RequestBody Com_kind_payment com_kind_payment) throws URISyntaxException {
        log.debug("REST request to update Com_kind_payment : {}", com_kind_payment);
        if (com_kind_payment.getId() == null) {
            return createCom_kind_payment(com_kind_payment);
        }
        Com_kind_payment result = com_kind_paymentService.save(com_kind_payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_kind_payment", com_kind_payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-kind-payments : get all the com_kind_payments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_kind_payments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-kind-payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_kind_payment>> getAllCom_kind_payments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_kind_payments");
        Page<Com_kind_payment> page = com_kind_paymentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-kind-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-kind-payments/:id : get the "id" com_kind_payment.
     *
     * @param id the id of the com_kind_payment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_kind_payment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-kind-payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_kind_payment> getCom_kind_payment(@PathVariable Long id) {
        log.debug("REST request to get Com_kind_payment : {}", id);
        Com_kind_payment com_kind_payment = com_kind_paymentService.findOne(id);
        return Optional.ofNullable(com_kind_payment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-kind-payments/:id : delete the "id" com_kind_payment.
     *
     * @param id the id of the com_kind_payment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-kind-payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_kind_payment(@PathVariable Long id) {
        log.debug("REST request to delete Com_kind_payment : {}", id);
        com_kind_paymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_kind_payment", id.toString())).build();
    }

}
