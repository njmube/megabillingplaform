package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Acquired_title;
import org.megapractical.billingplatform.service.Acquired_titleService;
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
 * REST controller for managing Acquired_title.
 */
@RestController
@RequestMapping("/api")
public class Acquired_titleResource {

    private final Logger log = LoggerFactory.getLogger(Acquired_titleResource.class);
        
    @Inject
    private Acquired_titleService acquired_titleService;
    
    /**
     * POST  /acquired-titles : Create a new acquired_title.
     *
     * @param acquired_title the acquired_title to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acquired_title, or with status 400 (Bad Request) if the acquired_title has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/acquired-titles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Acquired_title> createAcquired_title(@Valid @RequestBody Acquired_title acquired_title) throws URISyntaxException {
        log.debug("REST request to save Acquired_title : {}", acquired_title);
        if (acquired_title.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("acquired_title", "idexists", "A new acquired_title cannot already have an ID")).body(null);
        }
        Acquired_title result = acquired_titleService.save(acquired_title);
        return ResponseEntity.created(new URI("/api/acquired-titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("acquired_title", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acquired-titles : Updates an existing acquired_title.
     *
     * @param acquired_title the acquired_title to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acquired_title,
     * or with status 400 (Bad Request) if the acquired_title is not valid,
     * or with status 500 (Internal Server Error) if the acquired_title couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/acquired-titles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Acquired_title> updateAcquired_title(@Valid @RequestBody Acquired_title acquired_title) throws URISyntaxException {
        log.debug("REST request to update Acquired_title : {}", acquired_title);
        if (acquired_title.getId() == null) {
            return createAcquired_title(acquired_title);
        }
        Acquired_title result = acquired_titleService.save(acquired_title);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("acquired_title", acquired_title.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acquired-titles : get all the acquired_titles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of acquired_titles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/acquired-titles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Acquired_title>> getAllAcquired_titles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Acquired_titles");
        Page<Acquired_title> page = acquired_titleService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/acquired-titles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /acquired-titles/:id : get the "id" acquired_title.
     *
     * @param id the id of the acquired_title to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acquired_title, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/acquired-titles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Acquired_title> getAcquired_title(@PathVariable Long id) {
        log.debug("REST request to get Acquired_title : {}", id);
        Acquired_title acquired_title = acquired_titleService.findOne(id);
        return Optional.ofNullable(acquired_title)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /acquired-titles/:id : delete the "id" acquired_title.
     *
     * @param id the id of the acquired_title to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/acquired-titles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAcquired_title(@PathVariable Long id) {
        log.debug("REST request to delete Acquired_title : {}", id);
        acquired_titleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("acquired_title", id.toString())).build();
    }

}
