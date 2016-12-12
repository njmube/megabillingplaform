package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_data_enajenante;
import org.megapractical.billingplatform.service.Freecom_data_enajenanteService;
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
 * REST controller for managing Freecom_data_enajenante.
 */
@RestController
@RequestMapping("/api")
public class Freecom_data_enajenanteResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_data_enajenanteResource.class);
        
    @Inject
    private Freecom_data_enajenanteService freecom_data_enajenanteService;
    
    /**
     * POST  /freecom-data-enajenantes : Create a new freecom_data_enajenante.
     *
     * @param freecom_data_enajenante the freecom_data_enajenante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_data_enajenante, or with status 400 (Bad Request) if the freecom_data_enajenante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-data-enajenantes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_data_enajenante> createFreecom_data_enajenante(@Valid @RequestBody Freecom_data_enajenante freecom_data_enajenante) throws URISyntaxException {
        log.debug("REST request to save Freecom_data_enajenante : {}", freecom_data_enajenante);
        if (freecom_data_enajenante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_data_enajenante", "idexists", "A new freecom_data_enajenante cannot already have an ID")).body(null);
        }
        Freecom_data_enajenante result = freecom_data_enajenanteService.save(freecom_data_enajenante);
        return ResponseEntity.created(new URI("/api/freecom-data-enajenantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_data_enajenante", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-data-enajenantes : Updates an existing freecom_data_enajenante.
     *
     * @param freecom_data_enajenante the freecom_data_enajenante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_data_enajenante,
     * or with status 400 (Bad Request) if the freecom_data_enajenante is not valid,
     * or with status 500 (Internal Server Error) if the freecom_data_enajenante couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-data-enajenantes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_data_enajenante> updateFreecom_data_enajenante(@Valid @RequestBody Freecom_data_enajenante freecom_data_enajenante) throws URISyntaxException {
        log.debug("REST request to update Freecom_data_enajenante : {}", freecom_data_enajenante);
        if (freecom_data_enajenante.getId() == null) {
            return createFreecom_data_enajenante(freecom_data_enajenante);
        }
        Freecom_data_enajenante result = freecom_data_enajenanteService.save(freecom_data_enajenante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_data_enajenante", freecom_data_enajenante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-data-enajenantes : get all the freecom_data_enajenantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_data_enajenantes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-data-enajenantes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_data_enajenante>> getAllFreecom_data_enajenantes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_data_enajenantes");
        Page<Freecom_data_enajenante> page = freecom_data_enajenanteService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-data-enajenantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-data-enajenantes/:id : get the "id" freecom_data_enajenante.
     *
     * @param id the id of the freecom_data_enajenante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_data_enajenante, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-data-enajenantes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_data_enajenante> getFreecom_data_enajenante(@PathVariable Long id) {
        log.debug("REST request to get Freecom_data_enajenante : {}", id);
        Freecom_data_enajenante freecom_data_enajenante = freecom_data_enajenanteService.findOne(id);
        return Optional.ofNullable(freecom_data_enajenante)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-data-enajenantes/:id : delete the "id" freecom_data_enajenante.
     *
     * @param id the id of the freecom_data_enajenante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-data-enajenantes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_data_enajenante(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_data_enajenante : {}", id);
        freecom_data_enajenanteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_data_enajenante", id.toString())).build();
    }

}
