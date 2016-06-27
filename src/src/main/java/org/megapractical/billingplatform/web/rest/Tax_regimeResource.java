package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tax_regime;
import org.megapractical.billingplatform.service.Tax_regimeService;
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
 * REST controller for managing Tax_regime.
 */
@RestController
@RequestMapping("/api")
public class Tax_regimeResource {

    private final Logger log = LoggerFactory.getLogger(Tax_regimeResource.class);
        
    @Inject
    private Tax_regimeService tax_regimeService;
    
    /**
     * POST  /tax-regimes : Create a new tax_regime.
     *
     * @param tax_regime the tax_regime to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tax_regime, or with status 400 (Bad Request) if the tax_regime has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-regimes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_regime> createTax_regime(@Valid @RequestBody Tax_regime tax_regime) throws URISyntaxException {
        log.debug("REST request to save Tax_regime : {}", tax_regime);
        if (tax_regime.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tax_regime", "idexists", "A new tax_regime cannot already have an ID")).body(null);
        }
        Tax_regime result = tax_regimeService.save(tax_regime);
        return ResponseEntity.created(new URI("/api/tax-regimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax_regime", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-regimes : Updates an existing tax_regime.
     *
     * @param tax_regime the tax_regime to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tax_regime,
     * or with status 400 (Bad Request) if the tax_regime is not valid,
     * or with status 500 (Internal Server Error) if the tax_regime couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-regimes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_regime> updateTax_regime(@Valid @RequestBody Tax_regime tax_regime) throws URISyntaxException {
        log.debug("REST request to update Tax_regime : {}", tax_regime);
        if (tax_regime.getId() == null) {
            return createTax_regime(tax_regime);
        }
        Tax_regime result = tax_regimeService.save(tax_regime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax_regime", tax_regime.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-regimes : get all the tax_regimes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tax_regimes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tax-regimes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tax_regime>> getAllTax_regimes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tax_regimes");
        Page<Tax_regime> page = tax_regimeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-regimes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tax-regimes/:id : get the "id" tax_regime.
     *
     * @param id the id of the tax_regime to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tax_regime, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tax-regimes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_regime> getTax_regime(@PathVariable Long id) {
        log.debug("REST request to get Tax_regime : {}", id);
        Tax_regime tax_regime = tax_regimeService.findOne(id);
        return Optional.ofNullable(tax_regime)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tax-regimes/:id : delete the "id" tax_regime.
     *
     * @param id the id of the tax_regime to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tax-regimes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax_regime(@PathVariable Long id) {
        log.debug("REST request to delete Tax_regime : {}", id);
        tax_regimeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax_regime", id.toString())).build();
    }

}
