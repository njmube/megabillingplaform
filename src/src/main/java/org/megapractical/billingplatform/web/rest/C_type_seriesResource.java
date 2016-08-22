package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_type_series;
import org.megapractical.billingplatform.service.C_type_seriesService;
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
 * REST controller for managing C_type_series.
 */
@RestController
@RequestMapping("/api")
public class C_type_seriesResource {

    private final Logger log = LoggerFactory.getLogger(C_type_seriesResource.class);
        
    @Inject
    private C_type_seriesService c_type_seriesService;
    
    /**
     * POST  /c-type-series : Create a new c_type_series.
     *
     * @param c_type_series the c_type_series to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_type_series, or with status 400 (Bad Request) if the c_type_series has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-series",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_series> createC_type_series(@Valid @RequestBody C_type_series c_type_series) throws URISyntaxException {
        log.debug("REST request to save C_type_series : {}", c_type_series);
        if (c_type_series.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_type_series", "idexists", "A new c_type_series cannot already have an ID")).body(null);
        }
        C_type_series result = c_type_seriesService.save(c_type_series);
        return ResponseEntity.created(new URI("/api/c-type-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_type_series", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-type-series : Updates an existing c_type_series.
     *
     * @param c_type_series the c_type_series to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_type_series,
     * or with status 400 (Bad Request) if the c_type_series is not valid,
     * or with status 500 (Internal Server Error) if the c_type_series couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-series",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_series> updateC_type_series(@Valid @RequestBody C_type_series c_type_series) throws URISyntaxException {
        log.debug("REST request to update C_type_series : {}", c_type_series);
        if (c_type_series.getId() == null) {
            return createC_type_series(c_type_series);
        }
        C_type_series result = c_type_seriesService.save(c_type_series);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_type_series", c_type_series.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-type-series : get all the c_type_series.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_type_series in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-type-series",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_type_series>> getAllC_type_series(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_type_series");
        Page<C_type_series> page = c_type_seriesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-type-series");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-type-series/:id : get the "id" c_type_series.
     *
     * @param id the id of the c_type_series to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_type_series, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-type-series/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_series> getC_type_series(@PathVariable Long id) {
        log.debug("REST request to get C_type_series : {}", id);
        C_type_series c_type_series = c_type_seriesService.findOne(id);
        return Optional.ofNullable(c_type_series)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-type-series/:id : delete the "id" c_type_series.
     *
     * @param id the id of the c_type_series to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-type-series/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_type_series(@PathVariable Long id) {
        log.debug("REST request to delete C_type_series : {}", id);
        c_type_seriesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_type_series", id.toString())).build();
    }

}
