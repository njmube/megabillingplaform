package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_municipality;
import org.megapractical.billingplatform.service.C_municipalityService;
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
 * REST controller for managing C_municipality.
 */
@RestController
@RequestMapping("/api")
public class C_municipalityResource {

    private final Logger log = LoggerFactory.getLogger(C_municipalityResource.class);

    @Inject
    private C_municipalityService c_municipalityService;

    /**
     * POST  /c-municipalities : Create a new c_municipality.
     *
     * @param c_municipality the c_municipality to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_municipality, or with status 400 (Bad Request) if the c_municipality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-municipalities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_municipality> createC_municipality(@RequestBody C_municipality c_municipality) throws URISyntaxException {
        log.debug("REST request to save C_municipality : {}", c_municipality);
        if (c_municipality.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_municipality", "idexists", "A new c_municipality cannot already have an ID")).body(null);
        }
        C_municipality result = c_municipalityService.save(c_municipality);
        return ResponseEntity.created(new URI("/api/c-municipalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_municipality", result.getId().toString()))
            .body(result);
    }

    /*@RequestMapping(value = "/c-municipalitiesbystate" ,
        method = RequestMethod.GET,
        params = {"stateId"},
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<C_municipality>> findByState(
        @RequestParam(value = "stateId") Long stateId) throws URISyntaxException {
        List<C_municipality> page = c_municipalityService.findByState(stateId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }*/

    /**
     * PUT  /c-municipalities : Updates an existing c_municipality.
     *
     * @param c_municipality the c_municipality to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_municipality,
     * or with status 400 (Bad Request) if the c_municipality is not valid,
     * or with status 500 (Internal Server Error) if the c_municipality couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-municipalities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_municipality> updateC_municipality(@RequestBody C_municipality c_municipality) throws URISyntaxException {
        log.debug("REST request to update C_municipality : {}", c_municipality);
        if (c_municipality.getId() == null) {
            return createC_municipality(c_municipality);
        }
        C_municipality result = c_municipalityService.save(c_municipality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_municipality", c_municipality.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-municipalities : get all the c_municipalities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_municipalities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-municipalities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"stateId"})
    @Timed
    public ResponseEntity<List<C_municipality>> getAllC_municipalities(@RequestParam(value = "stateId") Integer stateId, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_municipalities");
        if(stateId == 0){
            Page<C_municipality> page = c_municipalityService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-municipalities");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);}
        else
        {
            List<C_municipality> page = c_municipalityService.findByState(stateId);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
    }

    /**
     * GET  /c-municipalities/:id : get the "id" c_municipality.
     *
     * @param id the id of the c_municipality to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_municipality, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-municipalities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_municipality> getC_municipality(@PathVariable Long id) {
        log.debug("REST request to get C_municipality : {}", id);
        C_municipality c_municipality = c_municipalityService.findOne(id);
        return Optional.ofNullable(c_municipality)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-municipalities/:id : delete the "id" c_municipality.
     *
     * @param id the id of the c_municipality to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-municipalities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_municipality(@PathVariable Long id) {
        log.debug("REST request to delete C_municipality : {}", id);
        c_municipalityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_municipality", id.toString())).build();
    }

}
