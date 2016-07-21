package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_acquired_title;
import org.megapractical.billingplatform.service.C_acquired_titleService;
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
 * REST controller for managing C_acquired_title.
 */
@RestController
@RequestMapping("/api")
public class C_acquired_titleResource {

    private final Logger log = LoggerFactory.getLogger(C_acquired_titleResource.class);
        
    @Inject
    private C_acquired_titleService c_acquired_titleService;
    
    /**
     * POST  /c-acquired-titles : Create a new c_acquired_title.
     *
     * @param c_acquired_title the c_acquired_title to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_acquired_title, or with status 400 (Bad Request) if the c_acquired_title has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-acquired-titles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_acquired_title> createC_acquired_title(@Valid @RequestBody C_acquired_title c_acquired_title) throws URISyntaxException {
        log.debug("REST request to save C_acquired_title : {}", c_acquired_title);
        if (c_acquired_title.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_acquired_title", "idexists", "A new c_acquired_title cannot already have an ID")).body(null);
        }
        C_acquired_title result = c_acquired_titleService.save(c_acquired_title);
        return ResponseEntity.created(new URI("/api/c-acquired-titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_acquired_title", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-acquired-titles : Updates an existing c_acquired_title.
     *
     * @param c_acquired_title the c_acquired_title to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_acquired_title,
     * or with status 400 (Bad Request) if the c_acquired_title is not valid,
     * or with status 500 (Internal Server Error) if the c_acquired_title couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-acquired-titles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_acquired_title> updateC_acquired_title(@Valid @RequestBody C_acquired_title c_acquired_title) throws URISyntaxException {
        log.debug("REST request to update C_acquired_title : {}", c_acquired_title);
        if (c_acquired_title.getId() == null) {
            return createC_acquired_title(c_acquired_title);
        }
        C_acquired_title result = c_acquired_titleService.save(c_acquired_title);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_acquired_title", c_acquired_title.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-acquired-titles : get all the c_acquired_titles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_acquired_titles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-acquired-titles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_acquired_title>> getAllC_acquired_titles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_acquired_titles");
        Page<C_acquired_title> page = c_acquired_titleService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-acquired-titles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-acquired-titles/:id : get the "id" c_acquired_title.
     *
     * @param id the id of the c_acquired_title to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_acquired_title, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-acquired-titles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_acquired_title> getC_acquired_title(@PathVariable Long id) {
        log.debug("REST request to get C_acquired_title : {}", id);
        C_acquired_title c_acquired_title = c_acquired_titleService.findOne(id);
        return Optional.ofNullable(c_acquired_title)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-acquired-titles/:id : delete the "id" c_acquired_title.
     *
     * @param id the id of the c_acquired_title to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-acquired-titles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_acquired_title(@PathVariable Long id) {
        log.debug("REST request to delete C_acquired_title : {}", id);
        c_acquired_titleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_acquired_title", id.toString())).build();
    }

}
