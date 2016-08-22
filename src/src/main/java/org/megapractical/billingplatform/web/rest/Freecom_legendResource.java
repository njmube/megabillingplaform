package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_legend;
import org.megapractical.billingplatform.service.Freecom_legendService;
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
 * REST controller for managing Freecom_legend.
 */
@RestController
@RequestMapping("/api")
public class Freecom_legendResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_legendResource.class);
        
    @Inject
    private Freecom_legendService freecom_legendService;
    
    /**
     * POST  /freecom-legends : Create a new freecom_legend.
     *
     * @param freecom_legend the freecom_legend to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_legend, or with status 400 (Bad Request) if the freecom_legend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-legends",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_legend> createFreecom_legend(@Valid @RequestBody Freecom_legend freecom_legend) throws URISyntaxException {
        log.debug("REST request to save Freecom_legend : {}", freecom_legend);
        if (freecom_legend.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_legend", "idexists", "A new freecom_legend cannot already have an ID")).body(null);
        }
        Freecom_legend result = freecom_legendService.save(freecom_legend);
        return ResponseEntity.created(new URI("/api/freecom-legends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_legend", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-legends : Updates an existing freecom_legend.
     *
     * @param freecom_legend the freecom_legend to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_legend,
     * or with status 400 (Bad Request) if the freecom_legend is not valid,
     * or with status 500 (Internal Server Error) if the freecom_legend couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-legends",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_legend> updateFreecom_legend(@Valid @RequestBody Freecom_legend freecom_legend) throws URISyntaxException {
        log.debug("REST request to update Freecom_legend : {}", freecom_legend);
        if (freecom_legend.getId() == null) {
            return createFreecom_legend(freecom_legend);
        }
        Freecom_legend result = freecom_legendService.save(freecom_legend);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_legend", freecom_legend.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-legends : get all the freecom_legends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_legends in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-legends",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_legend>> getAllFreecom_legends(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_legends");
        Page<Freecom_legend> page = freecom_legendService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-legends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-legends/:id : get the "id" freecom_legend.
     *
     * @param id the id of the freecom_legend to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_legend, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-legends/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_legend> getFreecom_legend(@PathVariable Long id) {
        log.debug("REST request to get Freecom_legend : {}", id);
        Freecom_legend freecom_legend = freecom_legendService.findOne(id);
        return Optional.ofNullable(freecom_legend)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-legends/:id : delete the "id" freecom_legend.
     *
     * @param id the id of the freecom_legend to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-legends/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_legend(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_legend : {}", id);
        freecom_legendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_legend", id.toString())).build();
    }

}
