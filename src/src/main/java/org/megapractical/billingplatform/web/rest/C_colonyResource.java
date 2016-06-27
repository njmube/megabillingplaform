package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_colony;
import org.megapractical.billingplatform.service.C_colonyService;
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
 * REST controller for managing C_colony.
 */
@RestController
@RequestMapping("/api")
public class C_colonyResource {

    private final Logger log = LoggerFactory.getLogger(C_colonyResource.class);

    @Inject
    private C_colonyService c_colonyService;

    /**
     * POST  /c-colonies : Create a new c_colony.
     *
     * @param c_colony the c_colony to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_colony, or with status 400 (Bad Request) if the c_colony has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-colonies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_colony> createC_colony(@Valid @RequestBody C_colony c_colony) throws URISyntaxException {
        log.debug("REST request to save C_colony : {}", c_colony);
        if (c_colony.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_colony", "idexists", "A new c_colony cannot already have an ID")).body(null);
        }
        C_colony result = c_colonyService.save(c_colony);
        return ResponseEntity.created(new URI("/api/c-colonies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_colony", result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/c-coloniesbylocation" ,
        method = RequestMethod.GET,
        params = {"locationId"},
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<C_colony>> findByLocation(
        @RequestParam(value = "locationId") Long locationId) throws URISyntaxException {
        List<C_colony> page = c_colonyService.findByLocation(locationId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * PUT  /c-colonies : Updates an existing c_colony.
     *
     * @param c_colony the c_colony to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_colony,
     * or with status 400 (Bad Request) if the c_colony is not valid,
     * or with status 500 (Internal Server Error) if the c_colony couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-colonies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_colony> updateC_colony(@Valid @RequestBody C_colony c_colony) throws URISyntaxException {
        log.debug("REST request to update C_colony : {}", c_colony);
        if (c_colony.getId() == null) {
            return createC_colony(c_colony);
        }
        C_colony result = c_colonyService.save(c_colony);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_colony", c_colony.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-colonies : get all the c_colonies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_colonies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-colonies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_colony>> getAllC_colonies(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_colonies");
        Page<C_colony> page = c_colonyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-colonies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-colonies/:id : get the "id" c_colony.
     *
     * @param id the id of the c_colony to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_colony, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-colonies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_colony> getC_colony(@PathVariable Long id) {
        log.debug("REST request to get C_colony : {}", id);
        C_colony c_colony = c_colonyService.findOne(id);
        return Optional.ofNullable(c_colony)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-colonies/:id : delete the "id" c_colony.
     *
     * @param id the id of the c_colony to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-colonies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_colony(@PathVariable Long id) {
        log.debug("REST request to delete C_colony : {}", id);
        c_colonyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_colony", id.toString())).build();
    }

}
