package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_dataunenajenante;
import org.megapractical.billingplatform.service.Freecom_dataunenajenanteService;
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
 * REST controller for managing Freecom_dataunenajenante.
 */
@RestController
@RequestMapping("/api")
public class Freecom_dataunenajenanteResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_dataunenajenanteResource.class);
        
    @Inject
    private Freecom_dataunenajenanteService freecom_dataunenajenanteService;
    
    /**
     * POST  /freecom-dataunenajenantes : Create a new freecom_dataunenajenante.
     *
     * @param freecom_dataunenajenante the freecom_dataunenajenante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_dataunenajenante, or with status 400 (Bad Request) if the freecom_dataunenajenante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataunenajenantes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataunenajenante> createFreecom_dataunenajenante(@Valid @RequestBody Freecom_dataunenajenante freecom_dataunenajenante) throws URISyntaxException {
        log.debug("REST request to save Freecom_dataunenajenante : {}", freecom_dataunenajenante);
        if (freecom_dataunenajenante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_dataunenajenante", "idexists", "A new freecom_dataunenajenante cannot already have an ID")).body(null);
        }
        Freecom_dataunenajenante result = freecom_dataunenajenanteService.save(freecom_dataunenajenante);
        return ResponseEntity.created(new URI("/api/freecom-dataunenajenantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_dataunenajenante", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-dataunenajenantes : Updates an existing freecom_dataunenajenante.
     *
     * @param freecom_dataunenajenante the freecom_dataunenajenante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_dataunenajenante,
     * or with status 400 (Bad Request) if the freecom_dataunenajenante is not valid,
     * or with status 500 (Internal Server Error) if the freecom_dataunenajenante couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataunenajenantes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataunenajenante> updateFreecom_dataunenajenante(@Valid @RequestBody Freecom_dataunenajenante freecom_dataunenajenante) throws URISyntaxException {
        log.debug("REST request to update Freecom_dataunenajenante : {}", freecom_dataunenajenante);
        if (freecom_dataunenajenante.getId() == null) {
            return createFreecom_dataunenajenante(freecom_dataunenajenante);
        }
        Freecom_dataunenajenante result = freecom_dataunenajenanteService.save(freecom_dataunenajenante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_dataunenajenante", freecom_dataunenajenante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-dataunenajenantes : get all the freecom_dataunenajenantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_dataunenajenantes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-dataunenajenantes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_dataunenajenante>> getAllFreecom_dataunenajenantes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_dataunenajenantes");
        Page<Freecom_dataunenajenante> page = freecom_dataunenajenanteService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-dataunenajenantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-dataunenajenantes/:id : get the "id" freecom_dataunenajenante.
     *
     * @param id the id of the freecom_dataunenajenante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_dataunenajenante, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-dataunenajenantes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataunenajenante> getFreecom_dataunenajenante(@PathVariable Long id) {
        log.debug("REST request to get Freecom_dataunenajenante : {}", id);
        Freecom_dataunenajenante freecom_dataunenajenante = freecom_dataunenajenanteService.findOne(id);
        return Optional.ofNullable(freecom_dataunenajenante)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-dataunenajenantes/:id : delete the "id" freecom_dataunenajenante.
     *
     * @param id the id of the freecom_dataunenajenante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-dataunenajenantes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_dataunenajenante(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_dataunenajenante : {}", id);
        freecom_dataunenajenanteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_dataunenajenante", id.toString())).build();
    }

}
