package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_used_vehicle;
import org.megapractical.billingplatform.service.Freecom_used_vehicleService;
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
 * REST controller for managing Freecom_used_vehicle.
 */
@RestController
@RequestMapping("/api")
public class Freecom_used_vehicleResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_used_vehicleResource.class);
        
    @Inject
    private Freecom_used_vehicleService freecom_used_vehicleService;
    
    /**
     * POST  /freecom-used-vehicles : Create a new freecom_used_vehicle.
     *
     * @param freecom_used_vehicle the freecom_used_vehicle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_used_vehicle, or with status 400 (Bad Request) if the freecom_used_vehicle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-used-vehicles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_used_vehicle> createFreecom_used_vehicle(@Valid @RequestBody Freecom_used_vehicle freecom_used_vehicle) throws URISyntaxException {
        log.debug("REST request to save Freecom_used_vehicle : {}", freecom_used_vehicle);
        if (freecom_used_vehicle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_used_vehicle", "idexists", "A new freecom_used_vehicle cannot already have an ID")).body(null);
        }
        Freecom_used_vehicle result = freecom_used_vehicleService.save(freecom_used_vehicle);
        return ResponseEntity.created(new URI("/api/freecom-used-vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_used_vehicle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-used-vehicles : Updates an existing freecom_used_vehicle.
     *
     * @param freecom_used_vehicle the freecom_used_vehicle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_used_vehicle,
     * or with status 400 (Bad Request) if the freecom_used_vehicle is not valid,
     * or with status 500 (Internal Server Error) if the freecom_used_vehicle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-used-vehicles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_used_vehicle> updateFreecom_used_vehicle(@Valid @RequestBody Freecom_used_vehicle freecom_used_vehicle) throws URISyntaxException {
        log.debug("REST request to update Freecom_used_vehicle : {}", freecom_used_vehicle);
        if (freecom_used_vehicle.getId() == null) {
            return createFreecom_used_vehicle(freecom_used_vehicle);
        }
        Freecom_used_vehicle result = freecom_used_vehicleService.save(freecom_used_vehicle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_used_vehicle", freecom_used_vehicle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-used-vehicles : get all the freecom_used_vehicles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_used_vehicles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-used-vehicles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_used_vehicle>> getAllFreecom_used_vehicles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_used_vehicles");
        Page<Freecom_used_vehicle> page = freecom_used_vehicleService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-used-vehicles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-used-vehicles/:id : get the "id" freecom_used_vehicle.
     *
     * @param id the id of the freecom_used_vehicle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_used_vehicle, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-used-vehicles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_used_vehicle> getFreecom_used_vehicle(@PathVariable Long id) {
        log.debug("REST request to get Freecom_used_vehicle : {}", id);
        Freecom_used_vehicle freecom_used_vehicle = freecom_used_vehicleService.findOne(id);
        return Optional.ofNullable(freecom_used_vehicle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-used-vehicles/:id : delete the "id" freecom_used_vehicle.
     *
     * @param id the id of the freecom_used_vehicle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-used-vehicles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_used_vehicle(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_used_vehicle : {}", id);
        freecom_used_vehicleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_used_vehicle", id.toString())).build();
    }

}
