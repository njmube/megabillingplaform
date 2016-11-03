package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_payer;
import org.megapractical.billingplatform.service.Com_payerService;
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
 * REST controller for managing Com_payer.
 */
@RestController
@RequestMapping("/api")
public class Com_payerResource {

    private final Logger log = LoggerFactory.getLogger(Com_payerResource.class);
        
    @Inject
    private Com_payerService com_payerService;
    
    /**
     * POST  /com-payers : Create a new com_payer.
     *
     * @param com_payer the com_payer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_payer, or with status 400 (Bad Request) if the com_payer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-payers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_payer> createCom_payer(@Valid @RequestBody Com_payer com_payer) throws URISyntaxException {
        log.debug("REST request to save Com_payer : {}", com_payer);
        if (com_payer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_payer", "idexists", "A new com_payer cannot already have an ID")).body(null);
        }
        Com_payer result = com_payerService.save(com_payer);
        return ResponseEntity.created(new URI("/api/com-payers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_payer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-payers : Updates an existing com_payer.
     *
     * @param com_payer the com_payer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_payer,
     * or with status 400 (Bad Request) if the com_payer is not valid,
     * or with status 500 (Internal Server Error) if the com_payer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-payers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_payer> updateCom_payer(@Valid @RequestBody Com_payer com_payer) throws URISyntaxException {
        log.debug("REST request to update Com_payer : {}", com_payer);
        if (com_payer.getId() == null) {
            return createCom_payer(com_payer);
        }
        Com_payer result = com_payerService.save(com_payer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_payer", com_payer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-payers : get all the com_payers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_payers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-payers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_payer>> getAllCom_payers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_payers");
        Page<Com_payer> page = com_payerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-payers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-payers/:id : get the "id" com_payer.
     *
     * @param id the id of the com_payer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_payer, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-payers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_payer> getCom_payer(@PathVariable Long id) {
        log.debug("REST request to get Com_payer : {}", id);
        Com_payer com_payer = com_payerService.findOne(id);
        return Optional.ofNullable(com_payer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-payers/:id : delete the "id" com_payer.
     *
     * @param id the id of the com_payer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-payers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_payer(@PathVariable Long id) {
        log.debug("REST request to delete Com_payer : {}", id);
        com_payerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_payer", id.toString())).build();
    }

}
