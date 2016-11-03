package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_spei_third;
import org.megapractical.billingplatform.service.Com_spei_thirdService;
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
 * REST controller for managing Com_spei_third.
 */
@RestController
@RequestMapping("/api")
public class Com_spei_thirdResource {

    private final Logger log = LoggerFactory.getLogger(Com_spei_thirdResource.class);
        
    @Inject
    private Com_spei_thirdService com_spei_thirdService;
    
    /**
     * POST  /com-spei-thirds : Create a new com_spei_third.
     *
     * @param com_spei_third the com_spei_third to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_spei_third, or with status 400 (Bad Request) if the com_spei_third has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-spei-thirds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_spei_third> createCom_spei_third(@Valid @RequestBody Com_spei_third com_spei_third) throws URISyntaxException {
        log.debug("REST request to save Com_spei_third : {}", com_spei_third);
        if (com_spei_third.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_spei_third", "idexists", "A new com_spei_third cannot already have an ID")).body(null);
        }
        Com_spei_third result = com_spei_thirdService.save(com_spei_third);
        return ResponseEntity.created(new URI("/api/com-spei-thirds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_spei_third", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-spei-thirds : Updates an existing com_spei_third.
     *
     * @param com_spei_third the com_spei_third to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_spei_third,
     * or with status 400 (Bad Request) if the com_spei_third is not valid,
     * or with status 500 (Internal Server Error) if the com_spei_third couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-spei-thirds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_spei_third> updateCom_spei_third(@Valid @RequestBody Com_spei_third com_spei_third) throws URISyntaxException {
        log.debug("REST request to update Com_spei_third : {}", com_spei_third);
        if (com_spei_third.getId() == null) {
            return createCom_spei_third(com_spei_third);
        }
        Com_spei_third result = com_spei_thirdService.save(com_spei_third);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_spei_third", com_spei_third.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-spei-thirds : get all the com_spei_thirds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_spei_thirds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-spei-thirds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_spei_third>> getAllCom_spei_thirds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_spei_thirds");
        Page<Com_spei_third> page = com_spei_thirdService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-spei-thirds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-spei-thirds/:id : get the "id" com_spei_third.
     *
     * @param id the id of the com_spei_third to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_spei_third, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-spei-thirds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_spei_third> getCom_spei_third(@PathVariable Long id) {
        log.debug("REST request to get Com_spei_third : {}", id);
        Com_spei_third com_spei_third = com_spei_thirdService.findOne(id);
        return Optional.ofNullable(com_spei_third)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-spei-thirds/:id : delete the "id" com_spei_third.
     *
     * @param id the id of the com_spei_third to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-spei-thirds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_spei_third(@PathVariable Long id) {
        log.debug("REST request to delete Com_spei_third : {}", id);
        com_spei_thirdService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_spei_third", id.toString())).build();
    }

}
