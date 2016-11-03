package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_used_vehicle;
import org.megapractical.billingplatform.service.Com_used_vehicleService;
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
 * REST controller for managing Com_used_vehicle.
 */
@RestController
@RequestMapping("/api")
public class Com_used_vehicleResource {

    private final Logger log = LoggerFactory.getLogger(Com_used_vehicleResource.class);
        
    @Inject
    private Com_used_vehicleService com_used_vehicleService;
    
    /**
     * POST  /com-used-vehicles : Create a new com_used_vehicle.
     *
     * @param com_used_vehicle the com_used_vehicle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_used_vehicle, or with status 400 (Bad Request) if the com_used_vehicle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-used-vehicles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_used_vehicle> createCom_used_vehicle(@Valid @RequestBody Com_used_vehicle com_used_vehicle) throws URISyntaxException {
        log.debug("REST request to save Com_used_vehicle : {}", com_used_vehicle);
        if (com_used_vehicle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_used_vehicle", "idexists", "A new com_used_vehicle cannot already have an ID")).body(null);
        }
        Com_used_vehicle result = com_used_vehicleService.save(com_used_vehicle);
        return ResponseEntity.created(new URI("/api/com-used-vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_used_vehicle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-used-vehicles : Updates an existing com_used_vehicle.
     *
     * @param com_used_vehicle the com_used_vehicle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_used_vehicle,
     * or with status 400 (Bad Request) if the com_used_vehicle is not valid,
     * or with status 500 (Internal Server Error) if the com_used_vehicle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-used-vehicles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_used_vehicle> updateCom_used_vehicle(@Valid @RequestBody Com_used_vehicle com_used_vehicle) throws URISyntaxException {
        log.debug("REST request to update Com_used_vehicle : {}", com_used_vehicle);
        if (com_used_vehicle.getId() == null) {
            return createCom_used_vehicle(com_used_vehicle);
        }
        Com_used_vehicle result = com_used_vehicleService.save(com_used_vehicle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_used_vehicle", com_used_vehicle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-used-vehicles : get all the com_used_vehicles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_used_vehicles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-used-vehicles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_used_vehicle>> getAllCom_used_vehicles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_used_vehicles");
        Page<Com_used_vehicle> page = com_used_vehicleService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-used-vehicles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-used-vehicles/:id : get the "id" com_used_vehicle.
     *
     * @param id the id of the com_used_vehicle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_used_vehicle, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-used-vehicles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_used_vehicle> getCom_used_vehicle(@PathVariable Long id) {
        log.debug("REST request to get Com_used_vehicle : {}", id);
        Com_used_vehicle com_used_vehicle = com_used_vehicleService.findOne(id);
        return Optional.ofNullable(com_used_vehicle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-used-vehicles/:id : delete the "id" com_used_vehicle.
     *
     * @param id the id of the com_used_vehicle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-used-vehicles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_used_vehicle(@PathVariable Long id) {
        log.debug("REST request to delete Com_used_vehicle : {}", id);
        com_used_vehicleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_used_vehicle", id.toString())).build();
    }

}
