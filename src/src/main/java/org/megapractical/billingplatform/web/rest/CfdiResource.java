package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cfdi.
 */
@RestController
@RequestMapping("/api")
public class CfdiResource {

    private final Logger log = LoggerFactory.getLogger(CfdiResource.class);

    @Inject
    private CfdiService cfdiService;

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
     * POST  /cfdis : Create a new cfdi.
     *
     * @param cfdi the cfdi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfdi, or with status 400 (Bad Request) if the cfdi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> createCfdi(@Valid @RequestBody Cfdi cfdi) throws URISyntaxException {
        log.debug("REST request to save Cfdi : {}", cfdi);
        if (cfdi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cfdi", "idexists", "A new cfdi cannot already have an ID")).body(null);
        }
        Cfdi result = cfdiService.save(cfdi);
        return ResponseEntity.created(new URI("/api/cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cfdi", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfdis : Updates an existing cfdi.
     *
     * @param cfdi the cfdi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfdi,
     * or with status 400 (Bad Request) if the cfdi is not valid,
     * or with status 500 (Internal Server Error) if the cfdi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> updateCfdi(@Valid @RequestBody Cfdi cfdi) throws URISyntaxException {
        log.debug("REST request to update Cfdi : {}", cfdi);
        if (cfdi.getId() == null) {
            return createCfdi(cfdi);
        }
        Cfdi result = cfdiService.save(cfdi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfdi", cfdi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfdis : get all the cfdis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cfdis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"folio_fiscal","rfc_receiver","fromDate", "toDate", "idcfdi_type_doc","serie", "folio", "idaccount",
        "pre", "send", "cancel", "reciever"})
    @Timed
    public ResponseEntity<List<Cfdi>> getAllCfdis(@RequestParam(value = "folio_fiscal") String folio_fiscal,
                                                  @RequestParam(value = "rfc_receiver") String rfc_receiver,
                                                  @RequestParam(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                  @RequestParam(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                  @RequestParam(value = "idcfdi_type_doc") Integer idcfdi_type_doc,
                                                  @RequestParam(value = "serie") String serie,
                                                  @RequestParam(value = "folio") String folio,
                                                  @RequestParam(value = "idaccount") Integer idaccount,
                                                  @RequestParam(value = "pre") Integer pre,
                                                  @RequestParam(value = "send") Integer send,
                                                  @RequestParam(value = "cancel") Integer cancel,
                                                  @RequestParam(value = "reciever") Integer reciever,
                                                  Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cfdis");
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
                if (idaccount == 0 && folio_fiscal.compareTo(" ") == 0 && rfc_receiver.compareTo(" ") == 0 &&
                    fromDate.toString().compareTo("0001-01-01") == 0 && toDate.toString().compareTo("0001-01-01") == 0 &&
                    idcfdi_type_doc == 0 && serie.compareTo(" ") == 0 && folio.compareTo(" ") == 0) {
                    log.debug("Obtener todos para el admin");
                    Page<Cfdi> page = cfdiService.findAll(pageable);
                    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfdis");
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

                    Page<Cfdi> page = cfdiService.findCustom(folio_fiscal, rfc_receiver, fromDate, toDate, serie,
                        folio, idaccount, idcfdi_type_doc, pre, send, cancel, reciever, pageable);
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
                Page<Cfdi> page = cfdiService.findCustom(folio_fiscal,rfc_receiver,fromDate,toDate,serie,
                    folio,idaccount,idcfdi_type_doc,pre,send,cancel,reciever,pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-cfdis");
                Long idauditevent = new Long("36");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTrace(audit_event_type, c_state_event);
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }
        }
        Long idauditevent = new Long("36");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("2");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Cfdi", "notfound", "CFDI not found")).body(null);

    }

    /**
     * GET  /cfdis/:id : get the "id" cfdi.
     *
     * @param id the id of the cfdi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfdi, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cfdis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> getCfdi(@PathVariable Long id) {
        log.debug("REST request to get Cfdi : {}", id);
        Cfdi cfdi = cfdiService.findOne(id);
        return Optional.ofNullable(cfdi)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cfdis/:id : delete the "id" cfdi.
     *
     * @param id the id of the cfdi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cfdis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCfdi(@PathVariable Long id) {
        log.debug("REST request to delete Cfdi : {}", id);
        cfdiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cfdi", id.toString())).build();
    }

}
