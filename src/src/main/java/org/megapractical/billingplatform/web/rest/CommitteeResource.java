package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Committee;
import org.megapractical.billingplatform.service.CommitteeService;
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
 * REST controller for managing Committee.
 */
@RestController
@RequestMapping("/api")
public class CommitteeResource {

    private final Logger log = LoggerFactory.getLogger(CommitteeResource.class);
        
    @Inject
    private CommitteeService committeeService;
    
    /**
     * POST  /committees : Create a new committee.
     *
     * @param committee the committee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new committee, or with status 400 (Bad Request) if the committee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/committees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Committee> createCommittee(@Valid @RequestBody Committee committee) throws URISyntaxException {
        log.debug("REST request to save Committee : {}", committee);
        if (committee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("committee", "idexists", "A new committee cannot already have an ID")).body(null);
        }
        Committee result = committeeService.save(committee);
        return ResponseEntity.created(new URI("/api/committees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("committee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /committees : Updates an existing committee.
     *
     * @param committee the committee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated committee,
     * or with status 400 (Bad Request) if the committee is not valid,
     * or with status 500 (Internal Server Error) if the committee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/committees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Committee> updateCommittee(@Valid @RequestBody Committee committee) throws URISyntaxException {
        log.debug("REST request to update Committee : {}", committee);
        if (committee.getId() == null) {
            return createCommittee(committee);
        }
        Committee result = committeeService.save(committee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("committee", committee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /committees : get all the committees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of committees in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/committees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Committee>> getAllCommittees(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Committees");
        Page<Committee> page = committeeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/committees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /committees/:id : get the "id" committee.
     *
     * @param id the id of the committee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the committee, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/committees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Committee> getCommittee(@PathVariable Long id) {
        log.debug("REST request to get Committee : {}", id);
        Committee committee = committeeService.findOne(id);
        return Optional.ofNullable(committee)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /committees/:id : delete the "id" committee.
     *
     * @param id the id of the committee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/committees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCommittee(@PathVariable Long id) {
        log.debug("REST request to delete Committee : {}", id);
        committeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("committee", id.toString())).build();
    }

}
