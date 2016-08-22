package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_foreign_trade;
import org.megapractical.billingplatform.service.Freecom_foreign_tradeService;
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
 * REST controller for managing Freecom_foreign_trade.
 */
@RestController
@RequestMapping("/api")
public class Freecom_foreign_tradeResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_foreign_tradeResource.class);
        
    @Inject
    private Freecom_foreign_tradeService freecom_foreign_tradeService;
    
    /**
     * POST  /freecom-foreign-trades : Create a new freecom_foreign_trade.
     *
     * @param freecom_foreign_trade the freecom_foreign_trade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_foreign_trade, or with status 400 (Bad Request) if the freecom_foreign_trade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-foreign-trades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_trade> createFreecom_foreign_trade(@Valid @RequestBody Freecom_foreign_trade freecom_foreign_trade) throws URISyntaxException {
        log.debug("REST request to save Freecom_foreign_trade : {}", freecom_foreign_trade);
        if (freecom_foreign_trade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_foreign_trade", "idexists", "A new freecom_foreign_trade cannot already have an ID")).body(null);
        }
        Freecom_foreign_trade result = freecom_foreign_tradeService.save(freecom_foreign_trade);
        return ResponseEntity.created(new URI("/api/freecom-foreign-trades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_foreign_trade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-foreign-trades : Updates an existing freecom_foreign_trade.
     *
     * @param freecom_foreign_trade the freecom_foreign_trade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_foreign_trade,
     * or with status 400 (Bad Request) if the freecom_foreign_trade is not valid,
     * or with status 500 (Internal Server Error) if the freecom_foreign_trade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-foreign-trades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_trade> updateFreecom_foreign_trade(@Valid @RequestBody Freecom_foreign_trade freecom_foreign_trade) throws URISyntaxException {
        log.debug("REST request to update Freecom_foreign_trade : {}", freecom_foreign_trade);
        if (freecom_foreign_trade.getId() == null) {
            return createFreecom_foreign_trade(freecom_foreign_trade);
        }
        Freecom_foreign_trade result = freecom_foreign_tradeService.save(freecom_foreign_trade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_foreign_trade", freecom_foreign_trade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-foreign-trades : get all the freecom_foreign_trades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_foreign_trades in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-foreign-trades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_foreign_trade>> getAllFreecom_foreign_trades(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_foreign_trades");
        Page<Freecom_foreign_trade> page = freecom_foreign_tradeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-foreign-trades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-foreign-trades/:id : get the "id" freecom_foreign_trade.
     *
     * @param id the id of the freecom_foreign_trade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_foreign_trade, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-foreign-trades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_trade> getFreecom_foreign_trade(@PathVariable Long id) {
        log.debug("REST request to get Freecom_foreign_trade : {}", id);
        Freecom_foreign_trade freecom_foreign_trade = freecom_foreign_tradeService.findOne(id);
        return Optional.ofNullable(freecom_foreign_trade)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-foreign-trades/:id : delete the "id" freecom_foreign_trade.
     *
     * @param id the id of the freecom_foreign_trade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-foreign-trades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_foreign_trade(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_foreign_trade : {}", id);
        freecom_foreign_tradeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_foreign_trade", id.toString())).build();
    }

}
