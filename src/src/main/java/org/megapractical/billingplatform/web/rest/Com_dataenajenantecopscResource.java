package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_dataenajenantecopsc;
import org.megapractical.billingplatform.service.Com_dataenajenantecopscService;
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
 * REST controller for managing Com_dataenajenantecopsc.
 */
@RestController
@RequestMapping("/api")
public class Com_dataenajenantecopscResource {

    private final Logger log = LoggerFactory.getLogger(Com_dataenajenantecopscResource.class);
        
    @Inject
    private Com_dataenajenantecopscService com_dataenajenantecopscService;
    
    /**
     * POST  /com-dataenajenantecopscs : Create a new com_dataenajenantecopsc.
     *
     * @param com_dataenajenantecopsc the com_dataenajenantecopsc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_dataenajenantecopsc, or with status 400 (Bad Request) if the com_dataenajenantecopsc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataenajenantecopscs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataenajenantecopsc> createCom_dataenajenantecopsc(@Valid @RequestBody Com_dataenajenantecopsc com_dataenajenantecopsc) throws URISyntaxException {
        log.debug("REST request to save Com_dataenajenantecopsc : {}", com_dataenajenantecopsc);
        if (com_dataenajenantecopsc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_dataenajenantecopsc", "idexists", "A new com_dataenajenantecopsc cannot already have an ID")).body(null);
        }
        Com_dataenajenantecopsc result = com_dataenajenantecopscService.save(com_dataenajenantecopsc);
        return ResponseEntity.created(new URI("/api/com-dataenajenantecopscs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_dataenajenantecopsc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-dataenajenantecopscs : Updates an existing com_dataenajenantecopsc.
     *
     * @param com_dataenajenantecopsc the com_dataenajenantecopsc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_dataenajenantecopsc,
     * or with status 400 (Bad Request) if the com_dataenajenantecopsc is not valid,
     * or with status 500 (Internal Server Error) if the com_dataenajenantecopsc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataenajenantecopscs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataenajenantecopsc> updateCom_dataenajenantecopsc(@Valid @RequestBody Com_dataenajenantecopsc com_dataenajenantecopsc) throws URISyntaxException {
        log.debug("REST request to update Com_dataenajenantecopsc : {}", com_dataenajenantecopsc);
        if (com_dataenajenantecopsc.getId() == null) {
            return createCom_dataenajenantecopsc(com_dataenajenantecopsc);
        }
        Com_dataenajenantecopsc result = com_dataenajenantecopscService.save(com_dataenajenantecopsc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_dataenajenantecopsc", com_dataenajenantecopsc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-dataenajenantecopscs : get all the com_dataenajenantecopscs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_dataenajenantecopscs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-dataenajenantecopscs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_dataenajenantecopsc>> getAllCom_dataenajenantecopscs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_dataenajenantecopscs");
        Page<Com_dataenajenantecopsc> page = com_dataenajenantecopscService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-dataenajenantecopscs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-dataenajenantecopscs/:id : get the "id" com_dataenajenantecopsc.
     *
     * @param id the id of the com_dataenajenantecopsc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_dataenajenantecopsc, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-dataenajenantecopscs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataenajenantecopsc> getCom_dataenajenantecopsc(@PathVariable Long id) {
        log.debug("REST request to get Com_dataenajenantecopsc : {}", id);
        Com_dataenajenantecopsc com_dataenajenantecopsc = com_dataenajenantecopscService.findOne(id);
        return Optional.ofNullable(com_dataenajenantecopsc)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-dataenajenantecopscs/:id : delete the "id" com_dataenajenantecopsc.
     *
     * @param id the id of the com_dataenajenantecopsc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-dataenajenantecopscs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_dataenajenantecopsc(@PathVariable Long id) {
        log.debug("REST request to delete Com_dataenajenantecopsc : {}", id);
        com_dataenajenantecopscService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_dataenajenantecopsc", id.toString())).build();
    }

}
