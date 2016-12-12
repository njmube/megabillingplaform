package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_dataacquiringcopsc;
import org.megapractical.billingplatform.service.Freecom_dataacquiringcopscService;
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
 * REST controller for managing Freecom_dataacquiringcopsc.
 */
@RestController
@RequestMapping("/api")
public class Freecom_dataacquiringcopscResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_dataacquiringcopscResource.class);
        
    @Inject
    private Freecom_dataacquiringcopscService freecom_dataacquiringcopscService;
    
    /**
     * POST  /freecom-dataacquiringcopscs : Create a new freecom_dataacquiringcopsc.
     *
     * @param freecom_dataacquiringcopsc the freecom_dataacquiringcopsc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_dataacquiringcopsc, or with status 400 (Bad Request) if the freecom_dataacquiringcopsc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataacquiringcopscs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataacquiringcopsc> createFreecom_dataacquiringcopsc(@Valid @RequestBody Freecom_dataacquiringcopsc freecom_dataacquiringcopsc) throws URISyntaxException {
        log.debug("REST request to save Freecom_dataacquiringcopsc : {}", freecom_dataacquiringcopsc);
        if (freecom_dataacquiringcopsc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_dataacquiringcopsc", "idexists", "A new freecom_dataacquiringcopsc cannot already have an ID")).body(null);
        }
        Freecom_dataacquiringcopsc result = freecom_dataacquiringcopscService.save(freecom_dataacquiringcopsc);
        return ResponseEntity.created(new URI("/api/freecom-dataacquiringcopscs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_dataacquiringcopsc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-dataacquiringcopscs : Updates an existing freecom_dataacquiringcopsc.
     *
     * @param freecom_dataacquiringcopsc the freecom_dataacquiringcopsc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_dataacquiringcopsc,
     * or with status 400 (Bad Request) if the freecom_dataacquiringcopsc is not valid,
     * or with status 500 (Internal Server Error) if the freecom_dataacquiringcopsc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataacquiringcopscs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataacquiringcopsc> updateFreecom_dataacquiringcopsc(@Valid @RequestBody Freecom_dataacquiringcopsc freecom_dataacquiringcopsc) throws URISyntaxException {
        log.debug("REST request to update Freecom_dataacquiringcopsc : {}", freecom_dataacquiringcopsc);
        if (freecom_dataacquiringcopsc.getId() == null) {
            return createFreecom_dataacquiringcopsc(freecom_dataacquiringcopsc);
        }
        Freecom_dataacquiringcopsc result = freecom_dataacquiringcopscService.save(freecom_dataacquiringcopsc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_dataacquiringcopsc", freecom_dataacquiringcopsc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-dataacquiringcopscs : get all the freecom_dataacquiringcopscs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_dataacquiringcopscs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-dataacquiringcopscs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_dataacquiringcopsc>> getAllFreecom_dataacquiringcopscs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_dataacquiringcopscs");
        Page<Freecom_dataacquiringcopsc> page = freecom_dataacquiringcopscService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-dataacquiringcopscs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-dataacquiringcopscs/:id : get the "id" freecom_dataacquiringcopsc.
     *
     * @param id the id of the freecom_dataacquiringcopsc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_dataacquiringcopsc, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-dataacquiringcopscs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataacquiringcopsc> getFreecom_dataacquiringcopsc(@PathVariable Long id) {
        log.debug("REST request to get Freecom_dataacquiringcopsc : {}", id);
        Freecom_dataacquiringcopsc freecom_dataacquiringcopsc = freecom_dataacquiringcopscService.findOne(id);
        return Optional.ofNullable(freecom_dataacquiringcopsc)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-dataacquiringcopscs/:id : delete the "id" freecom_dataacquiringcopsc.
     *
     * @param id the id of the freecom_dataacquiringcopsc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-dataacquiringcopscs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_dataacquiringcopsc(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_dataacquiringcopsc : {}", id);
        freecom_dataacquiringcopscService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_dataacquiringcopsc", id.toString())).build();
    }

}
