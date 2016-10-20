package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tax_concept;
import org.megapractical.billingplatform.service.Tax_conceptService;
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
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tax_concept.
 */
@RestController
@RequestMapping("/api")
public class Tax_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Tax_conceptResource.class);

    @Inject
    private Tax_conceptService tax_conceptService;

    /**
     * POST  /tax-concepts : Create a new tax_concept.
     *
     * @param tax_concept the tax_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tax_concept, or with status 400 (Bad Request) if the tax_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_concept> createTax_concept(@Valid @RequestBody Tax_concept tax_concept) throws URISyntaxException {
        log.debug("REST request to save Tax_concept : {}", tax_concept);
        if (tax_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tax_concept", "idexists", "A new tax_concept cannot already have an ID")).body(null);
        }
        Tax_concept result = tax_conceptService.save(tax_concept);
        return ResponseEntity.created(new URI("/api/tax-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-concepts : Updates an existing tax_concept.
     *
     * @param tax_concept the tax_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tax_concept,
     * or with status 400 (Bad Request) if the tax_concept is not valid,
     * or with status 500 (Internal Server Error) if the tax_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_concept> updateTax_concept(@Valid @RequestBody Tax_concept tax_concept) throws URISyntaxException {
        log.debug("REST request to update Tax_concept : {}", tax_concept);
        if (tax_concept.getId() == null) {
            return createTax_concept(tax_concept);
        }
        Tax_concept result = tax_conceptService.save(tax_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax_concept", tax_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-concepts : get all the tax_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tax_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tax-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayeraccount", "tax_type", "rate", "concept", "conceptid"})
    @Timed
    public ResponseEntity<List<Tax_concept>> getAllTax_concepts(Pageable pageable,
                                                                @RequestParam(value = "taxpayeraccount") Integer taxpayeraccount,
                                                                @RequestParam(value = "tax_type") String tax_type,
                                                                @RequestParam(value = "rate") BigDecimal rate,
                                                                @RequestParam(value = "concept") String concept,
                                                                @RequestParam(value = "conceptid") Long conceptid)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tax_concepts");
        Page<Tax_concept> page = tax_conceptService.findAll(pageable, taxpayeraccount, tax_type, rate, concept, conceptid);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tax-concepts/:id : get the "id" tax_concept.
     *
     * @param id the id of the tax_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tax_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tax-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_concept> getTax_concept(@PathVariable Long id) {
        log.debug("REST request to get Tax_concept : {}", id);
        Tax_concept tax_concept = tax_conceptService.findOne(id);
        return Optional.ofNullable(tax_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tax-concepts/:id : delete the "id" tax_concept.
     *
     * @param id the id of the tax_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tax-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax_concept(@PathVariable Long id) {
        log.debug("REST request to delete Tax_concept : {}", id);
        tax_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax_concept", id.toString())).build();
    }

}
