package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_dataenajenantecopsc;
import org.megapractical.billingplatform.service.Freecom_dataenajenantecopscService;
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
 * REST controller for managing Freecom_dataenajenantecopsc.
 */
@RestController
@RequestMapping("/api")
public class Freecom_dataenajenantecopscResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_dataenajenantecopscResource.class);
        
    @Inject
    private Freecom_dataenajenantecopscService freecom_dataenajenantecopscService;
    
    /**
     * POST  /freecom-dataenajenantecopscs : Create a new freecom_dataenajenantecopsc.
     *
     * @param freecom_dataenajenantecopsc the freecom_dataenajenantecopsc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_dataenajenantecopsc, or with status 400 (Bad Request) if the freecom_dataenajenantecopsc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataenajenantecopscs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataenajenantecopsc> createFreecom_dataenajenantecopsc(@Valid @RequestBody Freecom_dataenajenantecopsc freecom_dataenajenantecopsc) throws URISyntaxException {
        log.debug("REST request to save Freecom_dataenajenantecopsc : {}", freecom_dataenajenantecopsc);
        if (freecom_dataenajenantecopsc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_dataenajenantecopsc", "idexists", "A new freecom_dataenajenantecopsc cannot already have an ID")).body(null);
        }
        Freecom_dataenajenantecopsc result = freecom_dataenajenantecopscService.save(freecom_dataenajenantecopsc);
        return ResponseEntity.created(new URI("/api/freecom-dataenajenantecopscs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_dataenajenantecopsc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-dataenajenantecopscs : Updates an existing freecom_dataenajenantecopsc.
     *
     * @param freecom_dataenajenantecopsc the freecom_dataenajenantecopsc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_dataenajenantecopsc,
     * or with status 400 (Bad Request) if the freecom_dataenajenantecopsc is not valid,
     * or with status 500 (Internal Server Error) if the freecom_dataenajenantecopsc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataenajenantecopscs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataenajenantecopsc> updateFreecom_dataenajenantecopsc(@Valid @RequestBody Freecom_dataenajenantecopsc freecom_dataenajenantecopsc) throws URISyntaxException {
        log.debug("REST request to update Freecom_dataenajenantecopsc : {}", freecom_dataenajenantecopsc);
        if (freecom_dataenajenantecopsc.getId() == null) {
            return createFreecom_dataenajenantecopsc(freecom_dataenajenantecopsc);
        }
        Freecom_dataenajenantecopsc result = freecom_dataenajenantecopscService.save(freecom_dataenajenantecopsc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_dataenajenantecopsc", freecom_dataenajenantecopsc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-dataenajenantecopscs : get all the freecom_dataenajenantecopscs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_dataenajenantecopscs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-dataenajenantecopscs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_dataenajenantecopsc>> getAllFreecom_dataenajenantecopscs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_dataenajenantecopscs");
        Page<Freecom_dataenajenantecopsc> page = freecom_dataenajenantecopscService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-dataenajenantecopscs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-dataenajenantecopscs/:id : get the "id" freecom_dataenajenantecopsc.
     *
     * @param id the id of the freecom_dataenajenantecopsc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_dataenajenantecopsc, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-dataenajenantecopscs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataenajenantecopsc> getFreecom_dataenajenantecopsc(@PathVariable Long id) {
        log.debug("REST request to get Freecom_dataenajenantecopsc : {}", id);
        Freecom_dataenajenantecopsc freecom_dataenajenantecopsc = freecom_dataenajenantecopscService.findOne(id);
        return Optional.ofNullable(freecom_dataenajenantecopsc)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-dataenajenantecopscs/:id : delete the "id" freecom_dataenajenantecopsc.
     *
     * @param id the id of the freecom_dataenajenantecopsc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-dataenajenantecopscs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_dataenajenantecopsc(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_dataenajenantecopsc : {}", id);
        freecom_dataenajenantecopscService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_dataenajenantecopsc", id.toString())).build();
    }

}
