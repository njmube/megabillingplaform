package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_data_enajenante;
import org.megapractical.billingplatform.service.Com_data_enajenanteService;
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
 * REST controller for managing Com_data_enajenante.
 */
@RestController
@RequestMapping("/api")
public class Com_data_enajenanteResource {

    private final Logger log = LoggerFactory.getLogger(Com_data_enajenanteResource.class);
        
    @Inject
    private Com_data_enajenanteService com_data_enajenanteService;
    
    /**
     * POST  /com-data-enajenantes : Create a new com_data_enajenante.
     *
     * @param com_data_enajenante the com_data_enajenante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_data_enajenante, or with status 400 (Bad Request) if the com_data_enajenante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-data-enajenantes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_data_enajenante> createCom_data_enajenante(@Valid @RequestBody Com_data_enajenante com_data_enajenante) throws URISyntaxException {
        log.debug("REST request to save Com_data_enajenante : {}", com_data_enajenante);
        if (com_data_enajenante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_data_enajenante", "idexists", "A new com_data_enajenante cannot already have an ID")).body(null);
        }
        Com_data_enajenante result = com_data_enajenanteService.save(com_data_enajenante);
        return ResponseEntity.created(new URI("/api/com-data-enajenantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_data_enajenante", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-data-enajenantes : Updates an existing com_data_enajenante.
     *
     * @param com_data_enajenante the com_data_enajenante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_data_enajenante,
     * or with status 400 (Bad Request) if the com_data_enajenante is not valid,
     * or with status 500 (Internal Server Error) if the com_data_enajenante couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-data-enajenantes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_data_enajenante> updateCom_data_enajenante(@Valid @RequestBody Com_data_enajenante com_data_enajenante) throws URISyntaxException {
        log.debug("REST request to update Com_data_enajenante : {}", com_data_enajenante);
        if (com_data_enajenante.getId() == null) {
            return createCom_data_enajenante(com_data_enajenante);
        }
        Com_data_enajenante result = com_data_enajenanteService.save(com_data_enajenante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_data_enajenante", com_data_enajenante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-data-enajenantes : get all the com_data_enajenantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_data_enajenantes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-data-enajenantes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_data_enajenante>> getAllCom_data_enajenantes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_data_enajenantes");
        Page<Com_data_enajenante> page = com_data_enajenanteService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-data-enajenantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-data-enajenantes/:id : get the "id" com_data_enajenante.
     *
     * @param id the id of the com_data_enajenante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_data_enajenante, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-data-enajenantes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_data_enajenante> getCom_data_enajenante(@PathVariable Long id) {
        log.debug("REST request to get Com_data_enajenante : {}", id);
        Com_data_enajenante com_data_enajenante = com_data_enajenanteService.findOne(id);
        return Optional.ofNullable(com_data_enajenante)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-data-enajenantes/:id : delete the "id" com_data_enajenante.
     *
     * @param id the id of the com_data_enajenante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-data-enajenantes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_data_enajenante(@PathVariable Long id) {
        log.debug("REST request to delete Com_data_enajenante : {}", id);
        com_data_enajenanteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_data_enajenante", id.toString())).build();
    }

}
