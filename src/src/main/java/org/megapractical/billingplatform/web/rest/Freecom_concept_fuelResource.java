package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_concept_fuel;
import org.megapractical.billingplatform.service.Freecom_concept_fuelService;
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
 * REST controller for managing Freecom_concept_fuel.
 */
@RestController
@RequestMapping("/api")
public class Freecom_concept_fuelResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_concept_fuelResource.class);
        
    @Inject
    private Freecom_concept_fuelService freecom_concept_fuelService;
    
    /**
     * POST  /freecom-concept-fuels : Create a new freecom_concept_fuel.
     *
     * @param freecom_concept_fuel the freecom_concept_fuel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_concept_fuel, or with status 400 (Bad Request) if the freecom_concept_fuel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-concept-fuels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_concept_fuel> createFreecom_concept_fuel(@Valid @RequestBody Freecom_concept_fuel freecom_concept_fuel) throws URISyntaxException {
        log.debug("REST request to save Freecom_concept_fuel : {}", freecom_concept_fuel);
        if (freecom_concept_fuel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_concept_fuel", "idexists", "A new freecom_concept_fuel cannot already have an ID")).body(null);
        }
        Freecom_concept_fuel result = freecom_concept_fuelService.save(freecom_concept_fuel);
        return ResponseEntity.created(new URI("/api/freecom-concept-fuels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_concept_fuel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-concept-fuels : Updates an existing freecom_concept_fuel.
     *
     * @param freecom_concept_fuel the freecom_concept_fuel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_concept_fuel,
     * or with status 400 (Bad Request) if the freecom_concept_fuel is not valid,
     * or with status 500 (Internal Server Error) if the freecom_concept_fuel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-concept-fuels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_concept_fuel> updateFreecom_concept_fuel(@Valid @RequestBody Freecom_concept_fuel freecom_concept_fuel) throws URISyntaxException {
        log.debug("REST request to update Freecom_concept_fuel : {}", freecom_concept_fuel);
        if (freecom_concept_fuel.getId() == null) {
            return createFreecom_concept_fuel(freecom_concept_fuel);
        }
        Freecom_concept_fuel result = freecom_concept_fuelService.save(freecom_concept_fuel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_concept_fuel", freecom_concept_fuel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-concept-fuels : get all the freecom_concept_fuels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_concept_fuels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-concept-fuels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_concept_fuel>> getAllFreecom_concept_fuels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_concept_fuels");
        Page<Freecom_concept_fuel> page = freecom_concept_fuelService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-concept-fuels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-concept-fuels/:id : get the "id" freecom_concept_fuel.
     *
     * @param id the id of the freecom_concept_fuel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_concept_fuel, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-concept-fuels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_concept_fuel> getFreecom_concept_fuel(@PathVariable Long id) {
        log.debug("REST request to get Freecom_concept_fuel : {}", id);
        Freecom_concept_fuel freecom_concept_fuel = freecom_concept_fuelService.findOne(id);
        return Optional.ofNullable(freecom_concept_fuel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-concept-fuels/:id : delete the "id" freecom_concept_fuel.
     *
     * @param id the id of the freecom_concept_fuel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-concept-fuels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_concept_fuel(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_concept_fuel : {}", id);
        freecom_concept_fuelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_concept_fuel", id.toString())).build();
    }

}
