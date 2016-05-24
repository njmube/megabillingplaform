package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.File_type;
import org.megapractical.billingplatform.service.File_typeService;
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
 * REST controller for managing File_type.
 */
@RestController
@RequestMapping("/api")
public class File_typeResource {

    private final Logger log = LoggerFactory.getLogger(File_typeResource.class);
        
    @Inject
    private File_typeService file_typeService;
    
    /**
     * POST  /file-types : Create a new file_type.
     *
     * @param file_type the file_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new file_type, or with status 400 (Bad Request) if the file_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/file-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<File_type> createFile_type(@RequestBody File_type file_type) throws URISyntaxException {
        log.debug("REST request to save File_type : {}", file_type);
        if (file_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("file_type", "idexists", "A new file_type cannot already have an ID")).body(null);
        }
        File_type result = file_typeService.save(file_type);
        return ResponseEntity.created(new URI("/api/file-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("file_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-types : Updates an existing file_type.
     *
     * @param file_type the file_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated file_type,
     * or with status 400 (Bad Request) if the file_type is not valid,
     * or with status 500 (Internal Server Error) if the file_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/file-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<File_type> updateFile_type(@RequestBody File_type file_type) throws URISyntaxException {
        log.debug("REST request to update File_type : {}", file_type);
        if (file_type.getId() == null) {
            return createFile_type(file_type);
        }
        File_type result = file_typeService.save(file_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("file_type", file_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-types : get all the file_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of file_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/file-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<File_type>> getAllFile_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of File_types");
        Page<File_type> page = file_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/file-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /file-types/:id : get the "id" file_type.
     *
     * @param id the id of the file_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the file_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/file-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<File_type> getFile_type(@PathVariable Long id) {
        log.debug("REST request to get File_type : {}", id);
        File_type file_type = file_typeService.findOne(id);
        return Optional.ofNullable(file_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /file-types/:id : delete the "id" file_type.
     *
     * @param id the id of the file_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/file-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFile_type(@PathVariable Long id) {
        log.debug("REST request to delete File_type : {}", id);
        file_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("file_type", id.toString())).build();
    }

}
