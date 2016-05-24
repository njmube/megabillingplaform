package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_tax_rate;
import org.megapractical.billingplatform.service.C_tax_rateService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing C_tax_rate.
 */
@RestController
@RequestMapping("/api")
public class C_tax_rateResource {

    private final Logger log = LoggerFactory.getLogger(C_tax_rateResource.class);
        
    @Inject
    private C_tax_rateService c_tax_rateService;
    
    /**
     * POST  /c-tax-rates : Create a new c_tax_rate.
     *
     * @param c_tax_rate the c_tax_rate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_tax_rate, or with status 400 (Bad Request) if the c_tax_rate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-tax-rates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_tax_rate> createC_tax_rate(@RequestBody C_tax_rate c_tax_rate) throws URISyntaxException {
        log.debug("REST request to save C_tax_rate : {}", c_tax_rate);
        if (c_tax_rate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_tax_rate", "idexists", "A new c_tax_rate cannot already have an ID")).body(null);
        }
        C_tax_rate result = c_tax_rateService.save(c_tax_rate);
        return ResponseEntity.created(new URI("/api/c-tax-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_tax_rate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-tax-rates : Updates an existing c_tax_rate.
     *
     * @param c_tax_rate the c_tax_rate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_tax_rate,
     * or with status 400 (Bad Request) if the c_tax_rate is not valid,
     * or with status 500 (Internal Server Error) if the c_tax_rate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-tax-rates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_tax_rate> updateC_tax_rate(@RequestBody C_tax_rate c_tax_rate) throws URISyntaxException {
        log.debug("REST request to update C_tax_rate : {}", c_tax_rate);
        if (c_tax_rate.getId() == null) {
            return createC_tax_rate(c_tax_rate);
        }
        C_tax_rate result = c_tax_rateService.save(c_tax_rate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_tax_rate", c_tax_rate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-tax-rates : get all the c_tax_rates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_tax_rates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-tax-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_tax_rate>> getAllC_tax_rates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_tax_rates");
        Page<C_tax_rate> page = c_tax_rateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-tax-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-tax-rates/:id : get the "id" c_tax_rate.
     *
     * @param id the id of the c_tax_rate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_tax_rate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-tax-rates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_tax_rate> getC_tax_rate(@PathVariable Long id) {
        log.debug("REST request to get C_tax_rate : {}", id);
        C_tax_rate c_tax_rate = c_tax_rateService.findOne(id);
        return Optional.ofNullable(c_tax_rate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-tax-rates/:id : delete the "id" c_tax_rate.
     *
     * @param id the id of the c_tax_rate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-tax-rates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_tax_rate(@PathVariable Long id) {
        log.debug("REST request to delete C_tax_rate : {}", id);
        c_tax_rateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_tax_rate", id.toString())).build();
    }

}
