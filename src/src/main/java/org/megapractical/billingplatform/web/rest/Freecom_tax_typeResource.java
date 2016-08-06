package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_tax_type;
import org.megapractical.billingplatform.service.Freecom_tax_typeService;
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
 * REST controller for managing Freecom_tax_type.
 */
@RestController
@RequestMapping("/api")
public class Freecom_tax_typeResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_tax_typeResource.class);
        
    @Inject
    private Freecom_tax_typeService freecom_tax_typeService;
    
    /**
     * POST  /freecom-tax-types : Create a new freecom_tax_type.
     *
     * @param freecom_tax_type the freecom_tax_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_tax_type, or with status 400 (Bad Request) if the freecom_tax_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-tax-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tax_type> createFreecom_tax_type(@Valid @RequestBody Freecom_tax_type freecom_tax_type) throws URISyntaxException {
        log.debug("REST request to save Freecom_tax_type : {}", freecom_tax_type);
        if (freecom_tax_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_tax_type", "idexists", "A new freecom_tax_type cannot already have an ID")).body(null);
        }
        Freecom_tax_type result = freecom_tax_typeService.save(freecom_tax_type);
        return ResponseEntity.created(new URI("/api/freecom-tax-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_tax_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-tax-types : Updates an existing freecom_tax_type.
     *
     * @param freecom_tax_type the freecom_tax_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_tax_type,
     * or with status 400 (Bad Request) if the freecom_tax_type is not valid,
     * or with status 500 (Internal Server Error) if the freecom_tax_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-tax-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tax_type> updateFreecom_tax_type(@Valid @RequestBody Freecom_tax_type freecom_tax_type) throws URISyntaxException {
        log.debug("REST request to update Freecom_tax_type : {}", freecom_tax_type);
        if (freecom_tax_type.getId() == null) {
            return createFreecom_tax_type(freecom_tax_type);
        }
        Freecom_tax_type result = freecom_tax_typeService.save(freecom_tax_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_tax_type", freecom_tax_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-tax-types : get all the freecom_tax_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_tax_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-tax-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_tax_type>> getAllFreecom_tax_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_tax_types");
        Page<Freecom_tax_type> page = freecom_tax_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-tax-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-tax-types/:id : get the "id" freecom_tax_type.
     *
     * @param id the id of the freecom_tax_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_tax_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-tax-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tax_type> getFreecom_tax_type(@PathVariable Long id) {
        log.debug("REST request to get Freecom_tax_type : {}", id);
        Freecom_tax_type freecom_tax_type = freecom_tax_typeService.findOne(id);
        return Optional.ofNullable(freecom_tax_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-tax-types/:id : delete the "id" freecom_tax_type.
     *
     * @param id the id of the freecom_tax_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-tax-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_tax_type(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_tax_type : {}", id);
        freecom_tax_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_tax_type", id.toString())).build();
    }

}
