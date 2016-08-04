package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_vehicle_customs_information;
import org.megapractical.billingplatform.service.Freecom_vehicle_customs_informationService;
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
 * REST controller for managing Freecom_vehicle_customs_information.
 */
@RestController
@RequestMapping("/api")
public class Freecom_vehicle_customs_informationResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_vehicle_customs_informationResource.class);
        
    @Inject
    private Freecom_vehicle_customs_informationService freecom_vehicle_customs_informationService;
    
    /**
     * POST  /freecom-vehicle-customs-informations : Create a new freecom_vehicle_customs_information.
     *
     * @param freecom_vehicle_customs_information the freecom_vehicle_customs_information to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_vehicle_customs_information, or with status 400 (Bad Request) if the freecom_vehicle_customs_information has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-vehicle-customs-informations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_vehicle_customs_information> createFreecom_vehicle_customs_information(@Valid @RequestBody Freecom_vehicle_customs_information freecom_vehicle_customs_information) throws URISyntaxException {
        log.debug("REST request to save Freecom_vehicle_customs_information : {}", freecom_vehicle_customs_information);
        if (freecom_vehicle_customs_information.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_vehicle_customs_information", "idexists", "A new freecom_vehicle_customs_information cannot already have an ID")).body(null);
        }
        Freecom_vehicle_customs_information result = freecom_vehicle_customs_informationService.save(freecom_vehicle_customs_information);
        return ResponseEntity.created(new URI("/api/freecom-vehicle-customs-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_vehicle_customs_information", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-vehicle-customs-informations : Updates an existing freecom_vehicle_customs_information.
     *
     * @param freecom_vehicle_customs_information the freecom_vehicle_customs_information to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_vehicle_customs_information,
     * or with status 400 (Bad Request) if the freecom_vehicle_customs_information is not valid,
     * or with status 500 (Internal Server Error) if the freecom_vehicle_customs_information couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-vehicle-customs-informations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_vehicle_customs_information> updateFreecom_vehicle_customs_information(@Valid @RequestBody Freecom_vehicle_customs_information freecom_vehicle_customs_information) throws URISyntaxException {
        log.debug("REST request to update Freecom_vehicle_customs_information : {}", freecom_vehicle_customs_information);
        if (freecom_vehicle_customs_information.getId() == null) {
            return createFreecom_vehicle_customs_information(freecom_vehicle_customs_information);
        }
        Freecom_vehicle_customs_information result = freecom_vehicle_customs_informationService.save(freecom_vehicle_customs_information);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_vehicle_customs_information", freecom_vehicle_customs_information.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-vehicle-customs-informations : get all the freecom_vehicle_customs_informations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_vehicle_customs_informations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-vehicle-customs-informations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_vehicle_customs_information>> getAllFreecom_vehicle_customs_informations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_vehicle_customs_informations");
        Page<Freecom_vehicle_customs_information> page = freecom_vehicle_customs_informationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-vehicle-customs-informations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-vehicle-customs-informations/:id : get the "id" freecom_vehicle_customs_information.
     *
     * @param id the id of the freecom_vehicle_customs_information to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_vehicle_customs_information, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-vehicle-customs-informations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_vehicle_customs_information> getFreecom_vehicle_customs_information(@PathVariable Long id) {
        log.debug("REST request to get Freecom_vehicle_customs_information : {}", id);
        Freecom_vehicle_customs_information freecom_vehicle_customs_information = freecom_vehicle_customs_informationService.findOne(id);
        return Optional.ofNullable(freecom_vehicle_customs_information)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-vehicle-customs-informations/:id : delete the "id" freecom_vehicle_customs_information.
     *
     * @param id the id of the freecom_vehicle_customs_information to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-vehicle-customs-informations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_vehicle_customs_information(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_vehicle_customs_information : {}", id);
        freecom_vehicle_customs_informationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_vehicle_customs_information", id.toString())).build();
    }

}
