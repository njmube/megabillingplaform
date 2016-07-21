package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_school_level;
import org.megapractical.billingplatform.service.C_school_levelService;
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
 * REST controller for managing C_school_level.
 */
@RestController
@RequestMapping("/api")
public class C_school_levelResource {

    private final Logger log = LoggerFactory.getLogger(C_school_levelResource.class);
        
    @Inject
    private C_school_levelService c_school_levelService;
    
    /**
     * POST  /c-school-levels : Create a new c_school_level.
     *
     * @param c_school_level the c_school_level to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_school_level, or with status 400 (Bad Request) if the c_school_level has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-school-levels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_school_level> createC_school_level(@Valid @RequestBody C_school_level c_school_level) throws URISyntaxException {
        log.debug("REST request to save C_school_level : {}", c_school_level);
        if (c_school_level.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_school_level", "idexists", "A new c_school_level cannot already have an ID")).body(null);
        }
        C_school_level result = c_school_levelService.save(c_school_level);
        return ResponseEntity.created(new URI("/api/c-school-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_school_level", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-school-levels : Updates an existing c_school_level.
     *
     * @param c_school_level the c_school_level to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_school_level,
     * or with status 400 (Bad Request) if the c_school_level is not valid,
     * or with status 500 (Internal Server Error) if the c_school_level couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-school-levels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_school_level> updateC_school_level(@Valid @RequestBody C_school_level c_school_level) throws URISyntaxException {
        log.debug("REST request to update C_school_level : {}", c_school_level);
        if (c_school_level.getId() == null) {
            return createC_school_level(c_school_level);
        }
        C_school_level result = c_school_levelService.save(c_school_level);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_school_level", c_school_level.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-school-levels : get all the c_school_levels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_school_levels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-school-levels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_school_level>> getAllC_school_levels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_school_levels");
        Page<C_school_level> page = c_school_levelService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-school-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-school-levels/:id : get the "id" c_school_level.
     *
     * @param id the id of the c_school_level to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_school_level, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-school-levels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_school_level> getC_school_level(@PathVariable Long id) {
        log.debug("REST request to get C_school_level : {}", id);
        C_school_level c_school_level = c_school_levelService.findOne(id);
        return Optional.ofNullable(c_school_level)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-school-levels/:id : delete the "id" c_school_level.
     *
     * @param id the id of the c_school_level to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-school-levels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_school_level(@PathVariable Long id) {
        log.debug("REST request to delete C_school_level : {}", id);
        c_school_levelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_school_level", id.toString())).build();
    }

}
