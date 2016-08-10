package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_spei_third;
import org.megapractical.billingplatform.service.Freecom_spei_thirdService;
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
 * REST controller for managing Freecom_spei_third.
 */
@RestController
@RequestMapping("/api")
public class Freecom_spei_thirdResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_spei_thirdResource.class);
        
    @Inject
    private Freecom_spei_thirdService freecom_spei_thirdService;
    
    /**
     * POST  /freecom-spei-thirds : Create a new freecom_spei_third.
     *
     * @param freecom_spei_third the freecom_spei_third to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_spei_third, or with status 400 (Bad Request) if the freecom_spei_third has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-spei-thirds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_spei_third> createFreecom_spei_third(@Valid @RequestBody Freecom_spei_third freecom_spei_third) throws URISyntaxException {
        log.debug("REST request to save Freecom_spei_third : {}", freecom_spei_third);
        if (freecom_spei_third.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_spei_third", "idexists", "A new freecom_spei_third cannot already have an ID")).body(null);
        }
        Freecom_spei_third result = freecom_spei_thirdService.save(freecom_spei_third);
        return ResponseEntity.created(new URI("/api/freecom-spei-thirds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_spei_third", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-spei-thirds : Updates an existing freecom_spei_third.
     *
     * @param freecom_spei_third the freecom_spei_third to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_spei_third,
     * or with status 400 (Bad Request) if the freecom_spei_third is not valid,
     * or with status 500 (Internal Server Error) if the freecom_spei_third couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-spei-thirds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_spei_third> updateFreecom_spei_third(@Valid @RequestBody Freecom_spei_third freecom_spei_third) throws URISyntaxException {
        log.debug("REST request to update Freecom_spei_third : {}", freecom_spei_third);
        if (freecom_spei_third.getId() == null) {
            return createFreecom_spei_third(freecom_spei_third);
        }
        Freecom_spei_third result = freecom_spei_thirdService.save(freecom_spei_third);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_spei_third", freecom_spei_third.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-spei-thirds : get all the freecom_spei_thirds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_spei_thirds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-spei-thirds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_spei_third>> getAllFreecom_spei_thirds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_spei_thirds");
        Page<Freecom_spei_third> page = freecom_spei_thirdService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-spei-thirds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-spei-thirds/:id : get the "id" freecom_spei_third.
     *
     * @param id the id of the freecom_spei_third to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_spei_third, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-spei-thirds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_spei_third> getFreecom_spei_third(@PathVariable Long id) {
        log.debug("REST request to get Freecom_spei_third : {}", id);
        Freecom_spei_third freecom_spei_third = freecom_spei_thirdService.findOne(id);
        return Optional.ofNullable(freecom_spei_third)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-spei-thirds/:id : delete the "id" freecom_spei_third.
     *
     * @param id the id of the freecom_spei_third to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-spei-thirds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_spei_third(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_spei_third : {}", id);
        freecom_spei_thirdService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_spei_third", id.toString())).build();
    }

}
