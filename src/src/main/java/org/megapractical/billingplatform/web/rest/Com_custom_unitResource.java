package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_custom_unit;
import org.megapractical.billingplatform.service.Com_custom_unitService;
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
 * REST controller for managing Com_custom_unit.
 */
@RestController
@RequestMapping("/api")
public class Com_custom_unitResource {

    private final Logger log = LoggerFactory.getLogger(Com_custom_unitResource.class);
        
    @Inject
    private Com_custom_unitService com_custom_unitService;
    
    /**
     * POST  /com-custom-units : Create a new com_custom_unit.
     *
     * @param com_custom_unit the com_custom_unit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_custom_unit, or with status 400 (Bad Request) if the com_custom_unit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-custom-units",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_custom_unit> createCom_custom_unit(@Valid @RequestBody Com_custom_unit com_custom_unit) throws URISyntaxException {
        log.debug("REST request to save Com_custom_unit : {}", com_custom_unit);
        if (com_custom_unit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_custom_unit", "idexists", "A new com_custom_unit cannot already have an ID")).body(null);
        }
        Com_custom_unit result = com_custom_unitService.save(com_custom_unit);
        return ResponseEntity.created(new URI("/api/com-custom-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_custom_unit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-custom-units : Updates an existing com_custom_unit.
     *
     * @param com_custom_unit the com_custom_unit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_custom_unit,
     * or with status 400 (Bad Request) if the com_custom_unit is not valid,
     * or with status 500 (Internal Server Error) if the com_custom_unit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-custom-units",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_custom_unit> updateCom_custom_unit(@Valid @RequestBody Com_custom_unit com_custom_unit) throws URISyntaxException {
        log.debug("REST request to update Com_custom_unit : {}", com_custom_unit);
        if (com_custom_unit.getId() == null) {
            return createCom_custom_unit(com_custom_unit);
        }
        Com_custom_unit result = com_custom_unitService.save(com_custom_unit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_custom_unit", com_custom_unit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-custom-units : get all the com_custom_units.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_custom_units in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-custom-units",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_custom_unit>> getAllCom_custom_units(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_custom_units");
        Page<Com_custom_unit> page = com_custom_unitService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-custom-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-custom-units/:id : get the "id" com_custom_unit.
     *
     * @param id the id of the com_custom_unit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_custom_unit, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-custom-units/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_custom_unit> getCom_custom_unit(@PathVariable Long id) {
        log.debug("REST request to get Com_custom_unit : {}", id);
        Com_custom_unit com_custom_unit = com_custom_unitService.findOne(id);
        return Optional.ofNullable(com_custom_unit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-custom-units/:id : delete the "id" com_custom_unit.
     *
     * @param id the id of the com_custom_unit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-custom-units/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_custom_unit(@PathVariable Long id) {
        log.debug("REST request to delete Com_custom_unit : {}", id);
        com_custom_unitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_custom_unit", id.toString())).build();
    }

}
