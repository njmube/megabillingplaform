package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Taxpayer_series_folio;
import org.megapractical.billingplatform.service.Taxpayer_series_folioService;
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
 * REST controller for managing Taxpayer_series_folio.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_series_folioResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_series_folioResource.class);
        
    @Inject
    private Taxpayer_series_folioService taxpayer_series_folioService;
    
    /**
     * POST  /taxpayer-series-folios : Create a new taxpayer_series_folio.
     *
     * @param taxpayer_series_folio the taxpayer_series_folio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_series_folio, or with status 400 (Bad Request) if the taxpayer_series_folio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-series-folios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_series_folio> createTaxpayer_series_folio(@Valid @RequestBody Taxpayer_series_folio taxpayer_series_folio) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_series_folio : {}", taxpayer_series_folio);
        if (taxpayer_series_folio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_series_folio", "idexists", "A new taxpayer_series_folio cannot already have an ID")).body(null);
        }
        Taxpayer_series_folio result = taxpayer_series_folioService.save(taxpayer_series_folio);
        return ResponseEntity.created(new URI("/api/taxpayer-series-folios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_series_folio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-series-folios : Updates an existing taxpayer_series_folio.
     *
     * @param taxpayer_series_folio the taxpayer_series_folio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_series_folio,
     * or with status 400 (Bad Request) if the taxpayer_series_folio is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_series_folio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-series-folios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_series_folio> updateTaxpayer_series_folio(@Valid @RequestBody Taxpayer_series_folio taxpayer_series_folio) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_series_folio : {}", taxpayer_series_folio);
        if (taxpayer_series_folio.getId() == null) {
            return createTaxpayer_series_folio(taxpayer_series_folio);
        }
        Taxpayer_series_folio result = taxpayer_series_folioService.save(taxpayer_series_folio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_series_folio", taxpayer_series_folio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-series-folios : get all the taxpayer_series_folios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_series_folios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-series-folios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Taxpayer_series_folio>> getAllTaxpayer_series_folios(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_series_folios");
        Page<Taxpayer_series_folio> page = taxpayer_series_folioService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-series-folios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-series-folios/:id : get the "id" taxpayer_series_folio.
     *
     * @param id the id of the taxpayer_series_folio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_series_folio, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-series-folios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_series_folio> getTaxpayer_series_folio(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_series_folio : {}", id);
        Taxpayer_series_folio taxpayer_series_folio = taxpayer_series_folioService.findOne(id);
        return Optional.ofNullable(taxpayer_series_folio)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-series-folios/:id : delete the "id" taxpayer_series_folio.
     *
     * @param id the id of the taxpayer_series_folio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-series-folios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_series_folio(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_series_folio : {}", id);
        taxpayer_series_folioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_series_folio", id.toString())).build();
    }

}
