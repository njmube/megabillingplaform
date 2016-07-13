package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Legend;
import org.megapractical.billingplatform.service.LegendService;
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
 * REST controller for managing Legend.
 */
@RestController
@RequestMapping("/api")
public class LegendResource {

    private final Logger log = LoggerFactory.getLogger(LegendResource.class);
        
    @Inject
    private LegendService legendService;
    
    /**
     * POST  /legends : Create a new legend.
     *
     * @param legend the legend to create
     * @return the ResponseEntity with status 201 (Created) and with body the new legend, or with status 400 (Bad Request) if the legend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/legends",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Legend> createLegend(@Valid @RequestBody Legend legend) throws URISyntaxException {
        log.debug("REST request to save Legend : {}", legend);
        if (legend.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("legend", "idexists", "A new legend cannot already have an ID")).body(null);
        }
        Legend result = legendService.save(legend);
        return ResponseEntity.created(new URI("/api/legends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("legend", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /legends : Updates an existing legend.
     *
     * @param legend the legend to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated legend,
     * or with status 400 (Bad Request) if the legend is not valid,
     * or with status 500 (Internal Server Error) if the legend couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/legends",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Legend> updateLegend(@Valid @RequestBody Legend legend) throws URISyntaxException {
        log.debug("REST request to update Legend : {}", legend);
        if (legend.getId() == null) {
            return createLegend(legend);
        }
        Legend result = legendService.save(legend);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("legend", legend.getId().toString()))
            .body(result);
    }

    /**
     * GET  /legends : get all the legends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of legends in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/legends",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Legend>> getAllLegends(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Legends");
        Page<Legend> page = legendService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/legends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /legends/:id : get the "id" legend.
     *
     * @param id the id of the legend to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the legend, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/legends/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Legend> getLegend(@PathVariable Long id) {
        log.debug("REST request to get Legend : {}", id);
        Legend legend = legendService.findOne(id);
        return Optional.ofNullable(legend)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /legends/:id : delete the "id" legend.
     *
     * @param id the id of the legend to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/legends/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLegend(@PathVariable Long id) {
        log.debug("REST request to delete Legend : {}", id);
        legendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("legend", id.toString())).build();
    }

}
