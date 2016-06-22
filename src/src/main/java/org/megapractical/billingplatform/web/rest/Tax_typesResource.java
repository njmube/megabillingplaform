package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tax_types;
import org.megapractical.billingplatform.service.Tax_typesService;
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
 * REST controller for managing Tax_types.
 */
@RestController
@RequestMapping("/api")
public class Tax_typesResource {

    private final Logger log = LoggerFactory.getLogger(Tax_typesResource.class);
        
    @Inject
    private Tax_typesService tax_typesService;
    
    /**
     * POST  /tax-types : Create a new tax_types.
     *
     * @param tax_types the tax_types to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tax_types, or with status 400 (Bad Request) if the tax_types has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_types> createTax_types(@Valid @RequestBody Tax_types tax_types) throws URISyntaxException {
        log.debug("REST request to save Tax_types : {}", tax_types);
        if (tax_types.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tax_types", "idexists", "A new tax_types cannot already have an ID")).body(null);
        }
        Tax_types result = tax_typesService.save(tax_types);
        return ResponseEntity.created(new URI("/api/tax-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax_types", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-types : Updates an existing tax_types.
     *
     * @param tax_types the tax_types to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tax_types,
     * or with status 400 (Bad Request) if the tax_types is not valid,
     * or with status 500 (Internal Server Error) if the tax_types couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_types> updateTax_types(@Valid @RequestBody Tax_types tax_types) throws URISyntaxException {
        log.debug("REST request to update Tax_types : {}", tax_types);
        if (tax_types.getId() == null) {
            return createTax_types(tax_types);
        }
        Tax_types result = tax_typesService.save(tax_types);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax_types", tax_types.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-types : get all the tax_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tax_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tax-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tax_types>> getAllTax_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tax_types");
        Page<Tax_types> page = tax_typesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tax-types/:id : get the "id" tax_types.
     *
     * @param id the id of the tax_types to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tax_types, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tax-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_types> getTax_types(@PathVariable Long id) {
        log.debug("REST request to get Tax_types : {}", id);
        Tax_types tax_types = tax_typesService.findOne(id);
        return Optional.ofNullable(tax_types)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tax-types/:id : delete the "id" tax_types.
     *
     * @param id the id of the tax_types to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tax-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax_types(@PathVariable Long id) {
        log.debug("REST request to delete Tax_types : {}", id);
        tax_typesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax_types", id.toString())).build();
    }

}
