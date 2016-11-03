package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_concept_fuel;
import org.megapractical.billingplatform.service.Com_concept_fuelService;
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
 * REST controller for managing Com_concept_fuel.
 */
@RestController
@RequestMapping("/api")
public class Com_concept_fuelResource {

    private final Logger log = LoggerFactory.getLogger(Com_concept_fuelResource.class);
        
    @Inject
    private Com_concept_fuelService com_concept_fuelService;
    
    /**
     * POST  /com-concept-fuels : Create a new com_concept_fuel.
     *
     * @param com_concept_fuel the com_concept_fuel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_concept_fuel, or with status 400 (Bad Request) if the com_concept_fuel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-concept-fuels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_concept_fuel> createCom_concept_fuel(@Valid @RequestBody Com_concept_fuel com_concept_fuel) throws URISyntaxException {
        log.debug("REST request to save Com_concept_fuel : {}", com_concept_fuel);
        if (com_concept_fuel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_concept_fuel", "idexists", "A new com_concept_fuel cannot already have an ID")).body(null);
        }
        Com_concept_fuel result = com_concept_fuelService.save(com_concept_fuel);
        return ResponseEntity.created(new URI("/api/com-concept-fuels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_concept_fuel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-concept-fuels : Updates an existing com_concept_fuel.
     *
     * @param com_concept_fuel the com_concept_fuel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_concept_fuel,
     * or with status 400 (Bad Request) if the com_concept_fuel is not valid,
     * or with status 500 (Internal Server Error) if the com_concept_fuel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-concept-fuels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_concept_fuel> updateCom_concept_fuel(@Valid @RequestBody Com_concept_fuel com_concept_fuel) throws URISyntaxException {
        log.debug("REST request to update Com_concept_fuel : {}", com_concept_fuel);
        if (com_concept_fuel.getId() == null) {
            return createCom_concept_fuel(com_concept_fuel);
        }
        Com_concept_fuel result = com_concept_fuelService.save(com_concept_fuel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_concept_fuel", com_concept_fuel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-concept-fuels : get all the com_concept_fuels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_concept_fuels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-concept-fuels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_concept_fuel>> getAllCom_concept_fuels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_concept_fuels");
        Page<Com_concept_fuel> page = com_concept_fuelService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-concept-fuels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-concept-fuels/:id : get the "id" com_concept_fuel.
     *
     * @param id the id of the com_concept_fuel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_concept_fuel, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-concept-fuels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_concept_fuel> getCom_concept_fuel(@PathVariable Long id) {
        log.debug("REST request to get Com_concept_fuel : {}", id);
        Com_concept_fuel com_concept_fuel = com_concept_fuelService.findOne(id);
        return Optional.ofNullable(com_concept_fuel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-concept-fuels/:id : delete the "id" com_concept_fuel.
     *
     * @param id the id of the com_concept_fuel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-concept-fuels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_concept_fuel(@PathVariable Long id) {
        log.debug("REST request to delete Com_concept_fuel : {}", id);
        com_concept_fuelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_concept_fuel", id.toString())).build();
    }

}
