package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.domain.User;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
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

    @Inject
    private UserService userService;

    @Inject
    private Type_taxpayerService type_taxpayerService;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

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
            Long id = new Long("1");
            Long idtypeevent = new Long("2");
            tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_emitter", "idexists", "A new free_emitter cannot already have an ID")).body(null);
        }

        Free_emitter rfc = free_emitterService.findOneByRfc(free_emitter.getRfc());
        if(rfc != null){
            Long id = new Long("1");
            Long idtypeevent = new Long("2");
            tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));

            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_emitter", "rfcexists", "A new free_emitter cannot already have an RFC")).body(null);
        }

        free_emitter.setActivated(true);
        free_emitter.setCreate_date(ZonedDateTime.now());

        String login = SecurityUtils.getCurrentUserLogin();
        free_emitter.setUser(userRepository.findOneByLogin(login).get());

        Free_emitter result = free_emitterService.save(free_emitter);
        Long id = new Long("1");
        Long idtypeevent = new Long("1");
        tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));

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

        if(free_emitter.getPass_certificate() != null && free_emitter.getInfo_certificate()!= null){
            String [] response = free_emitterService.validateCertificate(free_emitter.getFilecertificate(), free_emitter.getFilekey(), free_emitter.getPass_certificate());
            free_emitter.setInfo_certificate(response[1]);

            free_emitter = free_emitterService.InfoCertificate(free_emitter);
            //log.debug("RFC del certificado: " + free_emitter.getRfc_certificate());
            if(free_emitter.getRfc().compareTo(free_emitter.getRfc_certificate())!=0) {
                free_emitter.setInfo_certificate("ERROR: El RFC del emisor es diferente al RFC del certificado");
                free_emitter.setNumber_certificate(null);
                free_emitter.setDate_certificate(null);
                free_emitter.setRfc_certificate(null);
                free_emitter.setBussines_name_cert(null);
                free_emitter.setDate_created_cert(null);
                free_emitter.setDate_expiration_cert(null);
                free_emitter.setValid_days_cert(null);
            }else {
                if (response[0].compareTo("0") != 0) {
                    free_emitter.setNumber_certificate(null);
                    free_emitter.setDate_certificate(null);
                    free_emitter.setRfc_certificate(null);
                    free_emitter.setBussines_name_cert(null);
                    free_emitter.setDate_created_cert(null);
                    free_emitter.setDate_expiration_cert(null);
                    free_emitter.setValid_days_cert(null);
                } else {
                    free_emitter.setInfo_certificate(null);
                }
            }
            return new ResponseEntity<Free_emitter>(free_emitter,HttpStatus.OK);

        }else {
            if(free_emitter.getFilecertificate() != null) {
                free_emitter = free_emitterService.InfoCertificate(free_emitter);
                //log.debug("RFC del certificado: " + free_emitter.getRfc_certificate());
                if (free_emitter.getRfc().compareTo(free_emitter.getRfc_certificate()) != 0) {
                    free_emitter.setInfo_certificate("ERROR: El RFC del emisor es diferente al RFC del certificado");
                    free_emitter.setNumber_certificate(null);
                    free_emitter.setDate_certificate(null);
                    free_emitter.setRfc_certificate(null);
                    free_emitter.setBussines_name_cert(null);
                    free_emitter.setDate_created_cert(null);
                    free_emitter.setDate_expiration_cert(null);
                    free_emitter.setValid_days_cert(null);
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_emitter", "diferentRFC", "ERROR: Emitter RFC is diferent to Certificate RFC")).body(null);
                }
            }

            if (free_emitter.getId() == null) {
                return createFree_emitter(free_emitter);
            }
            Free_emitter rfc = free_emitterService.findOneByRfc(free_emitter.getRfc());

            if (rfc != null) {
                if (rfc.getId().toString().compareTo(free_emitter.getId().toString()) != 0) {
                    Long id = new Long("2");
                    Long idtypeevent = new Long("2");
                    tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));

                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_emitter", "rfcexists", "A new free_emitter cannot already have an RFC")).body(null);
                }
            }
            Free_emitter result = free_emitterService.save(free_emitter);
            Long id = new Long("2");
            Long idtypeevent = new Long("1");
            tracemgService.saveTrace(audit_event_typeService.findOne(id), c_state_eventService.findOne(idtypeevent));


            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("free_emitter", free_emitter.getId().toString()))
                .body(result);
        }
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
     * GET  /free-emitters/:id : get the free_emitter by id.
     *
     * @param id the id of the free_emitter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_emitter, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-emitters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_emitter> getFree_emitter(@PathVariable Integer id) {
        log.debug("REST request to get Free_emitter by ID : {}", id);

        String login = SecurityUtils.getCurrentUserLogin();
        Free_emitter free_emitter = free_emitterService.findOneByUser(userRepository.findOneByLogin(login).get());

        if(free_emitter == null) {
            free_emitter = new Free_emitter();
            Optional<User> user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
            if(user.isPresent()){
                free_emitter.setRfc(user.get().getRFC());
                free_emitter.setEmail(user.get().getEmail());
                free_emitter.setPhone1(user.get().getPhone());
                if(free_emitter.getRfc().length() == 12){
                    Long idtax_payer = new Long("1");
                    free_emitter.setType_taxpayer(type_taxpayerService.findOne(idtax_payer));
                }else {
                    Long idtax_payer = new Long("2");
                    free_emitter.setType_taxpayer(type_taxpayerService.findOne(idtax_payer));
                }
            }
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
