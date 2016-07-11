package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.service.Free_emitterService;
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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Free_emitter.
 */
@RestController
@RequestMapping("/api")
public class Free_emitterResource {

    private final Logger log = LoggerFactory.getLogger(Free_emitterResource.class);

    @Inject
    private Free_emitterService free_emitterService;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /free-emitters : Create a new free_emitter.
     *
     * @param free_emitter the free_emitter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_emitter, or with status 400 (Bad Request) if the free_emitter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-emitters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_emitter> createFree_emitter(@Valid @RequestBody Free_emitter free_emitter) throws URISyntaxException {
        log.debug("REST request to create Free_emitter : {}", free_emitter);
        if (free_emitter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_emitter", "idexists", "A new free_emitter cannot already have an ID")).body(null);
        }
        Free_emitter rfc = free_emitterService.findOneByRfc(free_emitter.getRfc());
        if(rfc != null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_emitter", "rfcexists", "A new free_emitter cannot already have an RFC")).body(null);
        }

        free_emitter.setCreate_date(ZonedDateTime.now());
        Free_emitter result = free_emitterService.save(free_emitter);
        return ResponseEntity.created(new URI("/api/free-emitters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_emitter", result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /free-emitters : Updates an existing free_emitter.
     *
     * @param free_emitter the free_emitter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_emitter,
     * or with status 400 (Bad Request) if the free_emitter is not valid,
     * or with status 500 (Internal Server Error) if the free_emitter couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-emitters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_emitter> updateFree_emitter(@RequestBody Free_emitter free_emitter) throws URISyntaxException {
        log.debug("REST request to update Free_emitter : {}", free_emitter);

        if (free_emitter.getId() == null) {
            return createFree_emitter(free_emitter);
        }
        Free_emitter rfc = free_emitterService.findOneByRfc(free_emitter.getRfc());
        if(rfc != null){
            if(rfc.getId() != free_emitter.getId())
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_emitter", "rfcexists", "A new free_emitter cannot already have an RFC")).body(null);
        }
        Free_emitter result = free_emitterService.save(free_emitter);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_emitter", free_emitter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-emitters : get all the free_emitters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_emitters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-emitters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_emitter>> getAllFree_emitters(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_emitters");
        Page<Free_emitter> page = free_emitterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-emitters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /free-emitters/:login : get the free_emitter by login.
     *
     * @param login the id of the free_emitter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_emitter, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-emitters/{login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_emitter> getFree_emitter(@PathVariable String login) {
        log.debug("REST request to get Free_emitter by user Login : {}", login);

        Free_emitter free_emitter = free_emitterService.findOneByUser(userRepository.findOneByLogin(login).get());

        if(free_emitter == null) {
            free_emitter = new Free_emitter();
        }
        return Optional.ofNullable(free_emitter)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * DELETE  /free-emitters/:id : delete the "id" free_emitter.
     *
     * @param id the id of the free_emitter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-emitters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_emitter(@PathVariable Long id) {
        log.debug("REST request to delete Free_emitter : {}", id);
        free_emitterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_emitter", id.toString())).build();
    }

}
