package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Type_taxpayer;
import org.megapractical.billingplatform.service.Type_taxpayerService;
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
 * REST controller for managing Type_taxpayer.
 */
@RestController
@RequestMapping("/api")
public class Type_taxpayerResource {

    private final Logger log = LoggerFactory.getLogger(Type_taxpayerResource.class);
        
    @Inject
    private Type_taxpayerService type_taxpayerService;
    
    /**
     * POST  /type-taxpayers : Create a new type_taxpayer.
     *
     * @param type_taxpayer the type_taxpayer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new type_taxpayer, or with status 400 (Bad Request) if the type_taxpayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/type-taxpayers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_taxpayer> createType_taxpayer(@Valid @RequestBody Type_taxpayer type_taxpayer) throws URISyntaxException {
        log.debug("REST request to save Type_taxpayer : {}", type_taxpayer);
        if (type_taxpayer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("type_taxpayer", "idexists", "A new type_taxpayer cannot already have an ID")).body(null);
        }
        Type_taxpayer result = type_taxpayerService.save(type_taxpayer);
        return ResponseEntity.created(new URI("/api/type-taxpayers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("type_taxpayer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-taxpayers : Updates an existing type_taxpayer.
     *
     * @param type_taxpayer the type_taxpayer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated type_taxpayer,
     * or with status 400 (Bad Request) if the type_taxpayer is not valid,
     * or with status 500 (Internal Server Error) if the type_taxpayer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/type-taxpayers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_taxpayer> updateType_taxpayer(@Valid @RequestBody Type_taxpayer type_taxpayer) throws URISyntaxException {
        log.debug("REST request to update Type_taxpayer : {}", type_taxpayer);
        if (type_taxpayer.getId() == null) {
            return createType_taxpayer(type_taxpayer);
        }
        Type_taxpayer result = type_taxpayerService.save(type_taxpayer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("type_taxpayer", type_taxpayer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-taxpayers : get all the type_taxpayers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of type_taxpayers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/type-taxpayers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Type_taxpayer>> getAllType_taxpayers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Type_taxpayers");
        Page<Type_taxpayer> page = type_taxpayerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-taxpayers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-taxpayers/:id : get the "id" type_taxpayer.
     *
     * @param id the id of the type_taxpayer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the type_taxpayer, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/type-taxpayers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_taxpayer> getType_taxpayer(@PathVariable Long id) {
        log.debug("REST request to get Type_taxpayer : {}", id);
        Type_taxpayer type_taxpayer = type_taxpayerService.findOne(id);
        return Optional.ofNullable(type_taxpayer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-taxpayers/:id : delete the "id" type_taxpayer.
     *
     * @param id the id of the type_taxpayer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/type-taxpayers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteType_taxpayer(@PathVariable Long id) {
        log.debug("REST request to delete Type_taxpayer : {}", id);
        type_taxpayerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("type_taxpayer", id.toString())).build();
    }

}
