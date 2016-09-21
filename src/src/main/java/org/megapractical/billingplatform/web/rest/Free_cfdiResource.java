package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.service.Free_cfdiService;
import org.megapractical.billingplatform.service.Free_emitterService;
import org.megapractical.billingplatform.service.UserService;
import org.megapractical.billingplatform.web.rest.dto.Free_cfdiDTO;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * REST controller for managing Free_cfdi.
 */
@RestController
@RequestMapping("/api")
public class Free_cfdiResource {

    private final Logger log = LoggerFactory.getLogger(Free_cfdiResource.class);

    @Inject
    private Free_cfdiService free_cfdiService;

    @Inject
    private Free_emitterService free_emitterService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    @Inject
    private MailService mailService;

    /**
     * POST  /free-cfdis : Create a new free_cfdi.
     *
     * @param free_cfdi_dto the free_cfdi_dto to create free_cfdi
     * @return the ResponseEntity with status 201 (Created) and with body the new free_cfdi, or with status 400 (Bad Request) if the free_cfdi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    //public ResponseEntity<Free_cfdi> createFree_cfdi(@Valid @RequestBody Free_cfdi free_cfdi) throws URISyntaxException {
    public ResponseEntity<Free_cfdi> createFree_cfdi(@Valid @RequestBody Free_cfdiDTO free_cfdi_dto) throws URISyntaxException {

        Free_cfdi free_cfdi = free_cfdi_dto.getFreeCFDI();

        log.debug("REST request to save Free_cfdi : {}", free_cfdi);
        if (free_cfdi.getId() != null) {
            Long idauditevent = new Long("4");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type,c_state_event);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_cfdi", "idexists", "A new free_cfdi cannot already have an ID")).body(null);
        }

        free_cfdi.setVersion("3.2");
        free_cfdi.setDate_expedition(ZonedDateTime.now());

        String place_expedition = free_cfdi.getFree_emitter().getC_country().getName();

        if(free_cfdi.getFree_emitter().getC_state() != null)
            place_expedition += ", " + free_cfdi.getFree_emitter().getC_state().getName();

        if(free_cfdi.getFree_emitter().getC_municipality() != null)
            place_expedition += ", " + free_cfdi.getFree_emitter().getC_municipality().getName();

        if(free_cfdi.getFree_emitter().getC_colony() != null)
            place_expedition += ", " + free_cfdi.getFree_emitter().getC_colony().getCode();

        if(free_cfdi.getFree_emitter().getC_zip_code() != null)
            place_expedition += ", " + free_cfdi.getFree_emitter().getC_zip_code().getCode();

        free_cfdi.setPlace_expedition(place_expedition);
        free_cfdi.setStamp("stamp");
        free_cfdi.setNo_certificate("no_cetificate");
        free_cfdi.setCertificate("cetificate");

        free_cfdi_dto.setFreeCFDI(free_cfdi);
        Free_cfdi result = free_cfdiService.save(free_cfdi_dto);

        Long idauditevent = new Long("4");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);

        //Sending emails
        List<String> attachments = new ArrayList<>();
        attachments.add(result.getPath_cfdi()+".xml");
        attachments.add(result.getPath_cfdi()+".pdf");

        mailService.sendNewFreeCFDICreatedToEmitterEmail(result, attachments);
        mailService.sendNewFreeCFDICreatedToReceiverEmail(result, attachments);

        return ResponseEntity.created(new URI("/api/free-cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_cfdi", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-cfdis : Updates an existing free_cfdi.
     *
     * @param free_cfdi_dto the free_cfdi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_cfdi,
     * or with status 400 (Bad Request) if the free_cfdi is not valid,
     * or with status 500 (Internal Server Error) if the free_cfdi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_cfdi> updateFree_cfdi(@Valid @RequestBody Free_cfdiDTO free_cfdi_dto) throws URISyntaxException {
        Free_cfdi free_cfdi = free_cfdi_dto.getFreeCFDI();
        log.debug("REST request to update Free_cfdi : {}", free_cfdi);
        log.debug("Actualizando estado de free_cfdi: " + free_cfdi.getCfdi_states().getName());

        if (free_cfdi.getId() == null) {
            return createFree_cfdi(free_cfdi_dto);
        }

        if(free_cfdi.getCfdi_states().getId() == 2){
            Free_cfdi pre = free_cfdiService.findOne(free_cfdi.getId());
            if(pre.getCfdi_states().getId() == 1){
                log.debug("Cancelando free_cfdi");
                free_cfdiService.cancelarFree_cfdi(free_cfdi);
            }
        }
        Free_cfdi result = free_cfdiService.save(free_cfdi_dto);
        log.debug("Estado del free_cfdi despeus de salvado: " + result.getCfdi_states().getName());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_cfdi", free_cfdi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-cfdis : get all the free_cfdis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_cfdis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"idFree_cfdi", "folio_fiscal","rfc_receiver","fromDate", "toDate", "idState","serie", "folio"})
    @Timed
    public ResponseEntity<List<Free_cfdi>> getAllFree_cfdis(@RequestParam(value = "idFree_cfdi") Integer idFree_cfdi,
                                                            @RequestParam(value = "folio_fiscal") String folio_fiscal,
                                                            @RequestParam(value = "rfc_receiver") String rfc_receiver,
                                                            @RequestParam(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                            @RequestParam(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                            @RequestParam(value = "idState") Integer idState,
                                                            @RequestParam(value = "serie") String serie,
                                                            @RequestParam(value = "folio") String folio,
                                                            Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_cfdis");

        String login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(login);
        if(user.isPresent()){
            boolean administrator = false;
            for(Authority item: user.get().getAuthorities()){
                if(item.getName().compareTo("ROLE_ADMIN")==0){
                    administrator = true;
                }
            }
            if(administrator){
                if (idFree_cfdi == 0 && folio_fiscal.compareTo(" ") == 0 && rfc_receiver.compareTo(" ") == 0 &&
                    fromDate.toString().compareTo("0001-01-01") == 0 && toDate.toString().compareTo("0001-01-01") == 0 &&
                    idState == 0 && serie.compareTo(" ") == 0 && folio.compareTo(" ") == 0) {
                    log.debug("Obtener todos para el admin");
                    Page<Free_cfdi> page = free_cfdiService.findAll(pageable);
                    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-cfdis");
                    Long idauditevent = new Long("36");
                    Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                    C_state_event c_state_event;
                    Long idstate = new Long("1");
                    c_state_event = c_state_eventService.findOne(idstate);
                    tracemgService.saveTrace(audit_event_type, c_state_event);

                    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
                }else
                {
                    log.debug("Obtener alguno para el admin");
                    LocalDate inicio = fromDate;
                    LocalDate datefinal = toDate;
                    Page<Free_cfdi> page = free_cfdiService.findCustomAdmin(idFree_cfdi, folio_fiscal, rfc_receiver,
                        inicio, datefinal, idState, serie, folio, pageable);
                    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-cfdis");
                    Long idauditevent = new Long("36");
                    Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                    C_state_event c_state_event;
                    Long idstate = new Long("1");
                    c_state_event = c_state_eventService.findOne(idstate);
                    tracemgService.saveTrace(audit_event_type, c_state_event);
                    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
                }
            }else{
                Free_emitter free_emitter = free_emitterService.findOneByUser(userRepository.findOneByLogin(login).get());
                if(free_emitter != null) {
                    Long idauditevent = new Long("36");
                    Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                    C_state_event c_state_event;
                    Long idstate = new Long("1");
                    c_state_event = c_state_eventService.findOne(idstate);
                    tracemgService.saveTrace(audit_event_type, c_state_event);

                    if (idFree_cfdi == 0 && folio_fiscal.compareTo(" ") == 0 && rfc_receiver.compareTo(" ") == 0 &&
                        fromDate.toString().compareTo("0001-01-01") == 0 && toDate.toString().compareTo("0001-01-01") == 0 &&
                        idState == 0 && serie.compareTo(" ") == 0 && folio.compareTo(" ") == 0) {
                        log.debug("Obtener todos");
                        Page<Free_cfdi> page = free_cfdiService.findByFree_emitter(free_emitter, pageable);
                        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-cfdis");

                        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
                    } else {
                        log.debug("Obtener alguno");
                        LocalDate inicio = fromDate;
                        LocalDate datefinal = toDate;
                        Page<Free_cfdi> page = free_cfdiService.findCustom(idFree_cfdi, folio_fiscal, rfc_receiver,
                            inicio, datefinal, idState, serie, folio, free_emitter, pageable);
                        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-cfdis");
                        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
                    }
                }else
                {
                    Long idauditevent = new Long("36");
                    Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                    C_state_event c_state_event;
                    Long idstate = new Long("2");
                    c_state_event = c_state_eventService.findOne(idstate);
                    tracemgService.saveTrace(audit_event_type, c_state_event);
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_cfdi", "notfound", "Free CFDI not found")).body(null);
                }
            }
        }
        Long idauditevent = new Long("36");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("2");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_cfdi", "notfound", "Free CFDI not found")).body(null);

    }

    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"idFree_cfdi"})
    @Timed
    public ResponseEntity<Free_cfdi> getZip(@RequestParam(value = "idFree_cfdi") Integer idFree_cfdi)
        throws URISyntaxException {
        log.debug("REST request to get a zip of Free_cfdis");
        if(idFree_cfdi != null)
        {
            Free_cfdi temp = new Free_cfdi();
            byte [] zip = free_cfdiService.getZip(idFree_cfdi);
            temp.setFilexml(zip);
            return ResponseEntity.ok().body(temp);
        }

        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_cfdi", "notfound", "Free CFDI not found")).body(null);

    }

    /**
     * GET  /free-cfdis/:id : get the "id" free_cfdi.
     *
     * @param id the id of the free_cfdi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_cfdi, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-cfdis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_cfdi> getFree_cfdi(@PathVariable Long id) {
        log.debug("REST request to get Free_cfdi : {}", id);
        Long idauditevent = new Long("36");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);

        Free_cfdi free_cfdi = free_cfdiService.findOne(id);
        return Optional.ofNullable(free_cfdi)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-cfdis/:id : delete the "id" free_cfdi.
     *
     * @param id the id of the free_cfdi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-cfdis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_cfdi(@PathVariable Long id) {
        log.debug("REST request to delete Free_cfdi : {}", id);
        free_cfdiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_cfdi", id.toString())).build();
    }

}
