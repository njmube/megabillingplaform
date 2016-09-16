package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_tfd;
import org.megapractical.billingplatform.service.Com_tfdService;
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
 * REST controller for managing Com_tfd.
 */
@RestController
@RequestMapping("/api")
public class Com_tfdResource {

    private final Logger log = LoggerFactory.getLogger(Com_tfdResource.class);
        
    @Inject
    private Com_tfdService com_tfdService;
    
    /**
     * POST  /com-tfds : Create a new com_tfd.
     *
     * @param com_tfd the com_tfd to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_tfd, or with status 400 (Bad Request) if the com_tfd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-tfds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_tfd> createCom_tfd(@Valid @RequestBody Com_tfd com_tfd) throws URISyntaxException {
        log.debug("REST request to save Com_tfd : {}", com_tfd);
        if (com_tfd.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_tfd", "idexists", "A new com_tfd cannot already have an ID")).body(null);
        }
        Com_tfd result = com_tfdService.save(com_tfd);
        return ResponseEntity.created(new URI("/api/com-tfds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_tfd", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-tfds : Updates an existing com_tfd.
     *
     * @param com_tfd the com_tfd to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_tfd,
     * or with status 400 (Bad Request) if the com_tfd is not valid,
     * or with status 500 (Internal Server Error) if the com_tfd couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-tfds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_tfd> updateCom_tfd(@Valid @RequestBody Com_tfd com_tfd) throws URISyntaxException {
        log.debug("REST request to update Com_tfd : {}", com_tfd);
        if (com_tfd.getId() == null) {
            return createCom_tfd(com_tfd);
        }
        Com_tfd result = com_tfdService.save(com_tfd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_tfd", com_tfd.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-tfds : get all the com_tfds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_tfds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-tfds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_tfd>> getAllCom_tfds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_tfds");
        Page<Com_tfd> page = com_tfdService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-tfds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-tfds/:id : get the "id" com_tfd.
     *
     * @param id the id of the com_tfd to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_tfd, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-tfds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_tfd> getCom_tfd(@PathVariable Long id) {
        log.debug("REST request to get Com_tfd : {}", id);
        Com_tfd com_tfd = com_tfdService.findOne(id);
        return Optional.ofNullable(com_tfd)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-tfds/:id : delete the "id" com_tfd.
     *
     * @param id the id of the com_tfd to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-tfds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_tfd(@PathVariable Long id) {
        log.debug("REST request to delete Com_tfd : {}", id);
        com_tfdService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_tfd", id.toString())).build();
    }

}
