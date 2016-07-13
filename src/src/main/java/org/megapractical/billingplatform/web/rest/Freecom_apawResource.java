package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_apaw;
import org.megapractical.billingplatform.service.Freecom_apawService;
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
 * REST controller for managing Freecom_apaw.
 */
@RestController
@RequestMapping("/api")
public class Freecom_apawResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_apawResource.class);
        
    @Inject
    private Freecom_apawService freecom_apawService;
    
    /**
     * POST  /freecom-apaws : Create a new freecom_apaw.
     *
     * @param freecom_apaw the freecom_apaw to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_apaw, or with status 400 (Bad Request) if the freecom_apaw has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-apaws",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_apaw> createFreecom_apaw(@Valid @RequestBody Freecom_apaw freecom_apaw) throws URISyntaxException {
        log.debug("REST request to save Freecom_apaw : {}", freecom_apaw);
        if (freecom_apaw.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_apaw", "idexists", "A new freecom_apaw cannot already have an ID")).body(null);
        }
        Freecom_apaw result = freecom_apawService.save(freecom_apaw);
        return ResponseEntity.created(new URI("/api/freecom-apaws/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_apaw", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-apaws : Updates an existing freecom_apaw.
     *
     * @param freecom_apaw the freecom_apaw to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_apaw,
     * or with status 400 (Bad Request) if the freecom_apaw is not valid,
     * or with status 500 (Internal Server Error) if the freecom_apaw couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-apaws",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_apaw> updateFreecom_apaw(@Valid @RequestBody Freecom_apaw freecom_apaw) throws URISyntaxException {
        log.debug("REST request to update Freecom_apaw : {}", freecom_apaw);
        if (freecom_apaw.getId() == null) {
            return createFreecom_apaw(freecom_apaw);
        }
        Freecom_apaw result = freecom_apawService.save(freecom_apaw);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_apaw", freecom_apaw.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-apaws : get all the freecom_apaws.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_apaws in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-apaws",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_apaw>> getAllFreecom_apaws(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_apaws");
        Page<Freecom_apaw> page = freecom_apawService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-apaws");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-apaws/:id : get the "id" freecom_apaw.
     *
     * @param id the id of the freecom_apaw to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_apaw, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-apaws/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_apaw> getFreecom_apaw(@PathVariable Long id) {
        log.debug("REST request to get Freecom_apaw : {}", id);
        Freecom_apaw freecom_apaw = freecom_apawService.findOne(id);
        return Optional.ofNullable(freecom_apaw)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-apaws/:id : delete the "id" freecom_apaw.
     *
     * @param id the id of the freecom_apaw to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-apaws/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_apaw(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_apaw : {}", id);
        freecom_apawService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_apaw", id.toString())).build();
    }

}
