package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_commodity;
import org.megapractical.billingplatform.service.Com_commodityService;
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
 * REST controller for managing Com_commodity.
 */
@RestController
@RequestMapping("/api")
public class Com_commodityResource {

    private final Logger log = LoggerFactory.getLogger(Com_commodityResource.class);
        
    @Inject
    private Com_commodityService com_commodityService;
    
    /**
     * POST  /com-commodities : Create a new com_commodity.
     *
     * @param com_commodity the com_commodity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_commodity, or with status 400 (Bad Request) if the com_commodity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-commodities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_commodity> createCom_commodity(@Valid @RequestBody Com_commodity com_commodity) throws URISyntaxException {
        log.debug("REST request to save Com_commodity : {}", com_commodity);
        if (com_commodity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_commodity", "idexists", "A new com_commodity cannot already have an ID")).body(null);
        }
        Com_commodity result = com_commodityService.save(com_commodity);
        return ResponseEntity.created(new URI("/api/com-commodities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_commodity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-commodities : Updates an existing com_commodity.
     *
     * @param com_commodity the com_commodity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_commodity,
     * or with status 400 (Bad Request) if the com_commodity is not valid,
     * or with status 500 (Internal Server Error) if the com_commodity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-commodities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_commodity> updateCom_commodity(@Valid @RequestBody Com_commodity com_commodity) throws URISyntaxException {
        log.debug("REST request to update Com_commodity : {}", com_commodity);
        if (com_commodity.getId() == null) {
            return createCom_commodity(com_commodity);
        }
        Com_commodity result = com_commodityService.save(com_commodity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_commodity", com_commodity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-commodities : get all the com_commodities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_commodities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-commodities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_commodity>> getAllCom_commodities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_commodities");
        Page<Com_commodity> page = com_commodityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-commodities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-commodities/:id : get the "id" com_commodity.
     *
     * @param id the id of the com_commodity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_commodity, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-commodities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_commodity> getCom_commodity(@PathVariable Long id) {
        log.debug("REST request to get Com_commodity : {}", id);
        Com_commodity com_commodity = com_commodityService.findOne(id);
        return Optional.ofNullable(com_commodity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-commodities/:id : delete the "id" com_commodity.
     *
     * @param id the id of the com_commodity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-commodities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_commodity(@PathVariable Long id) {
        log.debug("REST request to delete Com_commodity : {}", id);
        com_commodityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_commodity", id.toString())).build();
    }

}
