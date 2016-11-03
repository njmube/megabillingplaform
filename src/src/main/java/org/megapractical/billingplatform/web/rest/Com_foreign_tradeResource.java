package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_foreign_trade;
import org.megapractical.billingplatform.service.Com_foreign_tradeService;
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
 * REST controller for managing Com_foreign_trade.
 */
@RestController
@RequestMapping("/api")
public class Com_foreign_tradeResource {

    private final Logger log = LoggerFactory.getLogger(Com_foreign_tradeResource.class);
        
    @Inject
    private Com_foreign_tradeService com_foreign_tradeService;
    
    /**
     * POST  /com-foreign-trades : Create a new com_foreign_trade.
     *
     * @param com_foreign_trade the com_foreign_trade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_foreign_trade, or with status 400 (Bad Request) if the com_foreign_trade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-foreign-trades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_trade> createCom_foreign_trade(@Valid @RequestBody Com_foreign_trade com_foreign_trade) throws URISyntaxException {
        log.debug("REST request to save Com_foreign_trade : {}", com_foreign_trade);
        if (com_foreign_trade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_foreign_trade", "idexists", "A new com_foreign_trade cannot already have an ID")).body(null);
        }
        Com_foreign_trade result = com_foreign_tradeService.save(com_foreign_trade);
        return ResponseEntity.created(new URI("/api/com-foreign-trades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_foreign_trade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-foreign-trades : Updates an existing com_foreign_trade.
     *
     * @param com_foreign_trade the com_foreign_trade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_foreign_trade,
     * or with status 400 (Bad Request) if the com_foreign_trade is not valid,
     * or with status 500 (Internal Server Error) if the com_foreign_trade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-foreign-trades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_trade> updateCom_foreign_trade(@Valid @RequestBody Com_foreign_trade com_foreign_trade) throws URISyntaxException {
        log.debug("REST request to update Com_foreign_trade : {}", com_foreign_trade);
        if (com_foreign_trade.getId() == null) {
            return createCom_foreign_trade(com_foreign_trade);
        }
        Com_foreign_trade result = com_foreign_tradeService.save(com_foreign_trade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_foreign_trade", com_foreign_trade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-foreign-trades : get all the com_foreign_trades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_foreign_trades in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-foreign-trades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_foreign_trade>> getAllCom_foreign_trades(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_foreign_trades");
        Page<Com_foreign_trade> page = com_foreign_tradeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-foreign-trades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-foreign-trades/:id : get the "id" com_foreign_trade.
     *
     * @param id the id of the com_foreign_trade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_foreign_trade, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-foreign-trades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_trade> getCom_foreign_trade(@PathVariable Long id) {
        log.debug("REST request to get Com_foreign_trade : {}", id);
        Com_foreign_trade com_foreign_trade = com_foreign_tradeService.findOne(id);
        return Optional.ofNullable(com_foreign_trade)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-foreign-trades/:id : delete the "id" com_foreign_trade.
     *
     * @param id the id of the com_foreign_trade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-foreign-trades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_foreign_trade(@PathVariable Long id) {
        log.debug("REST request to delete Com_foreign_trade : {}", id);
        com_foreign_tradeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_foreign_trade", id.toString())).build();
    }

}
