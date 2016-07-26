package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.service.AuditEventService;

import java.time.LocalDate;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for getting the audit events.
 */
@RestController
@RequestMapping(value = "/api/audits", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuditResource {

    private AuditEventService auditEventService;

    private final Logger log = LoggerFactory.getLogger(AuditEventService.class);

    @Inject
    public AuditResource(AuditEventService auditEventService) {
        this.auditEventService = auditEventService;
    }

    /**
     * GET  /audits : get a page of AuditEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of AuditEvents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AuditEvent>> getAll(Pageable pageable) throws URISyntaxException {
        Page<AuditEvent> page = auditEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /audits : get a page of AuditEvents between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of AuditEvents to get
     * @param toDate the end of the time period of AuditEvents to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of AuditEvents in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */

    @RequestMapping(method = RequestMethod.GET,
        params = {"pg","fromDate", "toDate", "principal", "auditEventType", "ip"})
    public ResponseEntity<List<AuditEvent>> getByDates(
        @RequestParam(value = "pg") Integer pg,
        @RequestParam(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
        @RequestParam(value = "principal") String principal,
        @RequestParam(value = "auditEventType") String auditEventType,
        @RequestParam(value = "ip") String ip,
        Pageable pageable) throws URISyntaxException {

        if (principal.compareTo(" ") == 0 && auditEventType.compareTo(" ") == 0 &&
            fromDate.toString().compareTo("0001-01-01") == 0 && toDate.toString().compareTo("0001-01-01") == 0 &&
            ip.compareTo(" ") == 0) {
            Page<AuditEvent> page = auditEventService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audits");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else {
            Page<AuditEvent> page = auditEventService.findByDates(fromDate, toDate,principal,
                auditEventType, ip, pageable, pg);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audits");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /audits/:id : get an AuditEvent by id.
     *
     * @param id the id of the entity to get
     * @return the ResponseEntity with status 200 (OK) and the AuditEvent in body, or status 404 (Not Found)
     */
    @RequestMapping(value = "/{id:.+}",
        method = RequestMethod.GET)
    public ResponseEntity<AuditEvent> get(@PathVariable Long id) {
        return auditEventService.find(id)
                .map((entity) -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
