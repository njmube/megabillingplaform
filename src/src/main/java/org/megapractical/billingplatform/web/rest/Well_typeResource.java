package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Well_type;
import org.megapractical.billingplatform.service.Well_typeService;
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
 * REST controller for managing Well_type.
 */
@RestController
@RequestMapping("/api")
public class Well_typeResource {

    private final Logger log = LoggerFactory.getLogger(Well_typeResource.class);
        
    @Inject
    private Well_typeService well_typeService;
    
    /**
     * POST  /well-types : Create a new well_type.
     *
     * @param well_type the well_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new well_type, or with status 400 (Bad Request) if the well_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/well-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Well_type> createWell_type(@Valid @RequestBody Well_type well_type) throws URISyntaxException {
        log.debug("REST request to save Well_type : {}", well_type);
        if (well_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("well_type", "idexists", "A new well_type cannot already have an ID")).body(null);
        }
        Well_type result = well_typeService.save(well_type);
        return ResponseEntity.created(new URI("/api/well-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("well_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /well-types : Updates an existing well_type.
     *
     * @param well_type the well_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated well_type,
     * or with status 400 (Bad Request) if the well_type is not valid,
     * or with status 500 (Internal Server Error) if the well_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/well-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Well_type> updateWell_type(@Valid @RequestBody Well_type well_type) throws URISyntaxException {
        log.debug("REST request to update Well_type : {}", well_type);
        if (well_type.getId() == null) {
            return createWell_type(well_type);
        }
        Well_type result = well_typeService.save(well_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("well_type", well_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /well-types : get all the well_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of well_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/well-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Well_type>> getAllWell_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Well_types");
        Page<Well_type> page = well_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/well-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /well-types/:id : get the "id" well_type.
     *
     * @param id the id of the well_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the well_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/well-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Well_type> getWell_type(@PathVariable Long id) {
        log.debug("REST request to get Well_type : {}", id);
        Well_type well_type = well_typeService.findOne(id);
        return Optional.ofNullable(well_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /well-types/:id : delete the "id" well_type.
     *
     * @param id the id of the well_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/well-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWell_type(@PathVariable Long id) {
        log.debug("REST request to delete Well_type : {}", id);
        well_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("well_type", id.toString())).build();
    }

}
