package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Rate_type;
import org.megapractical.billingplatform.service.Rate_typeService;
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
 * REST controller for managing Rate_type.
 */
@RestController
@RequestMapping("/api")
public class Rate_typeResource {

    private final Logger log = LoggerFactory.getLogger(Rate_typeResource.class);
        
    @Inject
    private Rate_typeService rate_typeService;
    
    /**
     * POST  /rate-types : Create a new rate_type.
     *
     * @param rate_type the rate_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rate_type, or with status 400 (Bad Request) if the rate_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rate_type> createRate_type(@Valid @RequestBody Rate_type rate_type) throws URISyntaxException {
        log.debug("REST request to save Rate_type : {}", rate_type);
        if (rate_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rate_type", "idexists", "A new rate_type cannot already have an ID")).body(null);
        }
        Rate_type result = rate_typeService.save(rate_type);
        return ResponseEntity.created(new URI("/api/rate-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rate_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rate-types : Updates an existing rate_type.
     *
     * @param rate_type the rate_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rate_type,
     * or with status 400 (Bad Request) if the rate_type is not valid,
     * or with status 500 (Internal Server Error) if the rate_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rate_type> updateRate_type(@Valid @RequestBody Rate_type rate_type) throws URISyntaxException {
        log.debug("REST request to update Rate_type : {}", rate_type);
        if (rate_type.getId() == null) {
            return createRate_type(rate_type);
        }
        Rate_type result = rate_typeService.save(rate_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rate_type", rate_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rate-types : get all the rate_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rate_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rate-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Rate_type>> getAllRate_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Rate_types");
        Page<Rate_type> page = rate_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rate-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rate-types/:id : get the "id" rate_type.
     *
     * @param id the id of the rate_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rate_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rate-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rate_type> getRate_type(@PathVariable Long id) {
        log.debug("REST request to get Rate_type : {}", id);
        Rate_type rate_type = rate_typeService.findOne(id);
        return Optional.ofNullable(rate_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rate-types/:id : delete the "id" rate_type.
     *
     * @param id the id of the rate_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rate-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRate_type(@PathVariable Long id) {
        log.debug("REST request to delete Rate_type : {}", id);
        rate_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rate_type", id.toString())).build();
    }

}
