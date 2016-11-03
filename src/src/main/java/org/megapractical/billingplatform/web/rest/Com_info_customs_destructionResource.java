package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_info_customs_destruction;
import org.megapractical.billingplatform.service.Com_info_customs_destructionService;
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
 * REST controller for managing Com_info_customs_destruction.
 */
@RestController
@RequestMapping("/api")
public class Com_info_customs_destructionResource {

    private final Logger log = LoggerFactory.getLogger(Com_info_customs_destructionResource.class);
        
    @Inject
    private Com_info_customs_destructionService com_info_customs_destructionService;
    
    /**
     * POST  /com-info-customs-destructions : Create a new com_info_customs_destruction.
     *
     * @param com_info_customs_destruction the com_info_customs_destruction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_info_customs_destruction, or with status 400 (Bad Request) if the com_info_customs_destruction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-info-customs-destructions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_info_customs_destruction> createCom_info_customs_destruction(@Valid @RequestBody Com_info_customs_destruction com_info_customs_destruction) throws URISyntaxException {
        log.debug("REST request to save Com_info_customs_destruction : {}", com_info_customs_destruction);
        if (com_info_customs_destruction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_info_customs_destruction", "idexists", "A new com_info_customs_destruction cannot already have an ID")).body(null);
        }
        Com_info_customs_destruction result = com_info_customs_destructionService.save(com_info_customs_destruction);
        return ResponseEntity.created(new URI("/api/com-info-customs-destructions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_info_customs_destruction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-info-customs-destructions : Updates an existing com_info_customs_destruction.
     *
     * @param com_info_customs_destruction the com_info_customs_destruction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_info_customs_destruction,
     * or with status 400 (Bad Request) if the com_info_customs_destruction is not valid,
     * or with status 500 (Internal Server Error) if the com_info_customs_destruction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-info-customs-destructions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_info_customs_destruction> updateCom_info_customs_destruction(@Valid @RequestBody Com_info_customs_destruction com_info_customs_destruction) throws URISyntaxException {
        log.debug("REST request to update Com_info_customs_destruction : {}", com_info_customs_destruction);
        if (com_info_customs_destruction.getId() == null) {
            return createCom_info_customs_destruction(com_info_customs_destruction);
        }
        Com_info_customs_destruction result = com_info_customs_destructionService.save(com_info_customs_destruction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_info_customs_destruction", com_info_customs_destruction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-info-customs-destructions : get all the com_info_customs_destructions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_info_customs_destructions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-info-customs-destructions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_info_customs_destruction>> getAllCom_info_customs_destructions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_info_customs_destructions");
        Page<Com_info_customs_destruction> page = com_info_customs_destructionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-info-customs-destructions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-info-customs-destructions/:id : get the "id" com_info_customs_destruction.
     *
     * @param id the id of the com_info_customs_destruction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_info_customs_destruction, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-info-customs-destructions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_info_customs_destruction> getCom_info_customs_destruction(@PathVariable Long id) {
        log.debug("REST request to get Com_info_customs_destruction : {}", id);
        Com_info_customs_destruction com_info_customs_destruction = com_info_customs_destructionService.findOne(id);
        return Optional.ofNullable(com_info_customs_destruction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-info-customs-destructions/:id : delete the "id" com_info_customs_destruction.
     *
     * @param id the id of the com_info_customs_destruction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-info-customs-destructions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_info_customs_destruction(@PathVariable Long id) {
        log.debug("REST request to delete Com_info_customs_destruction : {}", id);
        com_info_customs_destructionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_info_customs_destruction", id.toString())).build();
    }

}
