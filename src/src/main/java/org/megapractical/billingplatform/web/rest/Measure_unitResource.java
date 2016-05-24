package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Measure_unit;
import org.megapractical.billingplatform.service.Measure_unitService;
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
 * REST controller for managing Measure_unit.
 */
@RestController
@RequestMapping("/api")
public class Measure_unitResource {

    private final Logger log = LoggerFactory.getLogger(Measure_unitResource.class);
        
    @Inject
    private Measure_unitService measure_unitService;
    
    /**
     * POST  /measure-units : Create a new measure_unit.
     *
     * @param measure_unit the measure_unit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new measure_unit, or with status 400 (Bad Request) if the measure_unit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/measure-units",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Measure_unit> createMeasure_unit(@RequestBody Measure_unit measure_unit) throws URISyntaxException {
        log.debug("REST request to save Measure_unit : {}", measure_unit);
        if (measure_unit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("measure_unit", "idexists", "A new measure_unit cannot already have an ID")).body(null);
        }
        Measure_unit result = measure_unitService.save(measure_unit);
        return ResponseEntity.created(new URI("/api/measure-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("measure_unit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /measure-units : Updates an existing measure_unit.
     *
     * @param measure_unit the measure_unit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated measure_unit,
     * or with status 400 (Bad Request) if the measure_unit is not valid,
     * or with status 500 (Internal Server Error) if the measure_unit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/measure-units",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Measure_unit> updateMeasure_unit(@RequestBody Measure_unit measure_unit) throws URISyntaxException {
        log.debug("REST request to update Measure_unit : {}", measure_unit);
        if (measure_unit.getId() == null) {
            return createMeasure_unit(measure_unit);
        }
        Measure_unit result = measure_unitService.save(measure_unit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("measure_unit", measure_unit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /measure-units : get all the measure_units.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of measure_units in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/measure-units",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Measure_unit>> getAllMeasure_units(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Measure_units");
        Page<Measure_unit> page = measure_unitService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/measure-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /measure-units/:id : get the "id" measure_unit.
     *
     * @param id the id of the measure_unit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the measure_unit, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/measure-units/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Measure_unit> getMeasure_unit(@PathVariable Long id) {
        log.debug("REST request to get Measure_unit : {}", id);
        Measure_unit measure_unit = measure_unitService.findOne(id);
        return Optional.ofNullable(measure_unit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /measure-units/:id : delete the "id" measure_unit.
     *
     * @param id the id of the measure_unit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/measure-units/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMeasure_unit(@PathVariable Long id) {
        log.debug("REST request to delete Measure_unit : {}", id);
        measure_unitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("measure_unit", id.toString())).build();
    }

}
