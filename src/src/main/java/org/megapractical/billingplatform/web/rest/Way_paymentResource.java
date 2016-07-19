package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Way_payment;
import org.megapractical.billingplatform.service.Way_paymentService;
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
 * REST controller for managing Way_payment.
 */
@RestController
@RequestMapping("/api")
public class Way_paymentResource {

    private final Logger log = LoggerFactory.getLogger(Way_paymentResource.class);

    @Inject
    private Way_paymentService way_paymentService;

    /**
     * POST  /way-payments : Create a new way_payment.
     *
     * @param way_payment the way_payment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new way_payment, or with status 400 (Bad Request) if the way_payment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/way-payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Way_payment> createWay_payment(@Valid @RequestBody Way_payment way_payment) throws URISyntaxException {
        log.debug("REST request to save Way_payment : {}", way_payment);
        if (way_payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("way_payment", "idexists", "A new way_payment cannot already have an ID")).body(null);
        }
        Way_payment result = way_paymentService.save(way_payment);
        return ResponseEntity.created(new URI("/api/way-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("way_payment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /way-payments : Updates an existing way_payment.
     *
     * @param way_payment the way_payment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated way_payment,
     * or with status 400 (Bad Request) if the way_payment is not valid,
     * or with status 500 (Internal Server Error) if the way_payment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/way-payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Way_payment> updateWay_payment(@Valid @RequestBody Way_payment way_payment) throws URISyntaxException {
        log.debug("REST request to update Way_payment : {}", way_payment);
        if (way_payment.getId() == null) {
            return createWay_payment(way_payment);
        }
        Way_payment result = way_paymentService.save(way_payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("way_payment", way_payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /way-payments : get all the way_payments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of way_payments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/way-payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"filtername"})
    @Timed
    public ResponseEntity<List<Way_payment>> getAllWay_payments(@RequestParam(value = "filtername") String filtername,
                                                                Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Way_payments");
        if(filtername.compareTo(" ")==0 || filtername.isEmpty()) {
            Page<Way_payment> page = way_paymentService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/way-payments");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else {
            Page<Way_payment> page = way_paymentService.findAllByName(filtername,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/way-payments");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /way-payments/:id : get the "id" way_payment.
     *
     * @param id the id of the way_payment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the way_payment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/way-payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Way_payment> getWay_payment(@PathVariable Long id) {
        log.debug("REST request to get Way_payment : {}", id);
        Way_payment way_payment = way_paymentService.findOne(id);
        return Optional.ofNullable(way_payment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /way-payments/:id : delete the "id" way_payment.
     *
     * @param id the id of the way_payment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/way-payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWay_payment(@PathVariable Long id) {
        log.debug("REST request to delete Way_payment : {}", id);
        way_paymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("way_payment", id.toString())).build();
    }

}
