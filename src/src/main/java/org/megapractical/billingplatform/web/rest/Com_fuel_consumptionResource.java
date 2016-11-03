package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_fuel_consumption;
import org.megapractical.billingplatform.service.Com_fuel_consumptionService;
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
 * REST controller for managing Com_fuel_consumption.
 */
@RestController
@RequestMapping("/api")
public class Com_fuel_consumptionResource {

    private final Logger log = LoggerFactory.getLogger(Com_fuel_consumptionResource.class);
        
    @Inject
    private Com_fuel_consumptionService com_fuel_consumptionService;
    
    /**
     * POST  /com-fuel-consumptions : Create a new com_fuel_consumption.
     *
     * @param com_fuel_consumption the com_fuel_consumption to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_fuel_consumption, or with status 400 (Bad Request) if the com_fuel_consumption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-fuel-consumptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_fuel_consumption> createCom_fuel_consumption(@Valid @RequestBody Com_fuel_consumption com_fuel_consumption) throws URISyntaxException {
        log.debug("REST request to save Com_fuel_consumption : {}", com_fuel_consumption);
        if (com_fuel_consumption.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_fuel_consumption", "idexists", "A new com_fuel_consumption cannot already have an ID")).body(null);
        }
        Com_fuel_consumption result = com_fuel_consumptionService.save(com_fuel_consumption);
        return ResponseEntity.created(new URI("/api/com-fuel-consumptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_fuel_consumption", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-fuel-consumptions : Updates an existing com_fuel_consumption.
     *
     * @param com_fuel_consumption the com_fuel_consumption to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_fuel_consumption,
     * or with status 400 (Bad Request) if the com_fuel_consumption is not valid,
     * or with status 500 (Internal Server Error) if the com_fuel_consumption couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-fuel-consumptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_fuel_consumption> updateCom_fuel_consumption(@Valid @RequestBody Com_fuel_consumption com_fuel_consumption) throws URISyntaxException {
        log.debug("REST request to update Com_fuel_consumption : {}", com_fuel_consumption);
        if (com_fuel_consumption.getId() == null) {
            return createCom_fuel_consumption(com_fuel_consumption);
        }
        Com_fuel_consumption result = com_fuel_consumptionService.save(com_fuel_consumption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_fuel_consumption", com_fuel_consumption.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-fuel-consumptions : get all the com_fuel_consumptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_fuel_consumptions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-fuel-consumptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_fuel_consumption>> getAllCom_fuel_consumptions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_fuel_consumptions");
        Page<Com_fuel_consumption> page = com_fuel_consumptionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-fuel-consumptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-fuel-consumptions/:id : get the "id" com_fuel_consumption.
     *
     * @param id the id of the com_fuel_consumption to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_fuel_consumption, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-fuel-consumptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_fuel_consumption> getCom_fuel_consumption(@PathVariable Long id) {
        log.debug("REST request to get Com_fuel_consumption : {}", id);
        Com_fuel_consumption com_fuel_consumption = com_fuel_consumptionService.findOne(id);
        return Optional.ofNullable(com_fuel_consumption)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-fuel-consumptions/:id : delete the "id" com_fuel_consumption.
     *
     * @param id the id of the com_fuel_consumption to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-fuel-consumptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_fuel_consumption(@PathVariable Long id) {
        log.debug("REST request to delete Com_fuel_consumption : {}", id);
        com_fuel_consumptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_fuel_consumption", id.toString())).build();
    }

}
