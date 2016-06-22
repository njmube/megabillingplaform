package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Cfdi_states;
import org.megapractical.billingplatform.service.Cfdi_statesService;
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
 * REST controller for managing Cfdi_states.
 */
@RestController
@RequestMapping("/api")
public class Cfdi_statesResource {

    private final Logger log = LoggerFactory.getLogger(Cfdi_statesResource.class);
        
    @Inject
    private Cfdi_statesService cfdi_statesService;
    
    /**
     * POST  /cfdi-states : Create a new cfdi_states.
     *
     * @param cfdi_states the cfdi_states to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfdi_states, or with status 400 (Bad Request) if the cfdi_states has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_states> createCfdi_states(@Valid @RequestBody Cfdi_states cfdi_states) throws URISyntaxException {
        log.debug("REST request to save Cfdi_states : {}", cfdi_states);
        if (cfdi_states.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cfdi_states", "idexists", "A new cfdi_states cannot already have an ID")).body(null);
        }
        Cfdi_states result = cfdi_statesService.save(cfdi_states);
        return ResponseEntity.created(new URI("/api/cfdi-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cfdi_states", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfdi-states : Updates an existing cfdi_states.
     *
     * @param cfdi_states the cfdi_states to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfdi_states,
     * or with status 400 (Bad Request) if the cfdi_states is not valid,
     * or with status 500 (Internal Server Error) if the cfdi_states couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_states> updateCfdi_states(@Valid @RequestBody Cfdi_states cfdi_states) throws URISyntaxException {
        log.debug("REST request to update Cfdi_states : {}", cfdi_states);
        if (cfdi_states.getId() == null) {
            return createCfdi_states(cfdi_states);
        }
        Cfdi_states result = cfdi_statesService.save(cfdi_states);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfdi_states", cfdi_states.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfdi-states : get all the cfdi_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cfdi_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cfdi-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cfdi_states>> getAllCfdi_states(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cfdi_states");
        Page<Cfdi_states> page = cfdi_statesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfdi-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cfdi-states/:id : get the "id" cfdi_states.
     *
     * @param id the id of the cfdi_states to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfdi_states, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cfdi-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_states> getCfdi_states(@PathVariable Long id) {
        log.debug("REST request to get Cfdi_states : {}", id);
        Cfdi_states cfdi_states = cfdi_statesService.findOne(id);
        return Optional.ofNullable(cfdi_states)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cfdi-states/:id : delete the "id" cfdi_states.
     *
     * @param id the id of the cfdi_states to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cfdi-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCfdi_states(@PathVariable Long id) {
        log.debug("REST request to delete Cfdi_states : {}", id);
        cfdi_statesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cfdi_states", id.toString())).build();
    }

}
