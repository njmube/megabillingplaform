package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_location;
import org.megapractical.billingplatform.service.C_locationService;
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
 * REST controller for managing C_location.
 */
@RestController
@RequestMapping("/api")
public class C_locationResource {

    private final Logger log = LoggerFactory.getLogger(C_locationResource.class);

    @Inject
    private C_locationService c_locationService;

    /**
     * POST  /c-locations : Create a new c_location.
     *
     * @param c_location the c_location to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_location, or with status 400 (Bad Request) if the c_location has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-locations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_location> createC_location(@RequestBody C_location c_location) throws URISyntaxException {
        log.debug("REST request to save C_location : {}", c_location);
        if (c_location.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_location", "idexists", "A new c_location cannot already have an ID")).body(null);
        }
        C_location result = c_locationService.save(c_location);
        return ResponseEntity.created(new URI("/api/c-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_location", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-locations : Updates an existing c_location.
     *
     * @param c_location the c_location to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_location,
     * or with status 400 (Bad Request) if the c_location is not valid,
     * or with status 500 (Internal Server Error) if the c_location couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-locations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_location> updateC_location(@RequestBody C_location c_location) throws URISyntaxException {
        log.debug("REST request to update C_location : {}", c_location);
        if (c_location.getId() == null) {
            return createC_location(c_location);
        }
        C_location result = c_locationService.save(c_location);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_location", c_location.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/c-locationsbymunicipality" ,
        method = RequestMethod.GET,
        params = {"municipalityId"},
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<C_location>> findByMunicipality(
        @RequestParam(value = "municipalityId") Long municipalityId) throws URISyntaxException {
        List<C_location> page = c_locationService.findByMunicipality(municipalityId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /c-locations : get all the c_locations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_locations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-locations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_location>> getAllC_locations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_locations");
        Page<C_location> page = c_locationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-locations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-locations/:id : get the "id" c_location.
     *
     * @param id the id of the c_location to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_location, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-locations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_location> getC_location(@PathVariable Long id) {
        log.debug("REST request to get C_location : {}", id);
        C_location c_location = c_locationService.findOne(id);
        return Optional.ofNullable(c_location)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-locations/:id : delete the "id" c_location.
     *
     * @param id the id of the c_location to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-locations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_location(@PathVariable Long id) {
        log.debug("REST request to delete C_location : {}", id);
        c_locationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_location", id.toString())).build();
    }

}
