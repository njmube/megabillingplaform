package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_system;
import org.megapractical.billingplatform.service.C_systemService;
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
 * REST controller for managing C_system.
 */
@RestController
@RequestMapping("/api")
public class C_systemResource {

    private final Logger log = LoggerFactory.getLogger(C_systemResource.class);
        
    @Inject
    private C_systemService c_systemService;
    
    /**
     * POST  /c-systems : Create a new c_system.
     *
     * @param c_system the c_system to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_system, or with status 400 (Bad Request) if the c_system has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-systems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_system> createC_system(@Valid @RequestBody C_system c_system) throws URISyntaxException {
        log.debug("REST request to save C_system : {}", c_system);
        if (c_system.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_system", "idexists", "A new c_system cannot already have an ID")).body(null);
        }
        C_system result = c_systemService.save(c_system);
        return ResponseEntity.created(new URI("/api/c-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_system", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-systems : Updates an existing c_system.
     *
     * @param c_system the c_system to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_system,
     * or with status 400 (Bad Request) if the c_system is not valid,
     * or with status 500 (Internal Server Error) if the c_system couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-systems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_system> updateC_system(@Valid @RequestBody C_system c_system) throws URISyntaxException {
        log.debug("REST request to update C_system : {}", c_system);
        if (c_system.getId() == null) {
            return createC_system(c_system);
        }
        C_system result = c_systemService.save(c_system);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_system", c_system.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-systems : get all the c_systems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_systems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-systems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_system>> getAllC_systems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_systems");
        Page<C_system> page = c_systemService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-systems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-systems/:id : get the "id" c_system.
     *
     * @param id the id of the c_system to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_system, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-systems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_system> getC_system(@PathVariable Long id) {
        log.debug("REST request to get C_system : {}", id);
        C_system c_system = c_systemService.findOne(id);
        return Optional.ofNullable(c_system)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-systems/:id : delete the "id" c_system.
     *
     * @param id the id of the c_system to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-systems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_system(@PathVariable Long id) {
        log.debug("REST request to delete C_system : {}", id);
        c_systemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_system", id.toString())).build();
    }

}
