package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Customs_info;
import org.megapractical.billingplatform.service.Customs_infoService;
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
 * REST controller for managing Customs_info.
 */
@RestController
@RequestMapping("/api")
public class Customs_infoResource {

    private final Logger log = LoggerFactory.getLogger(Customs_infoResource.class);
        
    @Inject
    private Customs_infoService customs_infoService;
    
    /**
     * POST  /customs-infos : Create a new customs_info.
     *
     * @param customs_info the customs_info to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customs_info, or with status 400 (Bad Request) if the customs_info has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customs-infos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Customs_info> createCustoms_info(@Valid @RequestBody Customs_info customs_info) throws URISyntaxException {
        log.debug("REST request to save Customs_info : {}", customs_info);
        if (customs_info.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customs_info", "idexists", "A new customs_info cannot already have an ID")).body(null);
        }
        Customs_info result = customs_infoService.save(customs_info);
        return ResponseEntity.created(new URI("/api/customs-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customs_info", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customs-infos : Updates an existing customs_info.
     *
     * @param customs_info the customs_info to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customs_info,
     * or with status 400 (Bad Request) if the customs_info is not valid,
     * or with status 500 (Internal Server Error) if the customs_info couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customs-infos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Customs_info> updateCustoms_info(@Valid @RequestBody Customs_info customs_info) throws URISyntaxException {
        log.debug("REST request to update Customs_info : {}", customs_info);
        if (customs_info.getId() == null) {
            return createCustoms_info(customs_info);
        }
        Customs_info result = customs_infoService.save(customs_info);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customs_info", customs_info.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customs-infos : get all the customs_infos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customs_infos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/customs-infos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Customs_info>> getAllCustoms_infos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Customs_infos");
        Page<Customs_info> page = customs_infoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customs-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customs-infos/:id : get the "id" customs_info.
     *
     * @param id the id of the customs_info to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customs_info, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/customs-infos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Customs_info> getCustoms_info(@PathVariable Long id) {
        log.debug("REST request to get Customs_info : {}", id);
        Customs_info customs_info = customs_infoService.findOne(id);
        return Optional.ofNullable(customs_info)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customs-infos/:id : delete the "id" customs_info.
     *
     * @param id the id of the customs_info to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/customs-infos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustoms_info(@PathVariable Long id) {
        log.debug("REST request to delete Customs_info : {}", id);
        customs_infoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customs_info", id.toString())).build();
    }

}
