package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Archive_status;
import org.megapractical.billingplatform.service.Archive_statusService;
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
 * REST controller for managing Archive_status.
 */
@RestController
@RequestMapping("/api")
public class Archive_statusResource {

    private final Logger log = LoggerFactory.getLogger(Archive_statusResource.class);
        
    @Inject
    private Archive_statusService archive_statusService;
    
    /**
     * POST  /archive-statuses : Create a new archive_status.
     *
     * @param archive_status the archive_status to create
     * @return the ResponseEntity with status 201 (Created) and with body the new archive_status, or with status 400 (Bad Request) if the archive_status has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/archive-statuses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Archive_status> createArchive_status(@Valid @RequestBody Archive_status archive_status) throws URISyntaxException {
        log.debug("REST request to save Archive_status : {}", archive_status);
        if (archive_status.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("archive_status", "idexists", "A new archive_status cannot already have an ID")).body(null);
        }
        Archive_status result = archive_statusService.save(archive_status);
        return ResponseEntity.created(new URI("/api/archive-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("archive_status", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /archive-statuses : Updates an existing archive_status.
     *
     * @param archive_status the archive_status to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated archive_status,
     * or with status 400 (Bad Request) if the archive_status is not valid,
     * or with status 500 (Internal Server Error) if the archive_status couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/archive-statuses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Archive_status> updateArchive_status(@Valid @RequestBody Archive_status archive_status) throws URISyntaxException {
        log.debug("REST request to update Archive_status : {}", archive_status);
        if (archive_status.getId() == null) {
            return createArchive_status(archive_status);
        }
        Archive_status result = archive_statusService.save(archive_status);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("archive_status", archive_status.getId().toString()))
            .body(result);
    }

    /**
     * GET  /archive-statuses : get all the archive_statuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of archive_statuses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/archive-statuses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Archive_status>> getAllArchive_statuses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Archive_statuses");
        Page<Archive_status> page = archive_statusService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/archive-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /archive-statuses/:id : get the "id" archive_status.
     *
     * @param id the id of the archive_status to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the archive_status, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/archive-statuses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Archive_status> getArchive_status(@PathVariable Long id) {
        log.debug("REST request to get Archive_status : {}", id);
        Archive_status archive_status = archive_statusService.findOne(id);
        return Optional.ofNullable(archive_status)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /archive-statuses/:id : delete the "id" archive_status.
     *
     * @param id the id of the archive_status to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/archive-statuses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteArchive_status(@PathVariable Long id) {
        log.debug("REST request to delete Archive_status : {}", id);
        archive_statusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("archive_status", id.toString())).build();
    }

}
