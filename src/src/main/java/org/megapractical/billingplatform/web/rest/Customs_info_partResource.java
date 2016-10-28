package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Customs_info_part;
import org.megapractical.billingplatform.service.Customs_info_partService;
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
 * REST controller for managing Customs_info_part.
 */
@RestController
@RequestMapping("/api")
public class Customs_info_partResource {

    private final Logger log = LoggerFactory.getLogger(Customs_info_partResource.class);
        
    @Inject
    private Customs_info_partService customs_info_partService;
    
    /**
     * POST  /customs-info-parts : Create a new customs_info_part.
     *
     * @param customs_info_part the customs_info_part to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customs_info_part, or with status 400 (Bad Request) if the customs_info_part has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customs-info-parts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Customs_info_part> createCustoms_info_part(@Valid @RequestBody Customs_info_part customs_info_part) throws URISyntaxException {
        log.debug("REST request to save Customs_info_part : {}", customs_info_part);
        if (customs_info_part.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customs_info_part", "idexists", "A new customs_info_part cannot already have an ID")).body(null);
        }
        Customs_info_part result = customs_info_partService.save(customs_info_part);
        return ResponseEntity.created(new URI("/api/customs-info-parts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customs_info_part", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customs-info-parts : Updates an existing customs_info_part.
     *
     * @param customs_info_part the customs_info_part to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customs_info_part,
     * or with status 400 (Bad Request) if the customs_info_part is not valid,
     * or with status 500 (Internal Server Error) if the customs_info_part couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customs-info-parts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Customs_info_part> updateCustoms_info_part(@Valid @RequestBody Customs_info_part customs_info_part) throws URISyntaxException {
        log.debug("REST request to update Customs_info_part : {}", customs_info_part);
        if (customs_info_part.getId() == null) {
            return createCustoms_info_part(customs_info_part);
        }
        Customs_info_part result = customs_info_partService.save(customs_info_part);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customs_info_part", customs_info_part.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customs-info-parts : get all the customs_info_parts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customs_info_parts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/customs-info-parts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Customs_info_part>> getAllCustoms_info_parts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Customs_info_parts");
        Page<Customs_info_part> page = customs_info_partService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customs-info-parts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customs-info-parts/:id : get the "id" customs_info_part.
     *
     * @param id the id of the customs_info_part to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customs_info_part, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/customs-info-parts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Customs_info_part> getCustoms_info_part(@PathVariable Long id) {
        log.debug("REST request to get Customs_info_part : {}", id);
        Customs_info_part customs_info_part = customs_info_partService.findOne(id);
        return Optional.ofNullable(customs_info_part)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customs-info-parts/:id : delete the "id" customs_info_part.
     *
     * @param id the id of the customs_info_part to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/customs-info-parts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustoms_info_part(@PathVariable Long id) {
        log.debug("REST request to delete Customs_info_part : {}", id);
        customs_info_partService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customs_info_part", id.toString())).build();
    }

}
