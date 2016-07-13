package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_ine;
import org.megapractical.billingplatform.service.Freecom_ineService;
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
 * REST controller for managing Freecom_ine.
 */
@RestController
@RequestMapping("/api")
public class Freecom_ineResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_ineResource.class);
        
    @Inject
    private Freecom_ineService freecom_ineService;
    
    /**
     * POST  /freecom-ines : Create a new freecom_ine.
     *
     * @param freecom_ine the freecom_ine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_ine, or with status 400 (Bad Request) if the freecom_ine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ine> createFreecom_ine(@Valid @RequestBody Freecom_ine freecom_ine) throws URISyntaxException {
        log.debug("REST request to save Freecom_ine : {}", freecom_ine);
        if (freecom_ine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_ine", "idexists", "A new freecom_ine cannot already have an ID")).body(null);
        }
        Freecom_ine result = freecom_ineService.save(freecom_ine);
        return ResponseEntity.created(new URI("/api/freecom-ines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_ine", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-ines : Updates an existing freecom_ine.
     *
     * @param freecom_ine the freecom_ine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_ine,
     * or with status 400 (Bad Request) if the freecom_ine is not valid,
     * or with status 500 (Internal Server Error) if the freecom_ine couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ine> updateFreecom_ine(@Valid @RequestBody Freecom_ine freecom_ine) throws URISyntaxException {
        log.debug("REST request to update Freecom_ine : {}", freecom_ine);
        if (freecom_ine.getId() == null) {
            return createFreecom_ine(freecom_ine);
        }
        Freecom_ine result = freecom_ineService.save(freecom_ine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_ine", freecom_ine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-ines : get all the freecom_ines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_ines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-ines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_ine>> getAllFreecom_ines(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_ines");
        Page<Freecom_ine> page = freecom_ineService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-ines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-ines/:id : get the "id" freecom_ine.
     *
     * @param id the id of the freecom_ine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_ine, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-ines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ine> getFreecom_ine(@PathVariable Long id) {
        log.debug("REST request to get Freecom_ine : {}", id);
        Freecom_ine freecom_ine = freecom_ineService.findOne(id);
        return Optional.ofNullable(freecom_ine)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-ines/:id : delete the "id" freecom_ine.
     *
     * @param id the id of the freecom_ine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-ines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_ine(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_ine : {}", id);
        freecom_ineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_ine", id.toString())).build();
    }

}
