package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tax_retentions;
import org.megapractical.billingplatform.service.Tax_retentionsService;
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
 * REST controller for managing Tax_retentions.
 */
@RestController
@RequestMapping("/api")
public class Tax_retentionsResource {

    private final Logger log = LoggerFactory.getLogger(Tax_retentionsResource.class);
        
    @Inject
    private Tax_retentionsService tax_retentionsService;
    
    /**
     * POST  /tax-retentions : Create a new tax_retentions.
     *
     * @param tax_retentions the tax_retentions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tax_retentions, or with status 400 (Bad Request) if the tax_retentions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-retentions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_retentions> createTax_retentions(@Valid @RequestBody Tax_retentions tax_retentions) throws URISyntaxException {
        log.debug("REST request to save Tax_retentions : {}", tax_retentions);
        if (tax_retentions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tax_retentions", "idexists", "A new tax_retentions cannot already have an ID")).body(null);
        }
        Tax_retentions result = tax_retentionsService.save(tax_retentions);
        return ResponseEntity.created(new URI("/api/tax-retentions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax_retentions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-retentions : Updates an existing tax_retentions.
     *
     * @param tax_retentions the tax_retentions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tax_retentions,
     * or with status 400 (Bad Request) if the tax_retentions is not valid,
     * or with status 500 (Internal Server Error) if the tax_retentions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-retentions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_retentions> updateTax_retentions(@Valid @RequestBody Tax_retentions tax_retentions) throws URISyntaxException {
        log.debug("REST request to update Tax_retentions : {}", tax_retentions);
        if (tax_retentions.getId() == null) {
            return createTax_retentions(tax_retentions);
        }
        Tax_retentions result = tax_retentionsService.save(tax_retentions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax_retentions", tax_retentions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-retentions : get all the tax_retentions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tax_retentions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tax-retentions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tax_retentions>> getAllTax_retentions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tax_retentions");
        Page<Tax_retentions> page = tax_retentionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-retentions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tax-retentions/:id : get the "id" tax_retentions.
     *
     * @param id the id of the tax_retentions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tax_retentions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tax-retentions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_retentions> getTax_retentions(@PathVariable Long id) {
        log.debug("REST request to get Tax_retentions : {}", id);
        Tax_retentions tax_retentions = tax_retentionsService.findOne(id);
        return Optional.ofNullable(tax_retentions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tax-retentions/:id : delete the "id" tax_retentions.
     *
     * @param id the id of the tax_retentions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tax-retentions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax_retentions(@PathVariable Long id) {
        log.debug("REST request to delete Tax_retentions : {}", id);
        tax_retentionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax_retentions", id.toString())).build();
    }

}
