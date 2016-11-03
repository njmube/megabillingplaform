package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.web.rest.dto.CfdiDTO;
import org.megapractical.billingplatform.web.rest.dto.ConceptDTO;
import org.megapractical.billingplatform.web.rest.dto.Part_conceptDTO;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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
    private Taxpayer_transactionsService taxpayer_transactionsService;

    @Inject
    private Taxpayer_series_folioService taxpayer_series_folioService;

    @Inject
    private ConceptService conceptService;

    @Inject
    private Tax_transferedService tax_transferedService;

    @Inject
    private Tax_retentionsService tax_retentionsService;

    @Inject
    private Customs_infoService customs_infoService;

    @Inject
    private Part_conceptService part_conceptService;

    @Inject
    private Customs_info_partService customs_info_partService;

    @Inject
    private Com_taxregistrationService com_taxregistrationService;

    @Inject
    private Com_pficService com_pficService;

    @Inject
    private Com_accreditation_iepsService com_accreditation_iepsService;

    @Inject
    private Com_taxlegendsService com_taxlegendsService;

    @Inject
    private Com_legendsService com_legendsService;

    /*@Inject
    private MailService mailService;*/
    /**
     * POST  /cfdis : Create a new cfdi.
     *
     * @param cfdiDTO the cfdi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfdi, or with status 400 (Bad Request) if the cfdi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> createCfdi(@RequestBody CfdiDTO cfdiDTO) throws URISyntaxException {
        Cfdi cfdi = cfdiDTO.getCfdi();
        log.debug("REST request to save Cfdi : {}", cfdi);
        if (cfdi.getId() != null) {
            Long idauditevent = new Long("49");
            Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
            C_state_event c_state_event;
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);
            tracemgService.saveTrace(audit_event_type,c_state_event);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cfdi", "idexists", "A new cfdi cannot already have an ID")).body(null);
        }

        cfdi.setVersion("3.2");
        cfdi.setDate_expedition(ZonedDateTime.now());

        String place_expedition = cfdi.getTaxpayer_account().getTax_address().getC_country().getName();

        if(cfdi.getTaxpayer_account().getTax_address().getC_state() != null)
            place_expedition += ", " + cfdi.getTaxpayer_account().getTax_address().getC_state().getName();

        if(cfdi.getTaxpayer_account().getTax_address().getC_municipality() != null)
            place_expedition += ", " + cfdi.getTaxpayer_account().getTax_address().getC_municipality().getName();

        if(cfdi.getTaxpayer_account().getTax_address().getC_colony() != null)
            place_expedition += ", " + cfdi.getTaxpayer_account().getTax_address().getC_colony().getCode();

        if(cfdi.getTaxpayer_account().getTax_address().getC_zip_code() != null)
            place_expedition += ", " + cfdi.getTaxpayer_account().getTax_address().getC_zip_code().getCode();

        cfdi.setPlace_expedition(place_expedition);
        cfdi.setCertificate("cetificate");
        cfdi.setNumber_certificate("numbercertificate");

        Cfdi result = cfdiService.save(cfdiDTO);

        Integer taxpayer_accout_id = new Integer(result.getTaxpayer_account().getId().toString());

        Sort defaultSort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = new PageRequest(0, 30, defaultSort);

        //Updating taxpayer transactions
        Page<Taxpayer_transactions> taxpayer_transactions = taxpayer_transactionsService.findByAccount(taxpayer_accout_id, pageable);
        Taxpayer_transactions taxpayer_transactions_account = taxpayer_transactions.getContent().get(0);
        taxpayer_transactions_account.setTransactions_available(taxpayer_transactions_account.getTransactions_available() - 1);
        taxpayer_transactions_account.setTransactions_spent(taxpayer_transactions_account.getTransactions_spent() + 1);
        taxpayer_transactionsService.save(taxpayer_transactions_account);

        //Update taxpayer series folios
        Taxpayer_series_folio taxpayer_series_folio = cfdiDTO.getTaxpayer_series_folio();
        Integer folio_current = 0;
        if(taxpayer_series_folio.getFolio_current() == null){
            folio_current = taxpayer_series_folio.getFolio_start();
        }
        else{
            folio_current = taxpayer_series_folio.getFolio_current() + 1;
        }

        if(folio_current == taxpayer_series_folio.getFolio_end()){
            taxpayer_series_folio.setEnable(false);
            taxpayer_series_folio.setFolio_current(folio_current);
            taxpayer_series_folioService.save(taxpayer_series_folio);

            Page<Taxpayer_series_folio> taxpayer_series_folio_page = taxpayer_series_folioService.findAll(pageable, taxpayer_accout_id);
            for(Taxpayer_series_folio taxpayer_series_folio_item: taxpayer_series_folio_page.getContent()){
                if(taxpayer_series_folio_item.getFolio_current() == null || taxpayer_series_folio_item.getFolio_current() < taxpayer_series_folio_item.getFolio_end()){
                    taxpayer_series_folio_item.setEnable(true);
                    taxpayer_series_folioService.save(taxpayer_series_folio_item);
                    break;
                }
            }
        }
        else {
            taxpayer_series_folio.setFolio_current(folio_current);
            taxpayer_series_folioService.save(taxpayer_series_folio);
        }

        //Saving conceptDTOs
        List<ConceptDTO> conceptDTOs = cfdiDTO.getConceptDTOs();
        BigDecimal ceroValue = new BigDecimal("0");

        for(ConceptDTO conceptDTO: conceptDTOs){
            //save concept
            Concept concept = conceptDTO.getConcept();
            concept.setCfdi(result);
            concept = conceptService.save(concept);

            //save tax trasnfered iva
            Tax_transfered concept_iva = conceptDTO.getConcept_iva();
            if(concept_iva.getAmount().compareTo(ceroValue) == 1){
                concept_iva.setConcept(concept);
                tax_transferedService.save(concept_iva);
            }

            //save tax trasnfered ieps
            Tax_transfered concept_ieps = conceptDTO.getConcept_ieps();
            if(concept_ieps.getAmount().compareTo(ceroValue) == 1){
                concept_ieps.setConcept(concept);
                tax_transferedService.save(concept_ieps);
            }

            //save tax retentions iva
            Tax_retentions tax_retentions_iva = conceptDTO.getTax_retentions_iva();
            if(tax_retentions_iva != null && tax_retentions_iva.getAmount().compareTo(ceroValue) == 1){
                tax_retentions_iva.setConcept(concept);
                tax_retentionsService.save(tax_retentions_iva);
            }

            //save tax retentions isr
            Tax_retentions tax_retentions_isr = conceptDTO.getTax_retentions_isr();
            if(tax_retentions_isr != null && tax_retentions_isr.getAmount().compareTo(ceroValue) == 1){
                tax_retentions_isr.setConcept(concept);
                tax_retentionsService.save(tax_retentions_isr);
            }

            //save customs infos
            List<Customs_info> customs_infos = conceptDTO.getCustoms_infos();
            if(customs_infos != null) {
                for (Customs_info customs_info : customs_infos) {
                    customs_info.setConcept(concept);
                    customs_infoService.save(customs_info);
                }
            }

            //save part concepts
            List<Part_conceptDTO> part_conceptDTOs = conceptDTO.getPart_concepts();
            if(part_conceptDTOs != null) {
                for (Part_conceptDTO part_conceptDTO : part_conceptDTOs) {
                    Part_concept part_concept = part_conceptDTO.getPart_concept();
                    part_concept.setConcept(concept);
                    part_concept = part_conceptService.save(part_concept);

                    List<Customs_info_part> customs_info_parts = part_conceptDTO.getCustoms_info_parts();
                    for (Customs_info_part customs_info_part : customs_info_parts) {
                        customs_info_part.setPart_concept(part_concept);
                        customs_info_partService.save(customs_info_part);
                    }
                }
            }
        }

        //SAVING COMPLEMENTS

        //taxregistration
        Com_taxregistration com_taxregistration = cfdiDTO.getCom_taxregistration();
        if(com_taxregistration != null && com_taxregistration.getVersion() != null) {
            com_taxregistration.setCfdi(result);
            com_taxregistrationService.save(com_taxregistration);
        }

        //pfic
        Com_pfic com_pfic = cfdiDTO.getCom_pfic();
        if(com_pfic != null && com_pfic.getVersion() != null) {
            com_pfic.setCfdi(result);
            com_pficService.save(com_pfic);
        }

        //accreditation_ieps
        Com_accreditation_ieps com_accreditation_ieps = cfdiDTO.getCom_accreditation_ieps();
        if(com_accreditation_ieps != null && com_accreditation_ieps.getVersion() != null) {
            com_accreditation_ieps.setCfdi(result);
            com_accreditation_iepsService.save(com_accreditation_ieps);
        }

        //taxlegends
        Com_taxlegends com_taxlegends = cfdiDTO.getCom_taxlegends();
        if(com_taxlegends != null && com_taxlegends.getVersion() != null) {
            com_taxlegends.setCfdi(result);
            com_taxlegends = com_taxlegendsService.save(com_taxlegends);

            List<Com_legends> com_legends = cfdiDTO.getCom_legends();
            for (Com_legends com_legend : com_legends) {
                com_legend.setCom_taxlegends(com_taxlegends);
                com_legendsService.save(com_legend);
            }
        }



        Long idauditevent = new Long("49");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);

        //Sending emails

        return ResponseEntity.created(new URI("/api/cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cfdi", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfdis : Updates an existing cfdi.
     *
     * @param cfdiDTO the cfdi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfdi,
     * or with status 400 (Bad Request) if the cfdi is not valid,
     * or with status 500 (Internal Server Error) if the cfdi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> updateCfdi(@RequestBody CfdiDTO cfdiDTO) throws URISyntaxException {
        Cfdi cfdi = cfdiDTO.getCfdi();
        log.debug("REST request to update Cfdi : {}", cfdi);
        if (cfdi.getId() == null) {
            return createCfdi(cfdiDTO);
        }
        if(cfdiDTO.getCfdi().getCfdi_states().getId() == 2){
            Cfdi pre = cfdiService.findOne(cfdiDTO.getCfdi().getId());
            if(pre.getCfdi_states().getId() == 1){
                log.debug("Cancelando cfdi");
                cfdiService.cancelarCfdi(cfdiDTO.getCfdi());

                Long idauditevent = new Long("51");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTrace(audit_event_type, c_state_event);
            }
        }
        Cfdi result = cfdiService.save(cfdiDTO);
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
                    Long idauditevent = new Long("50");
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
                    Long idauditevent = new Long("50");
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
                Long idauditevent = new Long("50");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTrace(audit_event_type, c_state_event);
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }
        }
        Long idauditevent = new Long("50");
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
