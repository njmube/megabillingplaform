package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tax_transfered;
import org.megapractical.billingplatform.service.Tax_transferedService;
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
 * REST controller for managing Tax_transfered.
 */
@RestController
@RequestMapping("/api")
public class Tax_transferedResource {

    private final Logger log = LoggerFactory.getLogger(Tax_transferedResource.class);
        
    @Inject
    private Tax_transferedService tax_transferedService;
    
    /**
     * POST  /tax-transfereds : Create a new tax_transfered.
     *
     * @param tax_transfered the tax_transfered to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tax_transfered, or with status 400 (Bad Request) if the tax_transfered has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-transfereds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_transfered> createTax_transfered(@Valid @RequestBody Tax_transfered tax_transfered) throws URISyntaxException {
        log.debug("REST request to save Tax_transfered : {}", tax_transfered);
        if (tax_transfered.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tax_transfered", "idexists", "A new tax_transfered cannot already have an ID")).body(null);
        }
        Tax_transfered result = tax_transferedService.save(tax_transfered);
        return ResponseEntity.created(new URI("/api/tax-transfereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax_transfered", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-transfereds : Updates an existing tax_transfered.
     *
     * @param tax_transfered the tax_transfered to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tax_transfered,
     * or with status 400 (Bad Request) if the tax_transfered is not valid,
     * or with status 500 (Internal Server Error) if the tax_transfered couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-transfereds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_transfered> updateTax_transfered(@Valid @RequestBody Tax_transfered tax_transfered) throws URISyntaxException {
        log.debug("REST request to update Tax_transfered : {}", tax_transfered);
        if (tax_transfered.getId() == null) {
            return createTax_transfered(tax_transfered);
        }
        Tax_transfered result = tax_transferedService.save(tax_transfered);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax_transfered", tax_transfered.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-transfereds : get all the tax_transfereds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tax_transfereds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tax-transfereds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tax_transfered>> getAllTax_transfereds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tax_transfereds");
        Page<Tax_transfered> page = tax_transferedService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-transfereds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tax-transfereds/:id : get the "id" tax_transfered.
     *
     * @param id the id of the tax_transfered to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tax_transfered, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tax-transfereds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_transfered> getTax_transfered(@PathVariable Long id) {
        log.debug("REST request to get Tax_transfered : {}", id);
        Tax_transfered tax_transfered = tax_transferedService.findOne(id);
        return Optional.ofNullable(tax_transfered)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tax-transfereds/:id : delete the "id" tax_transfered.
     *
     * @param id the id of the tax_transfered to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tax-transfereds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax_transfered(@PathVariable Long id) {
        log.debug("REST request to delete Tax_transfered : {}", id);
        tax_transferedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax_transfered", id.toString())).build();
    }

}
