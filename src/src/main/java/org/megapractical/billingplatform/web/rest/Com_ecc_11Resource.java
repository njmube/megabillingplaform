package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_ecc_11;
import org.megapractical.billingplatform.service.Com_ecc_11Service;
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
 * REST controller for managing Com_ecc_11.
 */
@RestController
@RequestMapping("/api")
public class Com_ecc_11Resource {

    private final Logger log = LoggerFactory.getLogger(Com_ecc_11Resource.class);
        
    @Inject
    private Com_ecc_11Service com_ecc_11Service;
    
    /**
     * POST  /com-ecc-11-s : Create a new com_ecc_11.
     *
     * @param com_ecc_11 the com_ecc_11 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_ecc_11, or with status 400 (Bad Request) if the com_ecc_11 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ecc-11-s",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11> createCom_ecc_11(@Valid @RequestBody Com_ecc_11 com_ecc_11) throws URISyntaxException {
        log.debug("REST request to save Com_ecc_11 : {}", com_ecc_11);
        if (com_ecc_11.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_ecc_11", "idexists", "A new com_ecc_11 cannot already have an ID")).body(null);
        }
        Com_ecc_11 result = com_ecc_11Service.save(com_ecc_11);
        return ResponseEntity.created(new URI("/api/com-ecc-11-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_ecc_11", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-ecc-11-s : Updates an existing com_ecc_11.
     *
     * @param com_ecc_11 the com_ecc_11 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_ecc_11,
     * or with status 400 (Bad Request) if the com_ecc_11 is not valid,
     * or with status 500 (Internal Server Error) if the com_ecc_11 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ecc-11-s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11> updateCom_ecc_11(@Valid @RequestBody Com_ecc_11 com_ecc_11) throws URISyntaxException {
        log.debug("REST request to update Com_ecc_11 : {}", com_ecc_11);
        if (com_ecc_11.getId() == null) {
            return createCom_ecc_11(com_ecc_11);
        }
        Com_ecc_11 result = com_ecc_11Service.save(com_ecc_11);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_ecc_11", com_ecc_11.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-ecc-11-s : get all the com_ecc_11S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_ecc_11S in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-ecc-11-s",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_ecc_11>> getAllCom_ecc_11S(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_ecc_11S");
        Page<Com_ecc_11> page = com_ecc_11Service.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-ecc-11-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-ecc-11-s/:id : get the "id" com_ecc_11.
     *
     * @param id the id of the com_ecc_11 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_ecc_11, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-ecc-11-s/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11> getCom_ecc_11(@PathVariable Long id) {
        log.debug("REST request to get Com_ecc_11 : {}", id);
        Com_ecc_11 com_ecc_11 = com_ecc_11Service.findOne(id);
        return Optional.ofNullable(com_ecc_11)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-ecc-11-s/:id : delete the "id" com_ecc_11.
     *
     * @param id the id of the com_ecc_11 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-ecc-11-s/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_ecc_11(@PathVariable Long id) {
        log.debug("REST request to delete Com_ecc_11 : {}", id);
        com_ecc_11Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_ecc_11", id.toString())).build();
    }

}
