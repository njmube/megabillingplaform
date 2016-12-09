package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_dataunenajenante;
import org.megapractical.billingplatform.service.Com_dataunenajenanteService;
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
 * REST controller for managing Com_dataunenajenante.
 */
@RestController
@RequestMapping("/api")
public class Com_dataunenajenanteResource {

    private final Logger log = LoggerFactory.getLogger(Com_dataunenajenanteResource.class);
        
    @Inject
    private Com_dataunenajenanteService com_dataunenajenanteService;
    
    /**
     * POST  /com-dataunenajenantes : Create a new com_dataunenajenante.
     *
     * @param com_dataunenajenante the com_dataunenajenante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_dataunenajenante, or with status 400 (Bad Request) if the com_dataunenajenante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataunenajenantes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataunenajenante> createCom_dataunenajenante(@Valid @RequestBody Com_dataunenajenante com_dataunenajenante) throws URISyntaxException {
        log.debug("REST request to save Com_dataunenajenante : {}", com_dataunenajenante);
        if (com_dataunenajenante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_dataunenajenante", "idexists", "A new com_dataunenajenante cannot already have an ID")).body(null);
        }
        Com_dataunenajenante result = com_dataunenajenanteService.save(com_dataunenajenante);
        return ResponseEntity.created(new URI("/api/com-dataunenajenantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_dataunenajenante", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-dataunenajenantes : Updates an existing com_dataunenajenante.
     *
     * @param com_dataunenajenante the com_dataunenajenante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_dataunenajenante,
     * or with status 400 (Bad Request) if the com_dataunenajenante is not valid,
     * or with status 500 (Internal Server Error) if the com_dataunenajenante couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataunenajenantes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataunenajenante> updateCom_dataunenajenante(@Valid @RequestBody Com_dataunenajenante com_dataunenajenante) throws URISyntaxException {
        log.debug("REST request to update Com_dataunenajenante : {}", com_dataunenajenante);
        if (com_dataunenajenante.getId() == null) {
            return createCom_dataunenajenante(com_dataunenajenante);
        }
        Com_dataunenajenante result = com_dataunenajenanteService.save(com_dataunenajenante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_dataunenajenante", com_dataunenajenante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-dataunenajenantes : get all the com_dataunenajenantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_dataunenajenantes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-dataunenajenantes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_dataunenajenante>> getAllCom_dataunenajenantes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_dataunenajenantes");
        Page<Com_dataunenajenante> page = com_dataunenajenanteService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-dataunenajenantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-dataunenajenantes/:id : get the "id" com_dataunenajenante.
     *
     * @param id the id of the com_dataunenajenante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_dataunenajenante, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-dataunenajenantes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataunenajenante> getCom_dataunenajenante(@PathVariable Long id) {
        log.debug("REST request to get Com_dataunenajenante : {}", id);
        Com_dataunenajenante com_dataunenajenante = com_dataunenajenanteService.findOne(id);
        return Optional.ofNullable(com_dataunenajenante)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-dataunenajenantes/:id : delete the "id" com_dataunenajenante.
     *
     * @param id the id of the com_dataunenajenante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-dataunenajenantes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_dataunenajenante(@PathVariable Long id) {
        log.debug("REST request to delete Com_dataunenajenante : {}", id);
        com_dataunenajenanteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_dataunenajenante", id.toString())).build();
    }

}
