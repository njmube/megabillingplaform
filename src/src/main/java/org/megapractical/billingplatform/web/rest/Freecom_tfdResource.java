package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_tfd;
import org.megapractical.billingplatform.service.Freecom_tfdService;
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
 * REST controller for managing Freecom_tfd.
 */
@RestController
@RequestMapping("/api")
public class Freecom_tfdResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_tfdResource.class);
        
    @Inject
    private Freecom_tfdService freecom_tfdService;
    
    /**
     * POST  /freecom-tfds : Create a new freecom_tfd.
     *
     * @param freecom_tfd the freecom_tfd to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_tfd, or with status 400 (Bad Request) if the freecom_tfd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-tfds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tfd> createFreecom_tfd(@Valid @RequestBody Freecom_tfd freecom_tfd) throws URISyntaxException {
        log.debug("REST request to save Freecom_tfd : {}", freecom_tfd);
        if (freecom_tfd.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_tfd", "idexists", "A new freecom_tfd cannot already have an ID")).body(null);
        }
        Freecom_tfd result = freecom_tfdService.save(freecom_tfd);
        return ResponseEntity.created(new URI("/api/freecom-tfds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_tfd", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-tfds : Updates an existing freecom_tfd.
     *
     * @param freecom_tfd the freecom_tfd to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_tfd,
     * or with status 400 (Bad Request) if the freecom_tfd is not valid,
     * or with status 500 (Internal Server Error) if the freecom_tfd couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-tfds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tfd> updateFreecom_tfd(@Valid @RequestBody Freecom_tfd freecom_tfd) throws URISyntaxException {
        log.debug("REST request to update Freecom_tfd : {}", freecom_tfd);
        if (freecom_tfd.getId() == null) {
            return createFreecom_tfd(freecom_tfd);
        }
        Freecom_tfd result = freecom_tfdService.save(freecom_tfd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_tfd", freecom_tfd.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-tfds : get all the freecom_tfds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_tfds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-tfds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_tfd>> getAllFreecom_tfds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_tfds");
        Page<Freecom_tfd> page = freecom_tfdService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-tfds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-tfds/:id : get the "id" freecom_tfd.
     *
     * @param id the id of the freecom_tfd to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_tfd, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-tfds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tfd> getFreecom_tfd(@PathVariable Long id) {
        log.debug("REST request to get Freecom_tfd : {}", id);
        Freecom_tfd freecom_tfd = freecom_tfdService.findOne(id);
        return Optional.ofNullable(freecom_tfd)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-tfds/:id : delete the "id" freecom_tfd.
     *
     * @param id the id of the freecom_tfd to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-tfds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_tfd(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_tfd : {}", id);
        freecom_tfdService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_tfd", id.toString())).build();
    }

}
