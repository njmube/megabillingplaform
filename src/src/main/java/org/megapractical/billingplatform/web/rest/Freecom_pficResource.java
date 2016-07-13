package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_pfic;
import org.megapractical.billingplatform.service.Freecom_pficService;
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
 * REST controller for managing Freecom_pfic.
 */
@RestController
@RequestMapping("/api")
public class Freecom_pficResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_pficResource.class);
        
    @Inject
    private Freecom_pficService freecom_pficService;
    
    /**
     * POST  /freecom-pfics : Create a new freecom_pfic.
     *
     * @param freecom_pfic the freecom_pfic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_pfic, or with status 400 (Bad Request) if the freecom_pfic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-pfics",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_pfic> createFreecom_pfic(@Valid @RequestBody Freecom_pfic freecom_pfic) throws URISyntaxException {
        log.debug("REST request to save Freecom_pfic : {}", freecom_pfic);
        if (freecom_pfic.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_pfic", "idexists", "A new freecom_pfic cannot already have an ID")).body(null);
        }
        Freecom_pfic result = freecom_pficService.save(freecom_pfic);
        return ResponseEntity.created(new URI("/api/freecom-pfics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_pfic", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-pfics : Updates an existing freecom_pfic.
     *
     * @param freecom_pfic the freecom_pfic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_pfic,
     * or with status 400 (Bad Request) if the freecom_pfic is not valid,
     * or with status 500 (Internal Server Error) if the freecom_pfic couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-pfics",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_pfic> updateFreecom_pfic(@Valid @RequestBody Freecom_pfic freecom_pfic) throws URISyntaxException {
        log.debug("REST request to update Freecom_pfic : {}", freecom_pfic);
        if (freecom_pfic.getId() == null) {
            return createFreecom_pfic(freecom_pfic);
        }
        Freecom_pfic result = freecom_pficService.save(freecom_pfic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_pfic", freecom_pfic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-pfics : get all the freecom_pfics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_pfics in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-pfics",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_pfic>> getAllFreecom_pfics(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_pfics");
        Page<Freecom_pfic> page = freecom_pficService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-pfics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-pfics/:id : get the "id" freecom_pfic.
     *
     * @param id the id of the freecom_pfic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_pfic, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-pfics/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_pfic> getFreecom_pfic(@PathVariable Long id) {
        log.debug("REST request to get Freecom_pfic : {}", id);
        Freecom_pfic freecom_pfic = freecom_pficService.findOne(id);
        return Optional.ofNullable(freecom_pfic)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-pfics/:id : delete the "id" freecom_pfic.
     *
     * @param id the id of the freecom_pfic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-pfics/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_pfic(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_pfic : {}", id);
        freecom_pficService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_pfic", id.toString())).build();
    }

}
