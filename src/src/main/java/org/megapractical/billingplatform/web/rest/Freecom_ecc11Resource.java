package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_ecc11;
import org.megapractical.billingplatform.service.Freecom_ecc11Service;
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
 * REST controller for managing Freecom_ecc11.
 */
@RestController
@RequestMapping("/api")
public class Freecom_ecc11Resource {

    private final Logger log = LoggerFactory.getLogger(Freecom_ecc11Resource.class);
        
    @Inject
    private Freecom_ecc11Service freecom_ecc11Service;
    
    /**
     * POST  /freecom-ecc-11-s : Create a new freecom_ecc11.
     *
     * @param freecom_ecc11 the freecom_ecc11 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_ecc11, or with status 400 (Bad Request) if the freecom_ecc11 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ecc-11-s",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11> createFreecom_ecc11(@Valid @RequestBody Freecom_ecc11 freecom_ecc11) throws URISyntaxException {
        log.debug("REST request to save Freecom_ecc11 : {}", freecom_ecc11);
        if (freecom_ecc11.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_ecc11", "idexists", "A new freecom_ecc11 cannot already have an ID")).body(null);
        }
        Freecom_ecc11 result = freecom_ecc11Service.save(freecom_ecc11);
        return ResponseEntity.created(new URI("/api/freecom-ecc-11-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_ecc11", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-ecc-11-s : Updates an existing freecom_ecc11.
     *
     * @param freecom_ecc11 the freecom_ecc11 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_ecc11,
     * or with status 400 (Bad Request) if the freecom_ecc11 is not valid,
     * or with status 500 (Internal Server Error) if the freecom_ecc11 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ecc-11-s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11> updateFreecom_ecc11(@Valid @RequestBody Freecom_ecc11 freecom_ecc11) throws URISyntaxException {
        log.debug("REST request to update Freecom_ecc11 : {}", freecom_ecc11);
        if (freecom_ecc11.getId() == null) {
            return createFreecom_ecc11(freecom_ecc11);
        }
        Freecom_ecc11 result = freecom_ecc11Service.save(freecom_ecc11);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_ecc11", freecom_ecc11.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-ecc-11-s : get all the freecom_ecc11S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_ecc11S in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-ecc-11-s",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_ecc11>> getAllFreecom_ecc11S(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_ecc11S");
        Page<Freecom_ecc11> page = freecom_ecc11Service.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-ecc-11-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-ecc-11-s/:id : get the "id" freecom_ecc11.
     *
     * @param id the id of the freecom_ecc11 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_ecc11, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-ecc-11-s/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11> getFreecom_ecc11(@PathVariable Long id) {
        log.debug("REST request to get Freecom_ecc11 : {}", id);
        Freecom_ecc11 freecom_ecc11 = freecom_ecc11Service.findOne(id);
        return Optional.ofNullable(freecom_ecc11)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-ecc-11-s/:id : delete the "id" freecom_ecc11.
     *
     * @param id the id of the freecom_ecc11 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-ecc-11-s/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_ecc11(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_ecc11 : {}", id);
        freecom_ecc11Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_ecc11", id.toString())).build();
    }

}
