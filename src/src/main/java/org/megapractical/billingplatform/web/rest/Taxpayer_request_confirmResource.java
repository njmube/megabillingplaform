package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Taxpayer_request_confirm;
import org.megapractical.billingplatform.service.Taxpayer_request_confirmService;
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
 * REST controller for managing Taxpayer_request_confirm.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_request_confirmResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_request_confirmResource.class);
        
    @Inject
    private Taxpayer_request_confirmService taxpayer_request_confirmService;
    
    /**
     * POST  /taxpayer-request-confirms : Create a new taxpayer_request_confirm.
     *
     * @param taxpayer_request_confirm the taxpayer_request_confirm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_request_confirm, or with status 400 (Bad Request) if the taxpayer_request_confirm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-request-confirms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_request_confirm> createTaxpayer_request_confirm(@Valid @RequestBody Taxpayer_request_confirm taxpayer_request_confirm) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_request_confirm : {}", taxpayer_request_confirm);
        if (taxpayer_request_confirm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_request_confirm", "idexists", "A new taxpayer_request_confirm cannot already have an ID")).body(null);
        }
        Taxpayer_request_confirm result = taxpayer_request_confirmService.save(taxpayer_request_confirm);
        return ResponseEntity.created(new URI("/api/taxpayer-request-confirms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_request_confirm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-request-confirms : Updates an existing taxpayer_request_confirm.
     *
     * @param taxpayer_request_confirm the taxpayer_request_confirm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_request_confirm,
     * or with status 400 (Bad Request) if the taxpayer_request_confirm is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_request_confirm couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-request-confirms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_request_confirm> updateTaxpayer_request_confirm(@Valid @RequestBody Taxpayer_request_confirm taxpayer_request_confirm) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_request_confirm : {}", taxpayer_request_confirm);
        if (taxpayer_request_confirm.getId() == null) {
            return createTaxpayer_request_confirm(taxpayer_request_confirm);
        }
        Taxpayer_request_confirm result = taxpayer_request_confirmService.save(taxpayer_request_confirm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_request_confirm", taxpayer_request_confirm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-request-confirms : get all the taxpayer_request_confirms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_request_confirms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-request-confirms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Taxpayer_request_confirm>> getAllTaxpayer_request_confirms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_request_confirms");
        Page<Taxpayer_request_confirm> page = taxpayer_request_confirmService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-request-confirms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-request-confirms/:id : get the "id" taxpayer_request_confirm.
     *
     * @param id the id of the taxpayer_request_confirm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_request_confirm, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-request-confirms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_request_confirm> getTaxpayer_request_confirm(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_request_confirm : {}", id);
        Taxpayer_request_confirm taxpayer_request_confirm = taxpayer_request_confirmService.findOne(id);
        return Optional.ofNullable(taxpayer_request_confirm)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-request-confirms/:id : delete the "id" taxpayer_request_confirm.
     *
     * @param id the id of the taxpayer_request_confirm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-request-confirms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_request_confirm(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_request_confirm : {}", id);
        taxpayer_request_confirmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_request_confirm", id.toString())).build();
    }

}
