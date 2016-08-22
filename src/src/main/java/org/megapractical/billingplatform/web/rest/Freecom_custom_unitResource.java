package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_custom_unit;
import org.megapractical.billingplatform.service.Freecom_custom_unitService;
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
 * REST controller for managing Freecom_custom_unit.
 */
@RestController
@RequestMapping("/api")
public class Freecom_custom_unitResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_custom_unitResource.class);
        
    @Inject
    private Freecom_custom_unitService freecom_custom_unitService;
    
    /**
     * POST  /freecom-custom-units : Create a new freecom_custom_unit.
     *
     * @param freecom_custom_unit the freecom_custom_unit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_custom_unit, or with status 400 (Bad Request) if the freecom_custom_unit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-custom-units",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_custom_unit> createFreecom_custom_unit(@Valid @RequestBody Freecom_custom_unit freecom_custom_unit) throws URISyntaxException {
        log.debug("REST request to save Freecom_custom_unit : {}", freecom_custom_unit);
        if (freecom_custom_unit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_custom_unit", "idexists", "A new freecom_custom_unit cannot already have an ID")).body(null);
        }
        Freecom_custom_unit result = freecom_custom_unitService.save(freecom_custom_unit);
        return ResponseEntity.created(new URI("/api/freecom-custom-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_custom_unit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-custom-units : Updates an existing freecom_custom_unit.
     *
     * @param freecom_custom_unit the freecom_custom_unit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_custom_unit,
     * or with status 400 (Bad Request) if the freecom_custom_unit is not valid,
     * or with status 500 (Internal Server Error) if the freecom_custom_unit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-custom-units",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_custom_unit> updateFreecom_custom_unit(@Valid @RequestBody Freecom_custom_unit freecom_custom_unit) throws URISyntaxException {
        log.debug("REST request to update Freecom_custom_unit : {}", freecom_custom_unit);
        if (freecom_custom_unit.getId() == null) {
            return createFreecom_custom_unit(freecom_custom_unit);
        }
        Freecom_custom_unit result = freecom_custom_unitService.save(freecom_custom_unit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_custom_unit", freecom_custom_unit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-custom-units : get all the freecom_custom_units.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_custom_units in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-custom-units",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_custom_unit>> getAllFreecom_custom_units(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_custom_units");
        Page<Freecom_custom_unit> page = freecom_custom_unitService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-custom-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-custom-units/:id : get the "id" freecom_custom_unit.
     *
     * @param id the id of the freecom_custom_unit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_custom_unit, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-custom-units/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_custom_unit> getFreecom_custom_unit(@PathVariable Long id) {
        log.debug("REST request to get Freecom_custom_unit : {}", id);
        Freecom_custom_unit freecom_custom_unit = freecom_custom_unitService.findOne(id);
        return Optional.ofNullable(freecom_custom_unit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-custom-units/:id : delete the "id" freecom_custom_unit.
     *
     * @param id the id of the freecom_custom_unit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-custom-units/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_custom_unit(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_custom_unit : {}", id);
        freecom_custom_unitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_custom_unit", id.toString())).build();
    }

}
