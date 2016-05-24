package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Package_state;
import org.megapractical.billingplatform.service.Package_stateService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Package_state.
 */
@RestController
@RequestMapping("/api")
public class Package_stateResource {

    private final Logger log = LoggerFactory.getLogger(Package_stateResource.class);
        
    @Inject
    private Package_stateService package_stateService;
    
    /**
     * POST  /package-states : Create a new package_state.
     *
     * @param package_state the package_state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new package_state, or with status 400 (Bad Request) if the package_state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/package-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_state> createPackage_state(@RequestBody Package_state package_state) throws URISyntaxException {
        log.debug("REST request to save Package_state : {}", package_state);
        if (package_state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("package_state", "idexists", "A new package_state cannot already have an ID")).body(null);
        }
        Package_state result = package_stateService.save(package_state);
        return ResponseEntity.created(new URI("/api/package-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("package_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package-states : Updates an existing package_state.
     *
     * @param package_state the package_state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated package_state,
     * or with status 400 (Bad Request) if the package_state is not valid,
     * or with status 500 (Internal Server Error) if the package_state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/package-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_state> updatePackage_state(@RequestBody Package_state package_state) throws URISyntaxException {
        log.debug("REST request to update Package_state : {}", package_state);
        if (package_state.getId() == null) {
            return createPackage_state(package_state);
        }
        Package_state result = package_stateService.save(package_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("package_state", package_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package-states : get all the package_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of package_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/package-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Package_state>> getAllPackage_states(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Package_states");
        Page<Package_state> page = package_stateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/package-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /package-states/:id : get the "id" package_state.
     *
     * @param id the id of the package_state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the package_state, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/package-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_state> getPackage_state(@PathVariable Long id) {
        log.debug("REST request to get Package_state : {}", id);
        Package_state package_state = package_stateService.findOne(id);
        return Optional.ofNullable(package_state)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /package-states/:id : delete the "id" package_state.
     *
     * @param id the id of the package_state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/package-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePackage_state(@PathVariable Long id) {
        log.debug("REST request to delete Package_state : {}", id);
        package_stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("package_state", id.toString())).build();
    }

}
