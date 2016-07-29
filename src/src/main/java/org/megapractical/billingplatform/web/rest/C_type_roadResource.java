package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_type_road;
import org.megapractical.billingplatform.service.C_type_roadService;
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
 * REST controller for managing C_type_road.
 */
@RestController
@RequestMapping("/api")
public class C_type_roadResource {

    private final Logger log = LoggerFactory.getLogger(C_type_roadResource.class);
        
    @Inject
    private C_type_roadService c_type_roadService;
    
    /**
     * POST  /c-type-roads : Create a new c_type_road.
     *
     * @param c_type_road the c_type_road to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_type_road, or with status 400 (Bad Request) if the c_type_road has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-roads",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_road> createC_type_road(@Valid @RequestBody C_type_road c_type_road) throws URISyntaxException {
        log.debug("REST request to save C_type_road : {}", c_type_road);
        if (c_type_road.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_type_road", "idexists", "A new c_type_road cannot already have an ID")).body(null);
        }
        C_type_road result = c_type_roadService.save(c_type_road);
        return ResponseEntity.created(new URI("/api/c-type-roads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_type_road", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-type-roads : Updates an existing c_type_road.
     *
     * @param c_type_road the c_type_road to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_type_road,
     * or with status 400 (Bad Request) if the c_type_road is not valid,
     * or with status 500 (Internal Server Error) if the c_type_road couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-roads",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_road> updateC_type_road(@Valid @RequestBody C_type_road c_type_road) throws URISyntaxException {
        log.debug("REST request to update C_type_road : {}", c_type_road);
        if (c_type_road.getId() == null) {
            return createC_type_road(c_type_road);
        }
        C_type_road result = c_type_roadService.save(c_type_road);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_type_road", c_type_road.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-type-roads : get all the c_type_roads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_type_roads in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-type-roads",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_type_road>> getAllC_type_roads(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_type_roads");
        Page<C_type_road> page = c_type_roadService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-type-roads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-type-roads/:id : get the "id" c_type_road.
     *
     * @param id the id of the c_type_road to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_type_road, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-type-roads/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_road> getC_type_road(@PathVariable Long id) {
        log.debug("REST request to get C_type_road : {}", id);
        C_type_road c_type_road = c_type_roadService.findOne(id);
        return Optional.ofNullable(c_type_road)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-type-roads/:id : delete the "id" c_type_road.
     *
     * @param id the id of the c_type_road to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-type-roads/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_type_road(@PathVariable Long id) {
        log.debug("REST request to delete C_type_road : {}", id);
        c_type_roadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_type_road", id.toString())).build();
    }

}
