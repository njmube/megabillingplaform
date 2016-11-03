package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_storeroom_paybill;
import org.megapractical.billingplatform.service.Com_storeroom_paybillService;
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
 * REST controller for managing Com_storeroom_paybill.
 */
@RestController
@RequestMapping("/api")
public class Com_storeroom_paybillResource {

    private final Logger log = LoggerFactory.getLogger(Com_storeroom_paybillResource.class);
        
    @Inject
    private Com_storeroom_paybillService com_storeroom_paybillService;
    
    /**
     * POST  /com-storeroom-paybills : Create a new com_storeroom_paybill.
     *
     * @param com_storeroom_paybill the com_storeroom_paybill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_storeroom_paybill, or with status 400 (Bad Request) if the com_storeroom_paybill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-storeroom-paybills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_storeroom_paybill> createCom_storeroom_paybill(@Valid @RequestBody Com_storeroom_paybill com_storeroom_paybill) throws URISyntaxException {
        log.debug("REST request to save Com_storeroom_paybill : {}", com_storeroom_paybill);
        if (com_storeroom_paybill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_storeroom_paybill", "idexists", "A new com_storeroom_paybill cannot already have an ID")).body(null);
        }
        Com_storeroom_paybill result = com_storeroom_paybillService.save(com_storeroom_paybill);
        return ResponseEntity.created(new URI("/api/com-storeroom-paybills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_storeroom_paybill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-storeroom-paybills : Updates an existing com_storeroom_paybill.
     *
     * @param com_storeroom_paybill the com_storeroom_paybill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_storeroom_paybill,
     * or with status 400 (Bad Request) if the com_storeroom_paybill is not valid,
     * or with status 500 (Internal Server Error) if the com_storeroom_paybill couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-storeroom-paybills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_storeroom_paybill> updateCom_storeroom_paybill(@Valid @RequestBody Com_storeroom_paybill com_storeroom_paybill) throws URISyntaxException {
        log.debug("REST request to update Com_storeroom_paybill : {}", com_storeroom_paybill);
        if (com_storeroom_paybill.getId() == null) {
            return createCom_storeroom_paybill(com_storeroom_paybill);
        }
        Com_storeroom_paybill result = com_storeroom_paybillService.save(com_storeroom_paybill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_storeroom_paybill", com_storeroom_paybill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-storeroom-paybills : get all the com_storeroom_paybills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_storeroom_paybills in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-storeroom-paybills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_storeroom_paybill>> getAllCom_storeroom_paybills(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_storeroom_paybills");
        Page<Com_storeroom_paybill> page = com_storeroom_paybillService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-storeroom-paybills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-storeroom-paybills/:id : get the "id" com_storeroom_paybill.
     *
     * @param id the id of the com_storeroom_paybill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_storeroom_paybill, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-storeroom-paybills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_storeroom_paybill> getCom_storeroom_paybill(@PathVariable Long id) {
        log.debug("REST request to get Com_storeroom_paybill : {}", id);
        Com_storeroom_paybill com_storeroom_paybill = com_storeroom_paybillService.findOne(id);
        return Optional.ofNullable(com_storeroom_paybill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-storeroom-paybills/:id : delete the "id" com_storeroom_paybill.
     *
     * @param id the id of the com_storeroom_paybill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-storeroom-paybills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_storeroom_paybill(@PathVariable Long id) {
        log.debug("REST request to delete Com_storeroom_paybill : {}", id);
        com_storeroom_paybillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_storeroom_paybill", id.toString())).build();
    }

}
