package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_fuel_consumption;
import org.megapractical.billingplatform.service.Freecom_fuel_consumptionService;
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
 * REST controller for managing Freecom_fuel_consumption.
 */
@RestController
@RequestMapping("/api")
public class Freecom_fuel_consumptionResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_fuel_consumptionResource.class);
        
    @Inject
    private Freecom_fuel_consumptionService freecom_fuel_consumptionService;
    
    /**
     * POST  /freecom-fuel-consumptions : Create a new freecom_fuel_consumption.
     *
     * @param freecom_fuel_consumption the freecom_fuel_consumption to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_fuel_consumption, or with status 400 (Bad Request) if the freecom_fuel_consumption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-fuel-consumptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_fuel_consumption> createFreecom_fuel_consumption(@Valid @RequestBody Freecom_fuel_consumption freecom_fuel_consumption) throws URISyntaxException {
        log.debug("REST request to save Freecom_fuel_consumption : {}", freecom_fuel_consumption);
        if (freecom_fuel_consumption.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_fuel_consumption", "idexists", "A new freecom_fuel_consumption cannot already have an ID")).body(null);
        }
        Freecom_fuel_consumption result = freecom_fuel_consumptionService.save(freecom_fuel_consumption);
        return ResponseEntity.created(new URI("/api/freecom-fuel-consumptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_fuel_consumption", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-fuel-consumptions : Updates an existing freecom_fuel_consumption.
     *
     * @param freecom_fuel_consumption the freecom_fuel_consumption to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_fuel_consumption,
     * or with status 400 (Bad Request) if the freecom_fuel_consumption is not valid,
     * or with status 500 (Internal Server Error) if the freecom_fuel_consumption couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-fuel-consumptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_fuel_consumption> updateFreecom_fuel_consumption(@Valid @RequestBody Freecom_fuel_consumption freecom_fuel_consumption) throws URISyntaxException {
        log.debug("REST request to update Freecom_fuel_consumption : {}", freecom_fuel_consumption);
        if (freecom_fuel_consumption.getId() == null) {
            return createFreecom_fuel_consumption(freecom_fuel_consumption);
        }
        Freecom_fuel_consumption result = freecom_fuel_consumptionService.save(freecom_fuel_consumption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_fuel_consumption", freecom_fuel_consumption.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-fuel-consumptions : get all the freecom_fuel_consumptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_fuel_consumptions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-fuel-consumptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_fuel_consumption>> getAllFreecom_fuel_consumptions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_fuel_consumptions");
        Page<Freecom_fuel_consumption> page = freecom_fuel_consumptionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-fuel-consumptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-fuel-consumptions/:id : get the "id" freecom_fuel_consumption.
     *
     * @param id the id of the freecom_fuel_consumption to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_fuel_consumption, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-fuel-consumptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_fuel_consumption> getFreecom_fuel_consumption(@PathVariable Long id) {
        log.debug("REST request to get Freecom_fuel_consumption : {}", id);
        Freecom_fuel_consumption freecom_fuel_consumption = freecom_fuel_consumptionService.findOne(id);
        return Optional.ofNullable(freecom_fuel_consumption)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-fuel-consumptions/:id : delete the "id" freecom_fuel_consumption.
     *
     * @param id the id of the freecom_fuel_consumption to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-fuel-consumptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_fuel_consumption(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_fuel_consumption : {}", id);
        freecom_fuel_consumptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_fuel_consumption", id.toString())).build();
    }

}
