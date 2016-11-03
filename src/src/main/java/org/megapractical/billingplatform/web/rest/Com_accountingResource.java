package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_accounting;
import org.megapractical.billingplatform.service.Com_accountingService;
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
 * REST controller for managing Com_accounting.
 */
@RestController
@RequestMapping("/api")
public class Com_accountingResource {

    private final Logger log = LoggerFactory.getLogger(Com_accountingResource.class);
        
    @Inject
    private Com_accountingService com_accountingService;
    
    /**
     * POST  /com-accountings : Create a new com_accounting.
     *
     * @param com_accounting the com_accounting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_accounting, or with status 400 (Bad Request) if the com_accounting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-accountings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_accounting> createCom_accounting(@Valid @RequestBody Com_accounting com_accounting) throws URISyntaxException {
        log.debug("REST request to save Com_accounting : {}", com_accounting);
        if (com_accounting.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_accounting", "idexists", "A new com_accounting cannot already have an ID")).body(null);
        }
        Com_accounting result = com_accountingService.save(com_accounting);
        return ResponseEntity.created(new URI("/api/com-accountings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_accounting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-accountings : Updates an existing com_accounting.
     *
     * @param com_accounting the com_accounting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_accounting,
     * or with status 400 (Bad Request) if the com_accounting is not valid,
     * or with status 500 (Internal Server Error) if the com_accounting couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-accountings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_accounting> updateCom_accounting(@Valid @RequestBody Com_accounting com_accounting) throws URISyntaxException {
        log.debug("REST request to update Com_accounting : {}", com_accounting);
        if (com_accounting.getId() == null) {
            return createCom_accounting(com_accounting);
        }
        Com_accounting result = com_accountingService.save(com_accounting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_accounting", com_accounting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-accountings : get all the com_accountings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_accountings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-accountings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_accounting>> getAllCom_accountings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_accountings");
        Page<Com_accounting> page = com_accountingService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-accountings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-accountings/:id : get the "id" com_accounting.
     *
     * @param id the id of the com_accounting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_accounting, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-accountings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_accounting> getCom_accounting(@PathVariable Long id) {
        log.debug("REST request to get Com_accounting : {}", id);
        Com_accounting com_accounting = com_accountingService.findOne(id);
        return Optional.ofNullable(com_accounting)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-accountings/:id : delete the "id" com_accounting.
     *
     * @param id the id of the com_accounting to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-accountings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_accounting(@PathVariable Long id) {
        log.debug("REST request to delete Com_accounting : {}", id);
        com_accountingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_accounting", id.toString())).build();
    }

}
