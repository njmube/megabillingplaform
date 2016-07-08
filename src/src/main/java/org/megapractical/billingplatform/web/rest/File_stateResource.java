package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.File_state;
import org.megapractical.billingplatform.service.File_stateService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing File_state.
 */
@RestController
@RequestMapping("/api")
public class File_stateResource {

    private final Logger log = LoggerFactory.getLogger(File_stateResource.class);

    @Inject
    private File_stateService file_stateService;

    /**
     * POST  /file-states : Create a new file_state.
     *
     * @param file_state the file_state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new file_state, or with status 400 (Bad Request) if the file_state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/file-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<File_state> createFile_state(@RequestBody File_state file_state) throws URISyntaxException {
        log.debug("REST request to save File_state : {}", file_state);
        if (file_state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("file_state", "idexists", "A new file_state cannot already have an ID")).body(null);
        }
        File_state result = file_stateService.save(file_state);
        return ResponseEntity.created(new URI("/api/file-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("file_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-states : Updates an existing file_state.
     *
     * @param file_state the file_state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated file_state,
     * or with status 400 (Bad Request) if the file_state is not valid,
     * or with status 500 (Internal Server Error) if the file_state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/file-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<File_state> updateFile_state(@RequestBody File_state file_state) throws URISyntaxException {
        log.debug("REST request to update File_state : {}", file_state);
        if (file_state.getId() == null) {
            return createFile_state(file_state);
        }
        File_state result = file_stateService.save(file_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("file_state", file_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-states : get all the file_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of file_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/file-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"filtername"})
    @Timed
    public ResponseEntity<List<File_state>> getAllFile_states(@RequestParam(value = "filtername") String filtername,
                                                              Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of File_states");
        if(filtername.compareTo(" ")==0 || filtername.isEmpty()) {
            Page<File_state> page = file_stateService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-states");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else
        {
            Page<File_state> page = file_stateService.findAllByName(filtername,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-states");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /file-states/:id : get the "id" file_state.
     *
     * @param id the id of the file_state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the file_state, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/file-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<File_state> getFile_state(@PathVariable Long id) {
        log.debug("REST request to get File_state : {}", id);
        File_state file_state = file_stateService.findOne(id);
        return Optional.ofNullable(file_state)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /file-states/:id : delete the "id" file_state.
     *
     * @param id the id of the file_state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/file-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFile_state(@PathVariable Long id) {
        log.debug("REST request to delete File_state : {}", id);
        file_stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("file_state", id.toString())).build();
    }

}
