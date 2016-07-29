package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_local_taxes;
import org.megapractical.billingplatform.service.Freecom_local_taxesService;
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
 * REST controller for managing Freecom_local_taxes.
 */
@RestController
@RequestMapping("/api")
public class Freecom_local_taxesResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_local_taxesResource.class);
        
    @Inject
    private Freecom_local_taxesService freecom_local_taxesService;
    
    /**
     * POST  /freecom-local-taxes : Create a new freecom_local_taxes.
     *
     * @param freecom_local_taxes the freecom_local_taxes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_local_taxes, or with status 400 (Bad Request) if the freecom_local_taxes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-local-taxes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_taxes> createFreecom_local_taxes(@Valid @RequestBody Freecom_local_taxes freecom_local_taxes) throws URISyntaxException {
        log.debug("REST request to save Freecom_local_taxes : {}", freecom_local_taxes);
        if (freecom_local_taxes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_local_taxes", "idexists", "A new freecom_local_taxes cannot already have an ID")).body(null);
        }
        Freecom_local_taxes result = freecom_local_taxesService.save(freecom_local_taxes);
        return ResponseEntity.created(new URI("/api/freecom-local-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_local_taxes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-local-taxes : Updates an existing freecom_local_taxes.
     *
     * @param freecom_local_taxes the freecom_local_taxes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_local_taxes,
     * or with status 400 (Bad Request) if the freecom_local_taxes is not valid,
     * or with status 500 (Internal Server Error) if the freecom_local_taxes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-local-taxes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_taxes> updateFreecom_local_taxes(@Valid @RequestBody Freecom_local_taxes freecom_local_taxes) throws URISyntaxException {
        log.debug("REST request to update Freecom_local_taxes : {}", freecom_local_taxes);
        if (freecom_local_taxes.getId() == null) {
            return createFreecom_local_taxes(freecom_local_taxes);
        }
        Freecom_local_taxes result = freecom_local_taxesService.save(freecom_local_taxes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_local_taxes", freecom_local_taxes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-local-taxes : get all the freecom_local_taxes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_local_taxes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-local-taxes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_local_taxes>> getAllFreecom_local_taxes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_local_taxes");
        Page<Freecom_local_taxes> page = freecom_local_taxesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-local-taxes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-local-taxes/:id : get the "id" freecom_local_taxes.
     *
     * @param id the id of the freecom_local_taxes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_local_taxes, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-local-taxes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_taxes> getFreecom_local_taxes(@PathVariable Long id) {
        log.debug("REST request to get Freecom_local_taxes : {}", id);
        Freecom_local_taxes freecom_local_taxes = freecom_local_taxesService.findOne(id);
        return Optional.ofNullable(freecom_local_taxes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-local-taxes/:id : delete the "id" freecom_local_taxes.
     *
     * @param id the id of the freecom_local_taxes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-local-taxes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_local_taxes(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_local_taxes : {}", id);
        freecom_local_taxesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_local_taxes", id.toString())).build();
    }

}
