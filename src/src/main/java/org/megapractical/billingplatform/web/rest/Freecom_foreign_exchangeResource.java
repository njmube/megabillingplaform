package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_foreign_exchange;
import org.megapractical.billingplatform.service.Freecom_foreign_exchangeService;
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
 * REST controller for managing Freecom_foreign_exchange.
 */
@RestController
@RequestMapping("/api")
public class Freecom_foreign_exchangeResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_foreign_exchangeResource.class);
        
    @Inject
    private Freecom_foreign_exchangeService freecom_foreign_exchangeService;
    
    /**
     * POST  /freecom-foreign-exchanges : Create a new freecom_foreign_exchange.
     *
     * @param freecom_foreign_exchange the freecom_foreign_exchange to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_foreign_exchange, or with status 400 (Bad Request) if the freecom_foreign_exchange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-foreign-exchanges",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_exchange> createFreecom_foreign_exchange(@RequestBody Freecom_foreign_exchange freecom_foreign_exchange) throws URISyntaxException {
        log.debug("REST request to save Freecom_foreign_exchange : {}", freecom_foreign_exchange);
        if (freecom_foreign_exchange.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_foreign_exchange", "idexists", "A new freecom_foreign_exchange cannot already have an ID")).body(null);
        }
        Freecom_foreign_exchange result = freecom_foreign_exchangeService.save(freecom_foreign_exchange);
        return ResponseEntity.created(new URI("/api/freecom-foreign-exchanges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_foreign_exchange", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-foreign-exchanges : Updates an existing freecom_foreign_exchange.
     *
     * @param freecom_foreign_exchange the freecom_foreign_exchange to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_foreign_exchange,
     * or with status 400 (Bad Request) if the freecom_foreign_exchange is not valid,
     * or with status 500 (Internal Server Error) if the freecom_foreign_exchange couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-foreign-exchanges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_exchange> updateFreecom_foreign_exchange(@RequestBody Freecom_foreign_exchange freecom_foreign_exchange) throws URISyntaxException {
        log.debug("REST request to update Freecom_foreign_exchange : {}", freecom_foreign_exchange);
        if (freecom_foreign_exchange.getId() == null) {
            return createFreecom_foreign_exchange(freecom_foreign_exchange);
        }
        Freecom_foreign_exchange result = freecom_foreign_exchangeService.save(freecom_foreign_exchange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_foreign_exchange", freecom_foreign_exchange.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-foreign-exchanges : get all the freecom_foreign_exchanges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_foreign_exchanges in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-foreign-exchanges",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_foreign_exchange>> getAllFreecom_foreign_exchanges(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_foreign_exchanges");
        Page<Freecom_foreign_exchange> page = freecom_foreign_exchangeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-foreign-exchanges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-foreign-exchanges/:id : get the "id" freecom_foreign_exchange.
     *
     * @param id the id of the freecom_foreign_exchange to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_foreign_exchange, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-foreign-exchanges/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_exchange> getFreecom_foreign_exchange(@PathVariable Long id) {
        log.debug("REST request to get Freecom_foreign_exchange : {}", id);
        Freecom_foreign_exchange freecom_foreign_exchange = freecom_foreign_exchangeService.findOne(id);
        return Optional.ofNullable(freecom_foreign_exchange)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-foreign-exchanges/:id : delete the "id" freecom_foreign_exchange.
     *
     * @param id the id of the freecom_foreign_exchange to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-foreign-exchanges/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_foreign_exchange(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_foreign_exchange : {}", id);
        freecom_foreign_exchangeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_foreign_exchange", id.toString())).build();
    }

}
