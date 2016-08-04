package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_info_customs_destruction;
import org.megapractical.billingplatform.service.Freecom_info_customs_destructionService;
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
 * REST controller for managing Freecom_info_customs_destruction.
 */
@RestController
@RequestMapping("/api")
public class Freecom_info_customs_destructionResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_info_customs_destructionResource.class);
        
    @Inject
    private Freecom_info_customs_destructionService freecom_info_customs_destructionService;
    
    /**
     * POST  /freecom-info-customs-destructions : Create a new freecom_info_customs_destruction.
     *
     * @param freecom_info_customs_destruction the freecom_info_customs_destruction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_info_customs_destruction, or with status 400 (Bad Request) if the freecom_info_customs_destruction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-info-customs-destructions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_info_customs_destruction> createFreecom_info_customs_destruction(@Valid @RequestBody Freecom_info_customs_destruction freecom_info_customs_destruction) throws URISyntaxException {
        log.debug("REST request to save Freecom_info_customs_destruction : {}", freecom_info_customs_destruction);
        if (freecom_info_customs_destruction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_info_customs_destruction", "idexists", "A new freecom_info_customs_destruction cannot already have an ID")).body(null);
        }
        Freecom_info_customs_destruction result = freecom_info_customs_destructionService.save(freecom_info_customs_destruction);
        return ResponseEntity.created(new URI("/api/freecom-info-customs-destructions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_info_customs_destruction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-info-customs-destructions : Updates an existing freecom_info_customs_destruction.
     *
     * @param freecom_info_customs_destruction the freecom_info_customs_destruction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_info_customs_destruction,
     * or with status 400 (Bad Request) if the freecom_info_customs_destruction is not valid,
     * or with status 500 (Internal Server Error) if the freecom_info_customs_destruction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-info-customs-destructions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_info_customs_destruction> updateFreecom_info_customs_destruction(@Valid @RequestBody Freecom_info_customs_destruction freecom_info_customs_destruction) throws URISyntaxException {
        log.debug("REST request to update Freecom_info_customs_destruction : {}", freecom_info_customs_destruction);
        if (freecom_info_customs_destruction.getId() == null) {
            return createFreecom_info_customs_destruction(freecom_info_customs_destruction);
        }
        Freecom_info_customs_destruction result = freecom_info_customs_destructionService.save(freecom_info_customs_destruction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_info_customs_destruction", freecom_info_customs_destruction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-info-customs-destructions : get all the freecom_info_customs_destructions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_info_customs_destructions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-info-customs-destructions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_info_customs_destruction>> getAllFreecom_info_customs_destructions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_info_customs_destructions");
        Page<Freecom_info_customs_destruction> page = freecom_info_customs_destructionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-info-customs-destructions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-info-customs-destructions/:id : get the "id" freecom_info_customs_destruction.
     *
     * @param id the id of the freecom_info_customs_destruction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_info_customs_destruction, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-info-customs-destructions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_info_customs_destruction> getFreecom_info_customs_destruction(@PathVariable Long id) {
        log.debug("REST request to get Freecom_info_customs_destruction : {}", id);
        Freecom_info_customs_destruction freecom_info_customs_destruction = freecom_info_customs_destructionService.findOne(id);
        return Optional.ofNullable(freecom_info_customs_destruction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-info-customs-destructions/:id : delete the "id" freecom_info_customs_destruction.
     *
     * @param id the id of the freecom_info_customs_destruction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-info-customs-destructions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_info_customs_destruction(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_info_customs_destruction : {}", id);
        freecom_info_customs_destructionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_info_customs_destruction", id.toString())).build();
    }

}
