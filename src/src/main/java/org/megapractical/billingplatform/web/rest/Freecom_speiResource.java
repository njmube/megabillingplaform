package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_spei;
import org.megapractical.billingplatform.service.Freecom_speiService;
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
 * REST controller for managing Freecom_spei.
 */
@RestController
@RequestMapping("/api")
public class Freecom_speiResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_speiResource.class);
        
    @Inject
    private Freecom_speiService freecom_speiService;
    
    /**
     * POST  /freecom-speis : Create a new freecom_spei.
     *
     * @param freecom_spei the freecom_spei to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_spei, or with status 400 (Bad Request) if the freecom_spei has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-speis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_spei> createFreecom_spei(@Valid @RequestBody Freecom_spei freecom_spei) throws URISyntaxException {
        log.debug("REST request to save Freecom_spei : {}", freecom_spei);
        if (freecom_spei.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_spei", "idexists", "A new freecom_spei cannot already have an ID")).body(null);
        }
        Freecom_spei result = freecom_speiService.save(freecom_spei);
        return ResponseEntity.created(new URI("/api/freecom-speis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_spei", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-speis : Updates an existing freecom_spei.
     *
     * @param freecom_spei the freecom_spei to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_spei,
     * or with status 400 (Bad Request) if the freecom_spei is not valid,
     * or with status 500 (Internal Server Error) if the freecom_spei couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-speis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_spei> updateFreecom_spei(@Valid @RequestBody Freecom_spei freecom_spei) throws URISyntaxException {
        log.debug("REST request to update Freecom_spei : {}", freecom_spei);
        if (freecom_spei.getId() == null) {
            return createFreecom_spei(freecom_spei);
        }
        Freecom_spei result = freecom_speiService.save(freecom_spei);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_spei", freecom_spei.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-speis : get all the freecom_speis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_speis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-speis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_spei>> getAllFreecom_speis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_speis");
        Page<Freecom_spei> page = freecom_speiService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-speis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-speis/:id : get the "id" freecom_spei.
     *
     * @param id the id of the freecom_spei to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_spei, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-speis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_spei> getFreecom_spei(@PathVariable Long id) {
        log.debug("REST request to get Freecom_spei : {}", id);
        Freecom_spei freecom_spei = freecom_speiService.findOne(id);
        return Optional.ofNullable(freecom_spei)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-speis/:id : delete the "id" freecom_spei.
     *
     * @param id the id of the freecom_spei to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-speis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_spei(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_spei : {}", id);
        freecom_speiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_spei", id.toString())).build();
    }

}
