package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.config.JHipsterProperties;
import org.megapractical.billingplatform.domain.Ring_pack;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.Ring_packService;
import org.megapractical.billingplatform.service.UserService;
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
 * REST controller for managing Ring_pack.
 */
@RestController
@RequestMapping("/api")
public class Ring_packResource {

    private final Logger log = LoggerFactory.getLogger(Ring_packResource.class);

    @Inject
    private Ring_packService ring_packService;

    @Inject
    private UserService userService;

    /**
     * POST  /ring-packs : Create a new ring_pack.
     *
     * @param ring_pack the ring_pack to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ring_pack, or with status 400 (Bad Request) if the ring_pack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ring-packs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ring_pack> createRing_pack(@Valid @RequestBody Ring_pack ring_pack) throws URISyntaxException {
        log.debug("REST request to save Ring_pack : {}", ring_pack);
        if (ring_pack.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ring_pack", "idexists", "A new ring_pack cannot already have an ID")).body(null);
        }
        Ring_pack result = ring_packService.save(ring_pack);
        return ResponseEntity.created(new URI("/api/ring-packs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ring_pack", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ring-packs : Updates an existing ring_pack.
     *
     * @param ring_pack the ring_pack to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ring_pack,
     * or with status 400 (Bad Request) if the ring_pack is not valid,
     * or with status 500 (Internal Server Error) if the ring_pack couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ring-packs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ring_pack> updateRing_pack(@Valid @RequestBody Ring_pack ring_pack) throws URISyntaxException {
        log.debug("REST request to update Ring_pack : {}", ring_pack);
        if (ring_pack.getId() == null) {
            return createRing_pack(ring_pack);
        }
        Ring_pack result = ring_packService.save(ring_pack);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ring_pack", ring_pack.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ring-packs : get all the ring_packs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ring_packs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/ring-packs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ring_pack>> getAllRing_packs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Ring_packs");
        Page<Ring_pack> page = ring_packService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ring-packs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/ring-packs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"idaccount", "idring_pack","count"})
    @Timed
    public ResponseEntity<String> buytransactions(@RequestParam(value = "idaccount") Integer idaccount,
                                                     @RequestParam(value = "idring_pack") Integer idring_pack,
                                                     @RequestParam(value = "count") Integer count)
        throws URISyntaxException {
        log.debug("REST request to buy Ring_packs");

        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.getUserWithAuthoritiesByLogin(login).get();
        Boolean result = ring_packService.buytransactions(idaccount,idring_pack,user.getId(),count);
        if(result)
            return new ResponseEntity<String>(HttpStatus.OK);
        else
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    /**
     * GET  /ring-packs/:id : get the "id" ring_pack.
     *
     * @param id the id of the ring_pack to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ring_pack, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/ring-packs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ring_pack> getRing_pack(@PathVariable Long id) {
        log.debug("REST request to get Ring_pack : {}", id);
        Ring_pack ring_pack = ring_packService.findOne(id);
        return Optional.ofNullable(ring_pack)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ring-packs/:id : delete the "id" ring_pack.
     *
     * @param id the id of the ring_pack to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/ring-packs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRing_pack(@PathVariable Long id) {
        log.debug("REST request to delete Ring_pack : {}", id);
        ring_packService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ring_pack", id.toString())).build();
    }

}
