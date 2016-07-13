package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.School_level;
import org.megapractical.billingplatform.service.School_levelService;
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
 * REST controller for managing School_level.
 */
@RestController
@RequestMapping("/api")
public class School_levelResource {

    private final Logger log = LoggerFactory.getLogger(School_levelResource.class);
        
    @Inject
    private School_levelService school_levelService;
    
    /**
     * POST  /school-levels : Create a new school_level.
     *
     * @param school_level the school_level to create
     * @return the ResponseEntity with status 201 (Created) and with body the new school_level, or with status 400 (Bad Request) if the school_level has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/school-levels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<School_level> createSchool_level(@Valid @RequestBody School_level school_level) throws URISyntaxException {
        log.debug("REST request to save School_level : {}", school_level);
        if (school_level.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("school_level", "idexists", "A new school_level cannot already have an ID")).body(null);
        }
        School_level result = school_levelService.save(school_level);
        return ResponseEntity.created(new URI("/api/school-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("school_level", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /school-levels : Updates an existing school_level.
     *
     * @param school_level the school_level to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated school_level,
     * or with status 400 (Bad Request) if the school_level is not valid,
     * or with status 500 (Internal Server Error) if the school_level couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/school-levels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<School_level> updateSchool_level(@Valid @RequestBody School_level school_level) throws URISyntaxException {
        log.debug("REST request to update School_level : {}", school_level);
        if (school_level.getId() == null) {
            return createSchool_level(school_level);
        }
        School_level result = school_levelService.save(school_level);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("school_level", school_level.getId().toString()))
            .body(result);
    }

    /**
     * GET  /school-levels : get all the school_levels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of school_levels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/school-levels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<School_level>> getAllSchool_levels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of School_levels");
        Page<School_level> page = school_levelService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/school-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /school-levels/:id : get the "id" school_level.
     *
     * @param id the id of the school_level to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the school_level, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/school-levels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<School_level> getSchool_level(@PathVariable Long id) {
        log.debug("REST request to get School_level : {}", id);
        School_level school_level = school_levelService.findOne(id);
        return Optional.ofNullable(school_level)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /school-levels/:id : delete the "id" school_level.
     *
     * @param id the id of the school_level to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/school-levels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSchool_level(@PathVariable Long id) {
        log.debug("REST request to delete School_level : {}", id);
        school_levelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("school_level", id.toString())).build();
    }

}
