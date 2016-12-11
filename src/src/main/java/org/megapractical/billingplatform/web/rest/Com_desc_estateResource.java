package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_desc_estate;
import org.megapractical.billingplatform.service.Com_desc_estateService;
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
 * REST controller for managing Com_desc_estate.
 */
@RestController
@RequestMapping("/api")
public class Com_desc_estateResource {

    private final Logger log = LoggerFactory.getLogger(Com_desc_estateResource.class);
        
    @Inject
    private Com_desc_estateService com_desc_estateService;
    
    /**
     * POST  /com-desc-estates : Create a new com_desc_estate.
     *
     * @param com_desc_estate the com_desc_estate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_desc_estate, or with status 400 (Bad Request) if the com_desc_estate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-desc-estates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_desc_estate> createCom_desc_estate(@Valid @RequestBody Com_desc_estate com_desc_estate) throws URISyntaxException {
        log.debug("REST request to save Com_desc_estate : {}", com_desc_estate);
        if (com_desc_estate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_desc_estate", "idexists", "A new com_desc_estate cannot already have an ID")).body(null);
        }
        Com_desc_estate result = com_desc_estateService.save(com_desc_estate);
        return ResponseEntity.created(new URI("/api/com-desc-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_desc_estate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-desc-estates : Updates an existing com_desc_estate.
     *
     * @param com_desc_estate the com_desc_estate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_desc_estate,
     * or with status 400 (Bad Request) if the com_desc_estate is not valid,
     * or with status 500 (Internal Server Error) if the com_desc_estate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-desc-estates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_desc_estate> updateCom_desc_estate(@Valid @RequestBody Com_desc_estate com_desc_estate) throws URISyntaxException {
        log.debug("REST request to update Com_desc_estate : {}", com_desc_estate);
        if (com_desc_estate.getId() == null) {
            return createCom_desc_estate(com_desc_estate);
        }
        Com_desc_estate result = com_desc_estateService.save(com_desc_estate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_desc_estate", com_desc_estate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-desc-estates : get all the com_desc_estates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_desc_estates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-desc-estates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_desc_estate>> getAllCom_desc_estates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_desc_estates");
        Page<Com_desc_estate> page = com_desc_estateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-desc-estates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-desc-estates/:id : get the "id" com_desc_estate.
     *
     * @param id the id of the com_desc_estate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_desc_estate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-desc-estates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_desc_estate> getCom_desc_estate(@PathVariable Long id) {
        log.debug("REST request to get Com_desc_estate : {}", id);
        Com_desc_estate com_desc_estate = com_desc_estateService.findOne(id);
        return Optional.ofNullable(com_desc_estate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-desc-estates/:id : delete the "id" com_desc_estate.
     *
     * @param id the id of the com_desc_estate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-desc-estates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_desc_estate(@PathVariable Long id) {
        log.debug("REST request to delete Com_desc_estate : {}", id);
        com_desc_estateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_desc_estate", id.toString())).build();
    }

}
