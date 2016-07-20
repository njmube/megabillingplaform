package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
 * REST controller for managing Audit_event_type.
 */
@RestController
@RequestMapping("/api")
public class Audit_event_typeResource {

    private final Logger log = LoggerFactory.getLogger(Audit_event_typeResource.class);

    @Inject
    private Audit_event_typeService audit_event_typeService;

    /**
     * POST  /audit-event-types : Create a new audit_event_type.
     *
     * @param audit_event_type the audit_event_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audit_event_type, or with status 400 (Bad Request) if the audit_event_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/audit-event-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Audit_event_type> createAudit_event_type(@Valid @RequestBody Audit_event_type audit_event_type) throws URISyntaxException {
        log.debug("REST request to save Audit_event_type : {}", audit_event_type);
        if (audit_event_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("audit_event_type", "idexists", "A new audit_event_type cannot already have an ID")).body(null);
        }
        Audit_event_type result = audit_event_typeService.save(audit_event_type);
        return ResponseEntity.created(new URI("/api/audit-event-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("audit_event_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audit-event-types : Updates an existing audit_event_type.
     *
     * @param audit_event_type the audit_event_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audit_event_type,
     * or with status 400 (Bad Request) if the audit_event_type is not valid,
     * or with status 500 (Internal Server Error) if the audit_event_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/audit-event-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Audit_event_type> updateAudit_event_type(@Valid @RequestBody Audit_event_type audit_event_type) throws URISyntaxException {
        log.debug("REST request to update Audit_event_type : {}", audit_event_type);
        if (audit_event_type.getId() == null) {
            return createAudit_event_type(audit_event_type);
        }
        Audit_event_type result = audit_event_typeService.save(audit_event_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("audit_event_type", audit_event_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audit-event-types : get all the audit_event_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of audit_event_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/audit-event-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"pg"})
    @Timed
    public ResponseEntity<List<Audit_event_type>> getAllAudit_event_types(@RequestParam(value = "pg") Integer pg,
                                                                          Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Audit_event_types");
        if(pg == 0) {
            Page<Audit_event_type> page = audit_event_typeService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audit-event-types");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else {
            List<Audit_event_type> list = audit_event_typeService.findAll();
            Page<Audit_event_type> page = new PageImpl<Audit_event_type>(list);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audit-event-types");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /audit-event-types/:id : get the "id" audit_event_type.
     *
     * @param id the id of the audit_event_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audit_event_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/audit-event-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Audit_event_type> getAudit_event_type(@PathVariable Long id) {
        log.debug("REST request to get Audit_event_type : {}", id);
        Audit_event_type audit_event_type = audit_event_typeService.findOne(id);
        return Optional.ofNullable(audit_event_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /audit-event-types/:id : delete the "id" audit_event_type.
     *
     * @param id the id of the audit_event_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/audit-event-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAudit_event_type(@PathVariable Long id) {
        log.debug("REST request to delete Audit_event_type : {}", id);
        audit_event_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("audit_event_type", id.toString())).build();
    }

}
