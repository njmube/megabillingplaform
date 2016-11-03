package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_foreign_exchange;
import org.megapractical.billingplatform.service.Com_foreign_exchangeService;
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
 * REST controller for managing Com_foreign_exchange.
 */
@RestController
@RequestMapping("/api")
public class Com_foreign_exchangeResource {

    private final Logger log = LoggerFactory.getLogger(Com_foreign_exchangeResource.class);
        
    @Inject
    private Com_foreign_exchangeService com_foreign_exchangeService;
    
    /**
     * POST  /com-foreign-exchanges : Create a new com_foreign_exchange.
     *
     * @param com_foreign_exchange the com_foreign_exchange to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_foreign_exchange, or with status 400 (Bad Request) if the com_foreign_exchange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-foreign-exchanges",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_exchange> createCom_foreign_exchange(@RequestBody Com_foreign_exchange com_foreign_exchange) throws URISyntaxException {
        log.debug("REST request to save Com_foreign_exchange : {}", com_foreign_exchange);
        if (com_foreign_exchange.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_foreign_exchange", "idexists", "A new com_foreign_exchange cannot already have an ID")).body(null);
        }
        Com_foreign_exchange result = com_foreign_exchangeService.save(com_foreign_exchange);
        return ResponseEntity.created(new URI("/api/com-foreign-exchanges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_foreign_exchange", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-foreign-exchanges : Updates an existing com_foreign_exchange.
     *
     * @param com_foreign_exchange the com_foreign_exchange to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_foreign_exchange,
     * or with status 400 (Bad Request) if the com_foreign_exchange is not valid,
     * or with status 500 (Internal Server Error) if the com_foreign_exchange couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-foreign-exchanges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_exchange> updateCom_foreign_exchange(@RequestBody Com_foreign_exchange com_foreign_exchange) throws URISyntaxException {
        log.debug("REST request to update Com_foreign_exchange : {}", com_foreign_exchange);
        if (com_foreign_exchange.getId() == null) {
            return createCom_foreign_exchange(com_foreign_exchange);
        }
        Com_foreign_exchange result = com_foreign_exchangeService.save(com_foreign_exchange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_foreign_exchange", com_foreign_exchange.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-foreign-exchanges : get all the com_foreign_exchanges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_foreign_exchanges in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-foreign-exchanges",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_foreign_exchange>> getAllCom_foreign_exchanges(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_foreign_exchanges");
        Page<Com_foreign_exchange> page = com_foreign_exchangeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-foreign-exchanges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-foreign-exchanges/:id : get the "id" com_foreign_exchange.
     *
     * @param id the id of the com_foreign_exchange to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_foreign_exchange, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-foreign-exchanges/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_exchange> getCom_foreign_exchange(@PathVariable Long id) {
        log.debug("REST request to get Com_foreign_exchange : {}", id);
        Com_foreign_exchange com_foreign_exchange = com_foreign_exchangeService.findOne(id);
        return Optional.ofNullable(com_foreign_exchange)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-foreign-exchanges/:id : delete the "id" com_foreign_exchange.
     *
     * @param id the id of the com_foreign_exchange to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-foreign-exchanges/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_foreign_exchange(@PathVariable Long id) {
        log.debug("REST request to delete Com_foreign_exchange : {}", id);
        com_foreign_exchangeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_foreign_exchange", id.toString())).build();
    }

}
