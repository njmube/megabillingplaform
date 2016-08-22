package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_commodity;
import org.megapractical.billingplatform.service.Freecom_commodityService;
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
 * REST controller for managing Freecom_commodity.
 */
@RestController
@RequestMapping("/api")
public class Freecom_commodityResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_commodityResource.class);
        
    @Inject
    private Freecom_commodityService freecom_commodityService;
    
    /**
     * POST  /freecom-commodities : Create a new freecom_commodity.
     *
     * @param freecom_commodity the freecom_commodity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_commodity, or with status 400 (Bad Request) if the freecom_commodity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-commodities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_commodity> createFreecom_commodity(@Valid @RequestBody Freecom_commodity freecom_commodity) throws URISyntaxException {
        log.debug("REST request to save Freecom_commodity : {}", freecom_commodity);
        if (freecom_commodity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_commodity", "idexists", "A new freecom_commodity cannot already have an ID")).body(null);
        }
        Freecom_commodity result = freecom_commodityService.save(freecom_commodity);
        return ResponseEntity.created(new URI("/api/freecom-commodities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_commodity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-commodities : Updates an existing freecom_commodity.
     *
     * @param freecom_commodity the freecom_commodity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_commodity,
     * or with status 400 (Bad Request) if the freecom_commodity is not valid,
     * or with status 500 (Internal Server Error) if the freecom_commodity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-commodities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_commodity> updateFreecom_commodity(@Valid @RequestBody Freecom_commodity freecom_commodity) throws URISyntaxException {
        log.debug("REST request to update Freecom_commodity : {}", freecom_commodity);
        if (freecom_commodity.getId() == null) {
            return createFreecom_commodity(freecom_commodity);
        }
        Freecom_commodity result = freecom_commodityService.save(freecom_commodity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_commodity", freecom_commodity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-commodities : get all the freecom_commodities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_commodities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-commodities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_commodity>> getAllFreecom_commodities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_commodities");
        Page<Freecom_commodity> page = freecom_commodityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-commodities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-commodities/:id : get the "id" freecom_commodity.
     *
     * @param id the id of the freecom_commodity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_commodity, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-commodities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_commodity> getFreecom_commodity(@PathVariable Long id) {
        log.debug("REST request to get Freecom_commodity : {}", id);
        Freecom_commodity freecom_commodity = freecom_commodityService.findOne(id);
        return Optional.ofNullable(freecom_commodity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-commodities/:id : delete the "id" freecom_commodity.
     *
     * @param id the id of the freecom_commodity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-commodities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_commodity(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_commodity : {}", id);
        freecom_commodityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_commodity", id.toString())).build();
    }

}
