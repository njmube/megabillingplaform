package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.repository.UserRepository;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.service.Free_cfdiService;
import org.megapractical.billingplatform.service.Free_emitterService;
import org.megapractical.billingplatform.service.UserService;
import org.megapractical.billingplatform.web.rest.dto.*;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.math.BigDecimal;
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
    private Free_receiverService free_receiverService;

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

    @Inject
    private Free_conceptService free_conceptService;

    @Inject
    private Free_tax_transferedService free_tax_transferedService;

    @Inject
    private Free_tax_retentionsService free_tax_retentionsService;

    @Inject
    private Free_customs_infoService free_customs_infoService;

    @Inject
    private Free_part_conceptService free_part_conceptService;

    @Inject
    private Freecom_taxregistrationService freecom_taxregistrationService;

    @Inject
    private Freecom_pficService freecom_pficService;

    @Inject
    private Freecom_accreditation_iepsService freecom_accreditation_iepsService;

    @Inject
    private Freecom_taxlegendsService freecom_taxlegendsService;

    @Inject
    private Freecom_legendService freecom_legendService;

    @Inject
    private Freecom_airlineService freecom_airlineService;

    @Inject
    private Freecom_chargeService freecom_chargeService;

    @Inject
    private Freecom_apawService freecom_apawService;

    @Inject
    private Freecom_doneesService freecom_doneesService;

    @Inject
    private Freecom_educational_institutionsService freecom_educational_institutionsService;

    @Inject
    private Freecom_ineService freecom_ineService;

    @Inject
    private Freecom_ine_entityService freecom_ine_entityService;

    @Inject
    private AccountingService accountingService;

    @Inject
    private Freecom_kind_paymentService freecom_kind_paymentService;

    @Inject
    private Freecom_foreign_tourist_passengerService freecom_foreign_tourist_passengerService;

    @Inject
    private Freecom_partial_construction_servicesService freecom_partial_construction_servicesService;

    @Inject
    private Freecom_foreign_exchangeService freecom_foreign_exchangeService;

    @Inject
    private Freecom_local_taxesService freecom_local_taxesService;

    @Inject
    private Freecom_local_retentionsService freecom_local_retentionsService;

    @Inject
    private Freecom_local_transferedService freecom_local_transferedService;

    @Inject
    private Freecom_used_vehicleService freecom_used_vehicleService;

    @Inject
    private Freecom_vehicle_customs_informationService freecom_vehicle_customs_informationService;

    @Inject
    private Freecom_destruction_certificateService freecom_destruction_certificateService;

    @Inject
    private Freecom_info_customs_destructionService freecom_info_customs_destructionService;

    @Inject
    private Freecom_fuel_consumptionService freecom_fuel_consumptionService;

    @Inject
    private Freecom_concept_fuelService freecom_concept_fuelService;

    @Inject
    private Freecom_determinedService freecom_determinedService;

    @Inject
    private Freecom_storeroom_paybillService freecom_storeroom_paybillService;

    @Inject
    private Freecom_paybill_conceptService freecom_paybill_conceptService;

    @Inject
    private Freecom_ecc11Service freecom_ecc11Service;

    @Inject
    private Freecom_ecc11_conceptService freecom_ecc11_conceptService;

    @Inject
    private Freecom_ecc11_transferService freecom_ecc11_transferService;

    @Inject
    private Freecom_speiService freecom_speiService;

    @Inject
    private Freecom_payerService freecom_payerService;

    @Inject
    private Freecom_beneficiaryService freecom_beneficiaryService;

    @Inject
    private Freecom_spei_thirdService freecom_spei_thirdService;

    @Inject
    private Freecom_addresseeService freecom_addresseeService;

    @Inject
    private Freecom_foreign_tradeService freecom_foreign_tradeService;

    @Inject
    private Freecom_commodityService freecom_commodityService;

    @Inject
    private Freecom_specific_descriptionsService freecom_specific_descriptionsService;


    /**
     * POST  /free-cfdis : Create a new free_cfdi.
     *
     * @param freeCfdiDTO the free_cfdi_dto to create free_cfdi
     * @return the ResponseEntity with status 201 (Created) and with body the new free_cfdi, or with status 400 (Bad Request) if the free_cfdi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    //public ResponseEntity<Free_cfdi> createFree_cfdi(@Valid @RequestBody Free_cfdi free_cfdi) throws URISyntaxException {
    public ResponseEntity<Free_cfdi> createFree_cfdi(@RequestBody FreeCfdiDTO freeCfdiDTO) throws URISyntaxException {

        Free_cfdi free_cfdi = freeCfdiDTO.getFreeCFDI();

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

        Free_receiver free_receiver = freeCfdiDTO.getFreeReceiver();

        free_receiver.setCreate_date(ZonedDateTime.now());
        free_receiver.setActivated(true);
        free_receiver = free_receiverService.save(free_receiver);

        Long idauditevent = new Long("5");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);

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
        free_cfdi.setFree_receiver(free_receiver);

        freeCfdiDTO.setFreeCFDI(free_cfdi);
        Free_cfdi result = free_cfdiService.save(freeCfdiDTO);

        //Saving conceptDTOs
        List<Free_conceptDTO> free_conceptDTOs = freeCfdiDTO.getConceptDTOs();
        BigDecimal ceroValue = new BigDecimal("0");
        for(Free_conceptDTO free_conceptDTO: free_conceptDTOs){
            Free_concept free_concept = free_conceptDTO.getFree_concept();
            free_concept.setFree_cfdi(result);
            free_concept = free_conceptService.save(free_concept);

            //save tax trasnfered iva
            Free_tax_transfered free_concept_iva = free_conceptDTO.getFree_concept_iva();
            if(free_concept_iva.getAmount().compareTo(ceroValue) == 1){
                free_concept_iva.setFree_concept(free_concept);
                free_tax_transferedService.save(free_concept_iva);
            }

            //save tax trasnfered ieps
            Free_tax_transfered free_concept_ieps = free_conceptDTO.getFree_concept_ieps();
            if(free_concept_ieps.getAmount().compareTo(ceroValue) == 1){
                free_concept_ieps.setFree_concept(free_concept);
                free_tax_transferedService.save(free_concept_ieps);
            }

            //save tax retentions iva
            Free_tax_retentions free_tax_retentions_iva = free_conceptDTO.getFree_tax_retentions_iva();
            if(free_tax_retentions_iva != null && free_tax_retentions_iva.getAmount().compareTo(ceroValue) == 1){
                free_tax_retentions_iva.setFree_concept(free_concept);
                free_tax_retentionsService.save(free_tax_retentions_iva);
            }

            //save tax retentions isr
            Free_tax_retentions free_tax_retentions_isr = free_conceptDTO.getFree_tax_retentions_isr();
            if(free_tax_retentions_isr != null && free_tax_retentions_isr.getAmount().compareTo(ceroValue) == 1){
                free_tax_retentions_isr.setFree_concept(free_concept);
                free_tax_retentionsService.save(free_tax_retentions_isr);
            }

            //save customs infos
            List<Free_customs_info> free_customs_infos = free_conceptDTO.getFree_customs_infos();
            for(Free_customs_info free_customs_info: free_customs_infos){
                free_customs_info.setFree_concept(free_concept);
                free_customs_infoService.save(free_customs_info);
            }

            //save part concepts
            List<Free_part_concept> free_part_concepts = free_conceptDTO.getFree_part_concepts();
            for(Free_part_concept free_part_concept: free_part_concepts){
                free_part_concept.setFree_concept(free_concept);
                free_part_conceptService.save(free_part_concept);
            }
        }

        //SAVING COMPLEMENTS

        //taxregistration
        Freecom_taxregistration freecom_taxregistration = freeCfdiDTO.getFreecom_taxregistration();
        if(freecom_taxregistration != null && freecom_taxregistration.getVersion() != null) {
            freecom_taxregistration.setFree_cfdi(result);
            freecom_taxregistrationService.save(freecom_taxregistration);
        }

        //pfic
        Freecom_pfic freecom_pfic = freeCfdiDTO.getFreecom_pfic();
        if(freecom_pfic != null && freecom_pfic.getVersion() != null) {
            freecom_pfic.setFree_cfdi(result);
            freecom_pficService.save(freecom_pfic);
        }

        //accreditation_ieps
        Freecom_accreditation_ieps freecom_accreditation_ieps = freeCfdiDTO.getFreecom_accreditation_ieps();
        if(freecom_accreditation_ieps != null && freecom_accreditation_ieps.getVersion() != null) {
            freecom_accreditation_ieps.setFree_cfdi(result);
            freecom_accreditation_iepsService.save(freecom_accreditation_ieps);
        }

        //taxlegends
        Freecom_taxlegends freecom_taxlegends = freeCfdiDTO.getFreecom_taxlegends();
        if(freecom_taxlegends != null && freecom_taxlegends.getVersion() != null) {
            freecom_taxlegends.setFree_cfdi(result);
            freecom_taxlegends = freecom_taxlegendsService.save(freecom_taxlegends);

            List<Freecom_legend> freecom_legends = freeCfdiDTO.getFreecom_legends();
            for (Freecom_legend freecom_legend : freecom_legends) {
                freecom_legend.setFreecom_taxlegends(freecom_taxlegends);
                freecom_legendService.save(freecom_legend);
            }
        }

        //airline
        Freecom_airline freecom_airline = freeCfdiDTO.getFreecom_airline();
        if(freecom_airline != null && freecom_airline.getVersion() != null) {
            freecom_airline.setFree_cfdi(result);
            freecom_airline = freecom_airlineService.save(freecom_airline);

            List<Freecom_charge> freecom_charges = freeCfdiDTO.getFreecom_charges();
            for(Freecom_charge freecom_charge: freecom_charges){
                freecom_charge.setFreecom_airline(freecom_airline);
                freecom_chargeService.save(freecom_charge);
            }
        }

        //apaw
        Freecom_apaw freecom_apaw = freeCfdiDTO.getFreecom_apaw();
        if(freecom_apaw != null && freecom_apaw.getVersion() != null) {
            freecom_apaw.setFree_cfdi(result);
            freecom_apawService.save(freecom_apaw);
        }

        //donees
        Freecom_donees freecom_donees = freeCfdiDTO.getFreecom_donees();
        if(freecom_donees != null && freecom_donees.getVersion() != null) {
            freecom_donees.setFree_cfdi(result);
            freecom_doneesService.save(freecom_donees);
        }

        //educational_institutions
        Freecom_educational_institutions freecom_educational_institutions = freeCfdiDTO.getFreecom_educational_institutions();
        if(freecom_educational_institutions != null && freecom_educational_institutions.getVersion() != null) {
            freecom_educational_institutions.setFree_cfdi(result);
            freecom_educational_institutionsService.save(freecom_educational_institutions);
        }

        //ine
        Freecom_ine freecom_ine = freeCfdiDTO.getFreecom_ine();
        if(freecom_ine != null && freecom_ine.getVersion() != null) {
            freecom_ine.setFree_cfdi(result);
            freecom_ine = freecom_ineService.save(freecom_ine);
            List<Freecom_ine_entityDTO> freecom_ine_entities = freeCfdiDTO.getFreecom_ine_entities();
            for(Freecom_ine_entityDTO freecom_ine_entityDTO: freecom_ine_entities){
                Freecom_ine_entity freecom_ine_entity = freecom_ine_entityDTO.getFreecom_ine_entity();
                freecom_ine_entity.setFreecom_ine(freecom_ine);
                freecom_ine_entity = freecom_ine_entityService.save(freecom_ine_entity);

                List<Accounting> accountings = freecom_ine_entityDTO.getAccountings();
                for(Accounting accounting: accountings){
                    accounting.setFreecom_ine_entity(freecom_ine_entity);
                    accountingService.save(accounting);
                }
            }
        }

        //kind_payment
        Freecom_kind_payment freecom_kind_payment = freeCfdiDTO.getFreecom_kind_payment();
        if(freecom_kind_payment != null && freecom_kind_payment.getVersion() != null) {
            freecom_kind_payment.setFree_cfdi(result);
            freecom_kind_paymentService.save(freecom_kind_payment);
        }

        //foreign_tourist_passenger
        Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger = freeCfdiDTO.getFreecom_foreign_tourist_passenger();
        if(freecom_foreign_tourist_passenger != null && freecom_foreign_tourist_passenger.getVersion() != null) {
            freecom_foreign_tourist_passenger.setFree_cfdi(result);
            freecom_foreign_tourist_passengerService.save(freecom_foreign_tourist_passenger);
        }

        //partial_construction_services":
        Freecom_partial_construction_services freecom_partial_construction_services = freeCfdiDTO.getFreecom_partial_construction_services();
        if(freecom_partial_construction_services != null && freecom_partial_construction_services.getVersion() != null) {
            freecom_partial_construction_services.setFree_cfdi(result);
            freecom_partial_construction_servicesService.save(freecom_partial_construction_services);
        }

        //foreign_exchange
        Freecom_foreign_exchange freecom_foreign_exchange = freeCfdiDTO.getFreecom_foreign_exchange();
        if(freecom_foreign_exchange != null && freecom_foreign_exchange.getC_type_operation() != null) {
            freecom_foreign_exchange.setFree_cfdi(result);
            freecom_foreign_exchangeService.save(freecom_foreign_exchange);
        }

        //local_taxes
        Freecom_local_taxes freecom_local_taxes = freeCfdiDTO.getFreecom_local_taxes();
        if(freecom_local_taxes != null && freecom_local_taxes.getVersion() != null) {
            freecom_local_taxes.setFree_cfdi(result);
            freecom_local_taxes = freecom_local_taxesService.save(freecom_local_taxes);
            List<Freecom_ret_transfsDTO> freecom_ret_transfersDTOs = freeCfdiDTO.getFreecom_ret_transfs();
            for(Freecom_ret_transfsDTO freecom_ret_transfersDTO: freecom_ret_transfersDTOs){
                Freecom_local_retentions retentions = freecom_ret_transfersDTO.getRetentions();
                if(retentions != null){
                    retentions.setFreecom_local_taxes(freecom_local_taxes);
                    freecom_local_retentionsService.save(retentions);
                }

                Freecom_local_transfered transfered = freecom_ret_transfersDTO.getTransfered();
                if(transfered != null){
                    transfered.setFreecom_local_taxes(freecom_local_taxes);
                    freecom_local_transferedService.save(transfered);
                }
            }
        }

        //used_vehicle
        Freecom_used_vehicle freecom_used_vehicle = freeCfdiDTO.getFreecom_used_vehicle();
        if(freecom_used_vehicle != null && freecom_used_vehicle.getVersion() != null) {
            freecom_used_vehicle.setFree_cfdi(result);
            freecom_used_vehicle = freecom_used_vehicleService.save(freecom_used_vehicle);
            List<Freecom_vehicle_customs_information> vehicle_customs_informations = freeCfdiDTO.getVehicle_customs_informations();
            for(Freecom_vehicle_customs_information freecom_vehicle_customs_information: vehicle_customs_informations){
                freecom_vehicle_customs_information.setFreecom_used_vehicle(freecom_used_vehicle);
                freecom_vehicle_customs_informationService.save(freecom_vehicle_customs_information);
            }
        }

        //destruction_certificate
        Freecom_destruction_certificate freecom_destruction_certificate = freeCfdiDTO.getFreecom_destruction_certificate();
        if(freecom_destruction_certificate != null && freecom_destruction_certificate.getVersion() != null) {
            freecom_destruction_certificate.setFree_cfdi(result);
            freecom_destruction_certificate = freecom_destruction_certificateService.save(freecom_destruction_certificate);
            Freecom_info_customs_destruction freecom_info_customs_destruction = freeCfdiDTO.getFreecom_info_customs_destruction();
            if(freecom_info_customs_destruction != null){
                freecom_info_customs_destruction.setFreecom_destruction_certificate(freecom_destruction_certificate);
                freecom_info_customs_destructionService.save(freecom_info_customs_destruction);
            }
        }

        //fuel_consumption
        Freecom_fuel_consumption freecom_fuel_consumption = freeCfdiDTO.getFreecom_fuel_consumption();
        if(freecom_fuel_consumption != null && freecom_fuel_consumption.getVersion() != null) {
            freecom_fuel_consumption.setFree_cfdi(result);
            freecom_fuel_consumption = freecom_fuel_consumptionService.save(freecom_fuel_consumption);

            List<Freecom_concept_fuelDTO> freecom_concept_fuelDTOs = freeCfdiDTO.getFreecom_concept_fuels();
            for(Freecom_concept_fuelDTO freecom_concept_fuelDTO: freecom_concept_fuelDTOs){
                Freecom_concept_fuel freecom_concept_fuel = freecom_concept_fuelDTO.getConcept_fuel();
                freecom_concept_fuel.setFreecom_fuel_consumption(freecom_fuel_consumption);
                freecom_concept_fuel = freecom_concept_fuelService.save(freecom_concept_fuel);

                List<Freecom_determined> determinates = freecom_concept_fuelDTO.getDeterminates();
                for(Freecom_determined freecom_determined: determinates){
                    freecom_determined.setFreecom_concept_fuel(freecom_concept_fuel);
                    freecom_determinedService.save(freecom_determined);
                }
            }
        }

        //storeroom_paybill
        Freecom_storeroom_paybill freecom_storeroom_paybill = freeCfdiDTO.getFreecom_storeroom_paybill();
        if(freecom_storeroom_paybill != null && freecom_storeroom_paybill.getVersion() != null) {
            freecom_storeroom_paybill.setFree_cfdi(result);
            freecom_storeroom_paybill = freecom_storeroom_paybillService.save(freecom_storeroom_paybill);

            List<Freecom_paybill_concept> freecom_paybill_concepts = freeCfdiDTO.getFreecom_paybill_concepts();
            for(Freecom_paybill_concept freecom_paybill_concept: freecom_paybill_concepts) {
                freecom_paybill_concept.setFreecom_storeroom_paybill(freecom_storeroom_paybill);
                freecom_paybill_conceptService.save(freecom_paybill_concept);
            }
        }

        //ecc11
        Freecom_ecc11 freecom_ecc11 = freeCfdiDTO.getFreecom_ecc11();
        if(freecom_ecc11 != null && freecom_ecc11.getVersion() != null) {
            freecom_ecc11.setFree_cfdi(result);
            freecom_ecc11 = freecom_ecc11Service.save(freecom_ecc11);

            List<Freecom_ecc11_conceptDTO> freecom_ecc11_conceptDTOs = freeCfdiDTO.getFreecom_ecc11_concepts();
            for(Freecom_ecc11_conceptDTO freecom_ecc11_conceptDTO: freecom_ecc11_conceptDTOs){
                Freecom_ecc11_concept freecom_ecc11_concept = freecom_ecc11_conceptDTO.getConcept();
                freecom_ecc11_concept.setFreecom_ecc_11(freecom_ecc11);
                freecom_ecc11_concept = freecom_ecc11_conceptService.save(freecom_ecc11_concept);

                List<Freecom_ecc11_transfer> freecom_ecc11_transfers = freecom_ecc11_conceptDTO.getTransfers();
                for(Freecom_ecc11_transfer freecom_ecc11_transfer: freecom_ecc11_transfers){
                    freecom_ecc11_transfer.setFreecom_ecc_11_concept(freecom_ecc11_concept);
                    freecom_ecc11_transferService.save(freecom_ecc11_transfer);
                }
            }
        }

        //spei
        List<Freecom_spei_thirdDTO> freecom_spei_thirdDTOs = freeCfdiDTO.getFreecom_spei_thirds();
        if(freecom_spei_thirdDTOs != null && freecom_spei_thirdDTOs.size() > 0) {
            Freecom_spei freecom_spei = new Freecom_spei();
            freecom_spei.setFree_cfdi(result);
            freecom_spei = freecom_speiService.save(freecom_spei);

            for(Freecom_spei_thirdDTO freecom_spei_thirdDTO: freecom_spei_thirdDTOs){
                Freecom_payer freecom_payer = freecom_spei_thirdDTO.getPayer();
                freecom_payer = freecom_payerService.save(freecom_payer);

                Freecom_beneficiary freecom_beneficiary = freecom_spei_thirdDTO.getBeneficiary();
                freecom_beneficiary = freecom_beneficiaryService.save(freecom_beneficiary);

                Freecom_spei_third freecom_spei_third = freecom_spei_thirdDTO.getSpei_third();
                freecom_spei_third.setFreecom_spei(freecom_spei);
                freecom_spei_third.setFreecom_payer(freecom_payer);
                freecom_spei_third.setFreecom_beneficiary(freecom_beneficiary);
                freecom_spei_thirdService.save(freecom_spei_third);
            }
        }

        //foreign_trade
        Freecom_foreign_trade freecom_foreign_trade = freeCfdiDTO.getFreecom_foreign_trade();
        if(freecom_foreign_trade != null && freecom_foreign_trade.getVersion() != null) {
            Freecom_addressee freecom_addressee = freeCfdiDTO.getFreecom_addressee();
            freecom_addressee = freecom_addresseeService.save(freecom_addressee);

            freecom_foreign_trade.setFree_cfdi(result);
            freecom_foreign_trade.setFreecom_addressee(freecom_addressee);
            freecom_foreign_trade = freecom_foreign_tradeService.save(freecom_foreign_trade);

            List<Freecom_CommodityDTO> commodities = freeCfdiDTO.getCommodities();
            for(Freecom_CommodityDTO commodity: commodities){
                Freecom_commodity freecom_commodity = commodity.getFreecom_commodity();
                freecom_commodity.setFreecom_foreign_trade(freecom_foreign_trade);
                freecom_commodity = freecom_commodityService.save(freecom_commodity);

                List<Freecom_specific_descriptions> specific_descriptions = commodity.getSpecific_descriptions();
                for(Freecom_specific_descriptions freecom_specific_descriptions: specific_descriptions){
                    freecom_specific_descriptions.setFreecom_commodity(freecom_commodity);
                    freecom_specific_descriptionsService.save(freecom_specific_descriptions);
                }
            }
        }

        idauditevent = new Long("4");
        audit_event_type = audit_event_typeService.findOne(idauditevent);
        idstate = new Long("1");
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
     * @param freeCfdiDTO the free_cfdi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_cfdi,
     * or with status 400 (Bad Request) if the free_cfdi is not valid,
     * or with status 500 (Internal Server Error) if the free_cfdi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_cfdi> updateFree_cfdi(@Valid @RequestBody FreeCfdiDTO freeCfdiDTO) throws URISyntaxException {
        Free_cfdi free_cfdi = freeCfdiDTO.getFreeCFDI();
        log.debug("REST request to update Free_cfdi : {}", free_cfdi);
        log.debug("Actualizando estado de free_cfdi: " + free_cfdi.getCfdi_states().getName());

        if (free_cfdi.getId() == null) {
            return createFree_cfdi(freeCfdiDTO);
        }

        if(free_cfdi.getCfdi_states().getId() == 2){
            Free_cfdi pre = free_cfdiService.findOne(free_cfdi.getId());
            if(pre.getCfdi_states().getId() == 1){
                log.debug("Cancelando free_cfdi");
                free_cfdiService.cancelarFree_cfdi(free_cfdi);

                Long idauditevent = new Long("52");
                Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                C_state_event c_state_event;
                Long idstate = new Long("1");
                c_state_event = c_state_eventService.findOne(idstate);
                tracemgService.saveTrace(audit_event_type, c_state_event);
            }
        }
        Free_cfdi result = free_cfdiService.save(freeCfdiDTO);
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
                    log.debug("No se encuentra el free_emitter");
                    Long idauditevent = new Long("36");
                    Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
                    C_state_event c_state_event;
                    Long idstate = new Long("2");
                    c_state_event = c_state_eventService.findOne(idstate);
                    tracemgService.saveTrace(audit_event_type, c_state_event);

                    List<Free_cfdi> listtemp = new ArrayList<>();
                    Page<Free_cfdi> page = new PageImpl<Free_cfdi>(listtemp);
                    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-cfdis");

                    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
                }
            }
        }
        log.debug("No se encuentra el user autenticado");
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
    public ResponseEntity<byte[]> getZip(@RequestParam(value = "idFree_cfdi") Integer idFree_cfdi)
        throws URISyntaxException {
        log.debug("REST request to get a zip of Free_cfdis");
        if(idFree_cfdi != null)
        {
            //Free_cfdi temp = new Free_cfdi();
            byte [] zip = free_cfdiService.getZip(idFree_cfdi);
            //temp.setFilexml(zip);

            return ResponseEntity.ok().body(zip);
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
