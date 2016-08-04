package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_storeroom_paybill;
import org.megapractical.billingplatform.service.Freecom_storeroom_paybillService;
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
 * REST controller for managing Freecom_storeroom_paybill.
 */
@RestController
@RequestMapping("/api")
public class Freecom_storeroom_paybillResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_storeroom_paybillResource.class);
        
    @Inject
    private Freecom_storeroom_paybillService freecom_storeroom_paybillService;
    
    /**
     * POST  /freecom-storeroom-paybills : Create a new freecom_storeroom_paybill.
     *
     * @param freecom_storeroom_paybill the freecom_storeroom_paybill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_storeroom_paybill, or with status 400 (Bad Request) if the freecom_storeroom_paybill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-storeroom-paybills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_storeroom_paybill> createFreecom_storeroom_paybill(@Valid @RequestBody Freecom_storeroom_paybill freecom_storeroom_paybill) throws URISyntaxException {
        log.debug("REST request to save Freecom_storeroom_paybill : {}", freecom_storeroom_paybill);
        if (freecom_storeroom_paybill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_storeroom_paybill", "idexists", "A new freecom_storeroom_paybill cannot already have an ID")).body(null);
        }
        Freecom_storeroom_paybill result = freecom_storeroom_paybillService.save(freecom_storeroom_paybill);
        return ResponseEntity.created(new URI("/api/freecom-storeroom-paybills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_storeroom_paybill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-storeroom-paybills : Updates an existing freecom_storeroom_paybill.
     *
     * @param freecom_storeroom_paybill the freecom_storeroom_paybill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_storeroom_paybill,
     * or with status 400 (Bad Request) if the freecom_storeroom_paybill is not valid,
     * or with status 500 (Internal Server Error) if the freecom_storeroom_paybill couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-storeroom-paybills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_storeroom_paybill> updateFreecom_storeroom_paybill(@Valid @RequestBody Freecom_storeroom_paybill freecom_storeroom_paybill) throws URISyntaxException {
        log.debug("REST request to update Freecom_storeroom_paybill : {}", freecom_storeroom_paybill);
        if (freecom_storeroom_paybill.getId() == null) {
            return createFreecom_storeroom_paybill(freecom_storeroom_paybill);
        }
        Freecom_storeroom_paybill result = freecom_storeroom_paybillService.save(freecom_storeroom_paybill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_storeroom_paybill", freecom_storeroom_paybill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-storeroom-paybills : get all the freecom_storeroom_paybills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_storeroom_paybills in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-storeroom-paybills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_storeroom_paybill>> getAllFreecom_storeroom_paybills(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_storeroom_paybills");
        Page<Freecom_storeroom_paybill> page = freecom_storeroom_paybillService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-storeroom-paybills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-storeroom-paybills/:id : get the "id" freecom_storeroom_paybill.
     *
     * @param id the id of the freecom_storeroom_paybill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_storeroom_paybill, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-storeroom-paybills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_storeroom_paybill> getFreecom_storeroom_paybill(@PathVariable Long id) {
        log.debug("REST request to get Freecom_storeroom_paybill : {}", id);
        Freecom_storeroom_paybill freecom_storeroom_paybill = freecom_storeroom_paybillService.findOne(id);
        return Optional.ofNullable(freecom_storeroom_paybill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-storeroom-paybills/:id : delete the "id" freecom_storeroom_paybill.
     *
     * @param id the id of the freecom_storeroom_paybill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-storeroom-paybills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_storeroom_paybill(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_storeroom_paybill : {}", id);
        freecom_storeroom_paybillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_storeroom_paybill", id.toString())).build();
    }

}
