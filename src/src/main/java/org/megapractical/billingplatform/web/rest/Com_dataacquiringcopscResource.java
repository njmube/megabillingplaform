package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_dataacquiringcopsc;
import org.megapractical.billingplatform.service.Com_dataacquiringcopscService;
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
 * REST controller for managing Com_dataacquiringcopsc.
 */
@RestController
@RequestMapping("/api")
public class Com_dataacquiringcopscResource {

    private final Logger log = LoggerFactory.getLogger(Com_dataacquiringcopscResource.class);
        
    @Inject
    private Com_dataacquiringcopscService com_dataacquiringcopscService;
    
    /**
     * POST  /com-dataacquiringcopscs : Create a new com_dataacquiringcopsc.
     *
     * @param com_dataacquiringcopsc the com_dataacquiringcopsc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_dataacquiringcopsc, or with status 400 (Bad Request) if the com_dataacquiringcopsc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataacquiringcopscs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataacquiringcopsc> createCom_dataacquiringcopsc(@Valid @RequestBody Com_dataacquiringcopsc com_dataacquiringcopsc) throws URISyntaxException {
        log.debug("REST request to save Com_dataacquiringcopsc : {}", com_dataacquiringcopsc);
        if (com_dataacquiringcopsc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_dataacquiringcopsc", "idexists", "A new com_dataacquiringcopsc cannot already have an ID")).body(null);
        }
        Com_dataacquiringcopsc result = com_dataacquiringcopscService.save(com_dataacquiringcopsc);
        return ResponseEntity.created(new URI("/api/com-dataacquiringcopscs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_dataacquiringcopsc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-dataacquiringcopscs : Updates an existing com_dataacquiringcopsc.
     *
     * @param com_dataacquiringcopsc the com_dataacquiringcopsc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_dataacquiringcopsc,
     * or with status 400 (Bad Request) if the com_dataacquiringcopsc is not valid,
     * or with status 500 (Internal Server Error) if the com_dataacquiringcopsc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataacquiringcopscs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataacquiringcopsc> updateCom_dataacquiringcopsc(@Valid @RequestBody Com_dataacquiringcopsc com_dataacquiringcopsc) throws URISyntaxException {
        log.debug("REST request to update Com_dataacquiringcopsc : {}", com_dataacquiringcopsc);
        if (com_dataacquiringcopsc.getId() == null) {
            return createCom_dataacquiringcopsc(com_dataacquiringcopsc);
        }
        Com_dataacquiringcopsc result = com_dataacquiringcopscService.save(com_dataacquiringcopsc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_dataacquiringcopsc", com_dataacquiringcopsc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-dataacquiringcopscs : get all the com_dataacquiringcopscs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_dataacquiringcopscs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-dataacquiringcopscs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_dataacquiringcopsc>> getAllCom_dataacquiringcopscs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_dataacquiringcopscs");
        Page<Com_dataacquiringcopsc> page = com_dataacquiringcopscService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-dataacquiringcopscs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-dataacquiringcopscs/:id : get the "id" com_dataacquiringcopsc.
     *
     * @param id the id of the com_dataacquiringcopsc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_dataacquiringcopsc, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-dataacquiringcopscs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataacquiringcopsc> getCom_dataacquiringcopsc(@PathVariable Long id) {
        log.debug("REST request to get Com_dataacquiringcopsc : {}", id);
        Com_dataacquiringcopsc com_dataacquiringcopsc = com_dataacquiringcopscService.findOne(id);
        return Optional.ofNullable(com_dataacquiringcopsc)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-dataacquiringcopscs/:id : delete the "id" com_dataacquiringcopsc.
     *
     * @param id the id of the com_dataacquiringcopsc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-dataacquiringcopscs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_dataacquiringcopsc(@PathVariable Long id) {
        log.debug("REST request to delete Com_dataacquiringcopsc : {}", id);
        com_dataacquiringcopscService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_dataacquiringcopsc", id.toString())).build();
    }

}
