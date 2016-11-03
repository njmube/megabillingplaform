package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_charge;
import org.megapractical.billingplatform.service.Com_chargeService;
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
 * REST controller for managing Com_charge.
 */
@RestController
@RequestMapping("/api")
public class Com_chargeResource {

    private final Logger log = LoggerFactory.getLogger(Com_chargeResource.class);
        
    @Inject
    private Com_chargeService com_chargeService;
    
    /**
     * POST  /com-charges : Create a new com_charge.
     *
     * @param com_charge the com_charge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_charge, or with status 400 (Bad Request) if the com_charge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-charges",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_charge> createCom_charge(@Valid @RequestBody Com_charge com_charge) throws URISyntaxException {
        log.debug("REST request to save Com_charge : {}", com_charge);
        if (com_charge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_charge", "idexists", "A new com_charge cannot already have an ID")).body(null);
        }
        Com_charge result = com_chargeService.save(com_charge);
        return ResponseEntity.created(new URI("/api/com-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_charge", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-charges : Updates an existing com_charge.
     *
     * @param com_charge the com_charge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_charge,
     * or with status 400 (Bad Request) if the com_charge is not valid,
     * or with status 500 (Internal Server Error) if the com_charge couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-charges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_charge> updateCom_charge(@Valid @RequestBody Com_charge com_charge) throws URISyntaxException {
        log.debug("REST request to update Com_charge : {}", com_charge);
        if (com_charge.getId() == null) {
            return createCom_charge(com_charge);
        }
        Com_charge result = com_chargeService.save(com_charge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_charge", com_charge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-charges : get all the com_charges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_charges in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-charges",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_charge>> getAllCom_charges(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_charges");
        Page<Com_charge> page = com_chargeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-charges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-charges/:id : get the "id" com_charge.
     *
     * @param id the id of the com_charge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_charge, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-charges/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_charge> getCom_charge(@PathVariable Long id) {
        log.debug("REST request to get Com_charge : {}", id);
        Com_charge com_charge = com_chargeService.findOne(id);
        return Optional.ofNullable(com_charge)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-charges/:id : delete the "id" com_charge.
     *
     * @param id the id of the com_charge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-charges/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_charge(@PathVariable Long id) {
        log.debug("REST request to delete Com_charge : {}", id);
        com_chargeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_charge", id.toString())).build();
    }

}
