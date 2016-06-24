package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_customs_info;
import org.megapractical.billingplatform.service.Free_customs_infoService;
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
 * REST controller for managing Free_customs_info.
 */
@RestController
@RequestMapping("/api")
public class Free_customs_infoResource {

    private final Logger log = LoggerFactory.getLogger(Free_customs_infoResource.class);
        
    @Inject
    private Free_customs_infoService free_customs_infoService;
    
    /**
     * POST  /free-customs-infos : Create a new free_customs_info.
     *
     * @param free_customs_info the free_customs_info to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_customs_info, or with status 400 (Bad Request) if the free_customs_info has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-customs-infos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_customs_info> createFree_customs_info(@Valid @RequestBody Free_customs_info free_customs_info) throws URISyntaxException {
        log.debug("REST request to save Free_customs_info : {}", free_customs_info);
        if (free_customs_info.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_customs_info", "idexists", "A new free_customs_info cannot already have an ID")).body(null);
        }
        Free_customs_info result = free_customs_infoService.save(free_customs_info);
        return ResponseEntity.created(new URI("/api/free-customs-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_customs_info", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-customs-infos : Updates an existing free_customs_info.
     *
     * @param free_customs_info the free_customs_info to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_customs_info,
     * or with status 400 (Bad Request) if the free_customs_info is not valid,
     * or with status 500 (Internal Server Error) if the free_customs_info couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-customs-infos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_customs_info> updateFree_customs_info(@Valid @RequestBody Free_customs_info free_customs_info) throws URISyntaxException {
        log.debug("REST request to update Free_customs_info : {}", free_customs_info);
        if (free_customs_info.getId() == null) {
            return createFree_customs_info(free_customs_info);
        }
        Free_customs_info result = free_customs_infoService.save(free_customs_info);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_customs_info", free_customs_info.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-customs-infos : get all the free_customs_infos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_customs_infos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-customs-infos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_customs_info>> getAllFree_customs_infos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_customs_infos");
        Page<Free_customs_info> page = free_customs_infoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-customs-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-customs-infos/:id : get the "id" free_customs_info.
     *
     * @param id the id of the free_customs_info to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_customs_info, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-customs-infos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_customs_info> getFree_customs_info(@PathVariable Long id) {
        log.debug("REST request to get Free_customs_info : {}", id);
        Free_customs_info free_customs_info = free_customs_infoService.findOne(id);
        return Optional.ofNullable(free_customs_info)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-customs-infos/:id : delete the "id" free_customs_info.
     *
     * @param id the id of the free_customs_info to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-customs-infos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_customs_info(@PathVariable Long id) {
        log.debug("REST request to delete Free_customs_info : {}", id);
        free_customs_infoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_customs_info", id.toString())).build();
    }

}
