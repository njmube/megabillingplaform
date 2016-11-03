package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_vehicle_customs_information;
import org.megapractical.billingplatform.service.Com_vehicle_customs_informationService;
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
 * REST controller for managing Com_vehicle_customs_information.
 */
@RestController
@RequestMapping("/api")
public class Com_vehicle_customs_informationResource {

    private final Logger log = LoggerFactory.getLogger(Com_vehicle_customs_informationResource.class);
        
    @Inject
    private Com_vehicle_customs_informationService com_vehicle_customs_informationService;
    
    /**
     * POST  /com-vehicle-customs-informations : Create a new com_vehicle_customs_information.
     *
     * @param com_vehicle_customs_information the com_vehicle_customs_information to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_vehicle_customs_information, or with status 400 (Bad Request) if the com_vehicle_customs_information has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-vehicle-customs-informations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_vehicle_customs_information> createCom_vehicle_customs_information(@Valid @RequestBody Com_vehicle_customs_information com_vehicle_customs_information) throws URISyntaxException {
        log.debug("REST request to save Com_vehicle_customs_information : {}", com_vehicle_customs_information);
        if (com_vehicle_customs_information.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_vehicle_customs_information", "idexists", "A new com_vehicle_customs_information cannot already have an ID")).body(null);
        }
        Com_vehicle_customs_information result = com_vehicle_customs_informationService.save(com_vehicle_customs_information);
        return ResponseEntity.created(new URI("/api/com-vehicle-customs-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_vehicle_customs_information", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-vehicle-customs-informations : Updates an existing com_vehicle_customs_information.
     *
     * @param com_vehicle_customs_information the com_vehicle_customs_information to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_vehicle_customs_information,
     * or with status 400 (Bad Request) if the com_vehicle_customs_information is not valid,
     * or with status 500 (Internal Server Error) if the com_vehicle_customs_information couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-vehicle-customs-informations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_vehicle_customs_information> updateCom_vehicle_customs_information(@Valid @RequestBody Com_vehicle_customs_information com_vehicle_customs_information) throws URISyntaxException {
        log.debug("REST request to update Com_vehicle_customs_information : {}", com_vehicle_customs_information);
        if (com_vehicle_customs_information.getId() == null) {
            return createCom_vehicle_customs_information(com_vehicle_customs_information);
        }
        Com_vehicle_customs_information result = com_vehicle_customs_informationService.save(com_vehicle_customs_information);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_vehicle_customs_information", com_vehicle_customs_information.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-vehicle-customs-informations : get all the com_vehicle_customs_informations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_vehicle_customs_informations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-vehicle-customs-informations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_vehicle_customs_information>> getAllCom_vehicle_customs_informations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_vehicle_customs_informations");
        Page<Com_vehicle_customs_information> page = com_vehicle_customs_informationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-vehicle-customs-informations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-vehicle-customs-informations/:id : get the "id" com_vehicle_customs_information.
     *
     * @param id the id of the com_vehicle_customs_information to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_vehicle_customs_information, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-vehicle-customs-informations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_vehicle_customs_information> getCom_vehicle_customs_information(@PathVariable Long id) {
        log.debug("REST request to get Com_vehicle_customs_information : {}", id);
        Com_vehicle_customs_information com_vehicle_customs_information = com_vehicle_customs_informationService.findOne(id);
        return Optional.ofNullable(com_vehicle_customs_information)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-vehicle-customs-informations/:id : delete the "id" com_vehicle_customs_information.
     *
     * @param id the id of the com_vehicle_customs_information to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-vehicle-customs-informations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_vehicle_customs_information(@PathVariable Long id) {
        log.debug("REST request to delete Com_vehicle_customs_information : {}", id);
        com_vehicle_customs_informationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_vehicle_customs_information", id.toString())).build();
    }

}
