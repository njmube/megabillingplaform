package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_determined;
import org.megapractical.billingplatform.service.Freecom_determinedService;
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
 * REST controller for managing Freecom_determined.
 */
@RestController
@RequestMapping("/api")
public class Freecom_determinedResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_determinedResource.class);
        
    @Inject
    private Freecom_determinedService freecom_determinedService;
    
    /**
     * POST  /freecom-determineds : Create a new freecom_determined.
     *
     * @param freecom_determined the freecom_determined to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_determined, or with status 400 (Bad Request) if the freecom_determined has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-determineds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_determined> createFreecom_determined(@Valid @RequestBody Freecom_determined freecom_determined) throws URISyntaxException {
        log.debug("REST request to save Freecom_determined : {}", freecom_determined);
        if (freecom_determined.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_determined", "idexists", "A new freecom_determined cannot already have an ID")).body(null);
        }
        Freecom_determined result = freecom_determinedService.save(freecom_determined);
        return ResponseEntity.created(new URI("/api/freecom-determineds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_determined", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-determineds : Updates an existing freecom_determined.
     *
     * @param freecom_determined the freecom_determined to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_determined,
     * or with status 400 (Bad Request) if the freecom_determined is not valid,
     * or with status 500 (Internal Server Error) if the freecom_determined couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-determineds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_determined> updateFreecom_determined(@Valid @RequestBody Freecom_determined freecom_determined) throws URISyntaxException {
        log.debug("REST request to update Freecom_determined : {}", freecom_determined);
        if (freecom_determined.getId() == null) {
            return createFreecom_determined(freecom_determined);
        }
        Freecom_determined result = freecom_determinedService.save(freecom_determined);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_determined", freecom_determined.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-determineds : get all the freecom_determineds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_determineds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-determineds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_determined>> getAllFreecom_determineds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_determineds");
        Page<Freecom_determined> page = freecom_determinedService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-determineds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-determineds/:id : get the "id" freecom_determined.
     *
     * @param id the id of the freecom_determined to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_determined, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-determineds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_determined> getFreecom_determined(@PathVariable Long id) {
        log.debug("REST request to get Freecom_determined : {}", id);
        Freecom_determined freecom_determined = freecom_determinedService.findOne(id);
        return Optional.ofNullable(freecom_determined)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-determineds/:id : delete the "id" freecom_determined.
     *
     * @param id the id of the freecom_determined to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-determineds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_determined(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_determined : {}", id);
        freecom_determinedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_determined", id.toString())).build();
    }

}
