package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tracemg;
import org.megapractical.billingplatform.service.TracemgService;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tracemg.
 */
@RestController
@RequestMapping("/api")
public class TracemgResource {

    private final Logger log = LoggerFactory.getLogger(TracemgResource.class);

    @Inject
    private TracemgService tracemgService;

    /**
     * POST  /tracemgs : Create a new tracemg.
     *
     * @param tracemg the tracemg to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tracemg, or with status 400 (Bad Request) if the tracemg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tracemgs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tracemg> createTracemg(@Valid @RequestBody Tracemg tracemg) throws URISyntaxException {
        log.debug("REST request to save Tracemg : {}", tracemg);
        if (tracemg.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tracemg", "idexists", "A new tracemg cannot already have an ID")).body(null);
        }
        Tracemg result = tracemgService.save(tracemg);
        return ResponseEntity.created(new URI("/api/tracemgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tracemg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tracemgs : Updates an existing tracemg.
     *
     * @param tracemg the tracemg to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tracemg,
     * or with status 400 (Bad Request) if the tracemg is not valid,
     * or with status 500 (Internal Server Error) if the tracemg couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tracemgs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tracemg> updateTracemg(@Valid @RequestBody Tracemg tracemg) throws URISyntaxException {
        log.debug("REST request to update Tracemg : {}", tracemg);
        if (tracemg.getId() == null) {
            return createTracemg(tracemg);
        }
        Tracemg result = tracemgService.save(tracemg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tracemg", tracemg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tracemgs : get all the tracemgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tracemgs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tracemgs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"fromDate", "toDate", "principal", "auditEventType", "ip"})
    @Timed
    public ResponseEntity<List<Tracemg>> getAllTracemgs(@RequestParam(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                        @RequestParam(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                        @RequestParam(value = "principal") String principal,
                                                        @RequestParam(value = "auditEventType") String auditEventType,
                                                        @RequestParam(value = "ip") String ip,
                                                        Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tracemgs");
        if (principal.compareTo(" ") == 0 && auditEventType.compareTo(" ") == 0 &&
            fromDate.toString().compareTo("0001-01-01") == 0 && toDate.toString().compareTo("0001-01-01") == 0 &&
            ip.compareTo(" ") == 0) {
            ZonedDateTime from = fromDate.atStartOfDay(ZoneId.systemDefault());
            ZonedDateTime to = ZonedDateTime.now();
            Page<Tracemg> page = tracemgService.findAll(from, to, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tracemgs");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else {
            Page<Tracemg> page = tracemgService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tracemgs");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /tracemgs/:id : get the "id" tracemg.
     *
     * @param id the id of the tracemg to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tracemg, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tracemgs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tracemg> getTracemg(@PathVariable Long id) {
        log.debug("REST request to get Tracemg : {}", id);
        Tracemg tracemg = tracemgService.findOne(id);
        return Optional.ofNullable(tracemg)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tracemgs/:id : delete the "id" tracemg.
     *
     * @param id the id of the tracemg to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tracemgs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTracemg(@PathVariable Long id) {
        log.debug("REST request to delete Tracemg : {}", id);
        tracemgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tracemg", id.toString())).build();
    }

}
