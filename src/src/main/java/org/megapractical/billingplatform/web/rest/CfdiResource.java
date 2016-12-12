package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.security.SecurityUtils;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.web.rest.dto.*;
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
    private Client_addressService client_addressService;

    @Inject
    private ClientService clientService;

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

    @Inject
    private Com_airlineService com_airlineService;

    @Inject
    private Com_chargeService com_chargeService;

    @Inject
    private Com_apawService com_apawService;

    @Inject
    private Com_doneesService com_doneesService;

    @Inject
    private Com_educational_institutionsService com_educational_institutionsService;

    @Inject
    private Com_ineService com_ineService;

    @Inject
    private Com_ine_entityService com_ine_entityService;

    @Inject
    private Com_accountingService com_accountingService;

    @Inject
    private Com_kind_paymentService com_kind_paymentService;

    @Inject
    private Com_foreign_tourist_passengerService com_foreign_tourist_passengerService;

    @Inject
    private Com_partial_construction_servicesService com_partial_construction_servicesService;

    @Inject
    private Com_foreign_exchangeService com_foreign_exchangeService;

    @Inject
    private Com_local_taxesService com_local_taxesService;

    @Inject
    private Com_local_retentionsService com_local_retentionsService;

    @Inject
    private Com_local_transferedService com_local_transferedService;

    @Inject
    private Com_used_vehicleService com_used_vehicleService;

    @Inject
    private Com_vehicle_customs_informationService com_vehicle_customs_informationService;

    @Inject
    private Com_destruction_certificateService com_destruction_certificateService;

    @Inject
    private Com_info_customs_destructionService com_info_customs_destructionService;

    @Inject
    private Com_fuel_consumptionService com_fuel_consumptionService;

    @Inject
    private Com_concept_fuelService com_concept_fuelService;

    @Inject
    private Com_determinedService com_determinedService;

    @Inject
    private Com_storeroom_paybillService com_storeroom_paybillService;

    @Inject
    private Com_paybill_conceptService com_paybill_conceptService;

    @Inject
    private Com_ecc_11Service com_ecc_11Service;

    @Inject
    private Com_ecc_11_conceptService com_ecc_11_conceptService;

    @Inject
    private Com_ecc_11_transferService com_ecc_11_transferService;

    @Inject
    private Com_speiService com_speiService;

    @Inject
    private Com_payerService com_payerService;

    @Inject
    private Com_beneficiaryService com_beneficiaryService;

    @Inject
    private Com_spei_thirdService com_spei_thirdService;

    @Inject
    private Com_addresseeService com_addresseeService;

    @Inject
    private Com_foreign_tradeService com_foreign_tradeService;

    @Inject
    private Com_commodityService com_commodityService;

    @Inject
    private Com_specific_descriptionsService com_specific_descriptionsService;

    @Inject
    private Com_public_notariesService com_public_notariesService;

    @Inject
    private Com_desc_estateService com_desc_estateService;

    @Inject
    private Com_data_operationService com_data_operationService;

    @Inject
    private Com_notary_dataService com_notary_dataService;

    @Inject
    private Com_data_enajenanteService com_data_enajenanteService;

    @Inject
    private Com_dataunenajenanteService com_dataunenajenanteService;

    @Inject
    private Com_dataenajenantecopscService com_dataenajenantecopscService;

    @Inject
    private Com_acquiring_dataService com_acquiring_dataService;

    @Inject
    private Com_dataunacquiringService com_dataunacquiringService;

    @Inject
    private Com_dataacquiringcopscService com_dataacquiringcopscService;

    @Inject
    private MailService mailService;

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

        Taxpayer_client taxpayer_client = cfdiDTO.getTaxpayer_client();

        Client client = new Client();
        client.setRfc(taxpayer_client.getRfc());
        client.setBussinesname(taxpayer_client.getBussinesname());
        client.setEmail(taxpayer_client.getEmail());
        client.setEmail2(taxpayer_client.getEmail2());

        Client_address taxpayer_client_address = taxpayer_client.getClient_address();
        Client_address client_address = new Client_address();
        client_address.setStreet(taxpayer_client_address.getStreet());
        client_address.setNo_int(taxpayer_client_address.getNo_int());
        client_address.setNo_ext(taxpayer_client_address.getNo_ext());
        client_address.setLocation(taxpayer_client_address.getLocation());
        client_address.setIntersection(taxpayer_client_address.getIntersection());
        client_address.setReference(taxpayer_client_address.getReference());
        client_address.setC_country(taxpayer_client_address.getC_country());
        client_address.setC_state(taxpayer_client_address.getC_state());
        client_address.setC_municipality(taxpayer_client_address.getC_municipality());
        client_address.setC_colony(taxpayer_client_address.getC_colony());
        client_address.setC_zip_code(taxpayer_client_address.getC_zip_code());
        client_address = client_addressService.save(client_address);

        client.setClient_address(client_address);
        client = clientService.save(client);

        cfdi.setClient(client);

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
        if(taxpayer_series_folio != null) {
            Integer folio_current = 0;
            if (taxpayer_series_folio.getFolio_current() == null) {
                folio_current = taxpayer_series_folio.getFolio_start();
            } else {
                folio_current = taxpayer_series_folio.getFolio_current() + 1;
            }

            if (folio_current == taxpayer_series_folio.getFolio_end()) {
                taxpayer_series_folio.setEnable(false);
                taxpayer_series_folio.setFolio_current(folio_current);
                taxpayer_series_folioService.save(taxpayer_series_folio);

                Page<Taxpayer_series_folio> taxpayer_series_folio_page = taxpayer_series_folioService.findAll(pageable, taxpayer_accout_id);
                for (Taxpayer_series_folio taxpayer_series_folio_item : taxpayer_series_folio_page.getContent()) {
                    if (taxpayer_series_folio_item.getFolio_current() == null || taxpayer_series_folio_item.getFolio_current() < taxpayer_series_folio_item.getFolio_end()) {
                        taxpayer_series_folio_item.setEnable(true);
                        taxpayer_series_folioService.save(taxpayer_series_folio_item);
                        break;
                    }
                }
            } else {
                taxpayer_series_folio.setFolio_current(folio_current);
                taxpayer_series_folioService.save(taxpayer_series_folio);
            }
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

        //airline
        Com_airline com_airline = cfdiDTO.getCom_airline();
        if(com_airline != null && com_airline.getVersion() != null) {
            com_airline.setCfdi(result);
            com_airline = com_airlineService.save(com_airline);

            List<Com_charge> com_charges = cfdiDTO.getCom_charges();
            for(Com_charge com_charge: com_charges){
                com_charge.setCom_airline(com_airline);
                com_chargeService.save(com_charge);
            }
        }

        //apaw
        Com_apaw com_apaw = cfdiDTO.getCom_apaw();
        if(com_apaw != null && com_apaw.getVersion() != null) {
            com_apaw.setCfdi(result);
            com_apawService.save(com_apaw);
        }

        //donees
        Com_donees com_donees = cfdiDTO.getCom_donees();
        if(com_donees != null && com_donees.getVersion() != null) {
            com_donees.setCfdi(result);
            com_doneesService.save(com_donees);
        }

        //educational_institutions
        Com_educational_institutions com_educational_institutions = cfdiDTO.getCom_educational_institutions();
        if(com_educational_institutions != null && com_educational_institutions.getVersion() != null) {
            com_educational_institutions.setCfdi(result);
            com_educational_institutionsService.save(com_educational_institutions);
        }

        //ine
        Com_ine com_ine = cfdiDTO.getCom_ine();
        if(com_ine != null && com_ine.getVersion() != null) {
            com_ine.setCfdi(result);
            com_ine = com_ineService.save(com_ine);
            List<Com_ine_entityDTO> com_ine_entities = cfdiDTO.getCom_ine_entities();
            for(Com_ine_entityDTO com_ine_entityDTO: com_ine_entities){
                Com_ine_entity com_ine_entity = com_ine_entityDTO.getCom_ine_entity();
                com_ine_entity.setCom_ine(com_ine);
                com_ine_entity = com_ine_entityService.save(com_ine_entity);

                List<Com_accounting> com_accountings = com_ine_entityDTO.getAccountings();
                for(Com_accounting com_accounting: com_accountings){
                    com_accounting.setCom_ine_entity(com_ine_entity);
                    com_accountingService.save(com_accounting);
                }
            }
        }

        //kind_payment
        Com_kind_payment com_kind_payment =cfdiDTO.getCom_kind_payment();
        if(com_kind_payment != null && com_kind_payment.getVersion() != null) {
            com_kind_payment.setCfdi(result);
            com_kind_paymentService.save(com_kind_payment);
        }

        //foreign_tourist_passenger
        Com_foreign_tourist_passenger com_foreign_tourist_passenger = cfdiDTO.getCom_foreign_tourist_passenger();
        if(com_foreign_tourist_passenger != null && com_foreign_tourist_passenger.getVersion() != null) {
            com_foreign_tourist_passenger.setCfdi(result);
            com_foreign_tourist_passengerService.save(com_foreign_tourist_passenger);
        }
        //partial_construction_services":
        Com_partial_construction_services com_partial_construction_services = cfdiDTO.getCom_partial_construction_services();
        if(com_partial_construction_services != null && com_partial_construction_services.getVersion() != null) {
            com_partial_construction_services.setCfdi(result);
            com_partial_construction_servicesService.save(com_partial_construction_services);
        }

        //foreign_exchange
        Com_foreign_exchange com_foreign_exchange = cfdiDTO.getCom_foreign_exchange();
        if(com_foreign_exchange != null && com_foreign_exchange.getC_type_operation() != null) {
            com_foreign_exchange.setCfdi(result);
            com_foreign_exchangeService.save(com_foreign_exchange);
        }

        //local_taxes
        Com_local_taxes com_local_taxes = cfdiDTO.getCom_local_taxes();
        if(com_local_taxes != null && com_local_taxes.getVersion() != null) {
            com_local_taxes.setCfdi(result);
            com_local_taxes = com_local_taxesService.save(com_local_taxes);
            List<Com_ret_transfersDTO> com_ret_transfersDTOs = cfdiDTO.getCom_ret_transfs();
            for(Com_ret_transfersDTO com_ret_transfersDTO: com_ret_transfersDTOs){
                Com_local_retentions retentions = com_ret_transfersDTO.getRetentions();
                if(retentions != null){
                    retentions.setCom_local_taxes(com_local_taxes);
                    com_local_retentionsService.save(retentions);
                }

                Com_local_transfered transfered = com_ret_transfersDTO.getTransfered();
                if(transfered != null){
                    transfered.setCom_local_taxes(com_local_taxes);
                    com_local_transferedService.save(transfered);
                }
            }
        }

        //used_vehicle
        Com_used_vehicle com_used_vehicle = cfdiDTO.getCom_used_vehicle();
        if(com_used_vehicle != null && com_used_vehicle.getVersion() != null) {
            com_used_vehicle.setCfdi(result);
            com_used_vehicle = com_used_vehicleService.save(com_used_vehicle);
            List<Com_vehicle_customs_information> vehicle_customs_informations = cfdiDTO.getVehicle_customs_informations();
            for(Com_vehicle_customs_information com_vehicle_customs_information: vehicle_customs_informations){
                com_vehicle_customs_information.setCom_used_vehicle(com_used_vehicle);
                com_vehicle_customs_informationService.save(com_vehicle_customs_information);
            }
        }

        //destruction_certificate
        Com_destruction_certificate com_destruction_certificate = cfdiDTO.getCom_destruction_certificate();
        if(com_destruction_certificate != null && com_destruction_certificate.getVersion() != null) {
            com_destruction_certificate.setCfdi(result);
            com_destruction_certificate = com_destruction_certificateService.save(com_destruction_certificate);
            Com_info_customs_destruction com_info_customs_destruction = cfdiDTO.getCom_info_customs_destruction();
            if(com_info_customs_destruction != null){
                com_info_customs_destruction.setCom_destruction_certificate(com_destruction_certificate);
                com_info_customs_destructionService.save(com_info_customs_destruction);
            }
        }

        //fuel_consumption
        Com_fuel_consumption com_fuel_consumption = cfdiDTO.getCom_fuel_consumption();
        if(com_fuel_consumption != null && com_fuel_consumption.getVersion() != null) {
            com_fuel_consumption.setCfdi(result);
            com_fuel_consumption = com_fuel_consumptionService.save(com_fuel_consumption);

            List<Com_concept_fuelDTO> com_concept_fuelDTOs = cfdiDTO.getCom_concept_fuels();
            for(Com_concept_fuelDTO com_concept_fuelDTO: com_concept_fuelDTOs){
                Com_concept_fuel com_concept_fuel = com_concept_fuelDTO.getConcept_fuel();
                com_concept_fuel.setCom_fuel_consumption(com_fuel_consumption);
                com_concept_fuel = com_concept_fuelService.save(com_concept_fuel);

                List<Com_determined> determinates = com_concept_fuelDTO.getDeterminates();
                for(Com_determined com_determined: determinates){
                    com_determined.setCom_concept_fuel(com_concept_fuel);
                    com_determinedService.save(com_determined);
                }
            }
        }

        //storeroom_paybill
        Com_storeroom_paybill com_storeroom_paybill = cfdiDTO.getCom_storeroom_paybill();
        if(com_storeroom_paybill != null && com_storeroom_paybill.getVersion() != null) {
            com_storeroom_paybill.setCfdi(result);
            com_storeroom_paybill = com_storeroom_paybillService.save(com_storeroom_paybill);

            List<Com_paybill_concept> com_paybill_concepts = cfdiDTO.getCom_paybill_concepts();
            for(Com_paybill_concept com_paybill_concept: com_paybill_concepts) {
                com_paybill_concept.setCom_storeroom_paybill(com_storeroom_paybill);
                com_paybill_conceptService.save(com_paybill_concept);
            }
        }

        //ecc11
        Com_ecc_11 com_ecc_11 = cfdiDTO.getCom_ecc_11();
        if(com_ecc_11 != null && com_ecc_11.getVersion() != null) {
            com_ecc_11.setCfdi(result);
            com_ecc_11 = com_ecc_11Service.save(com_ecc_11);

            List<Com_ecc_11_conceptDTO> com_ecc_11_conceptDTOs = cfdiDTO.getCom_ecc_11_concepts();
            for(Com_ecc_11_conceptDTO com_ecc_11_conceptDTO: com_ecc_11_conceptDTOs){
                Com_ecc_11_concept com_ecc_11_concept = com_ecc_11_conceptDTO.getConcept();
                com_ecc_11_concept.setCom_ecc_11(com_ecc_11);
                com_ecc_11_concept = com_ecc_11_conceptService.save(com_ecc_11_concept);

                List<Com_ecc_11_transfer> com_ecc_11_transfers = com_ecc_11_conceptDTO.getTransfers();
                for(Com_ecc_11_transfer com_ecc_11_transfer: com_ecc_11_transfers){
                    com_ecc_11_transfer.setCom_ecc_11_concept(com_ecc_11_concept);
                    com_ecc_11_transferService.save(com_ecc_11_transfer);
                }
            }
        }

        //spei
        List<Com_spei_thirdDTO> com_spei_thirdDTOs = cfdiDTO.getCom_spei_thirds();
        if(com_spei_thirdDTOs != null && com_spei_thirdDTOs.size() > 0) {
            Com_spei com_spei = new Com_spei();
            com_spei.setCfdi(result);
            com_spei = com_speiService.save(com_spei);

            for(Com_spei_thirdDTO com_spei_thirdDTO: com_spei_thirdDTOs){
                Com_payer com_payer = com_spei_thirdDTO.getPayer();
                com_payer = com_payerService.save(com_payer);

                Com_beneficiary com_beneficiary = com_spei_thirdDTO.getBeneficiary();
                com_beneficiary = com_beneficiaryService.save(com_beneficiary);

                Com_spei_third com_spei_third = com_spei_thirdDTO.getSpei_third();
                com_spei_third.setCom_spei(com_spei);
                com_spei_third.setCom_payer(com_payer);
                com_spei_third.setCom_beneficiary(com_beneficiary);
                com_spei_thirdService.save(com_spei_third);
            }
        }

        //foreign_trade
        Com_foreign_trade com_foreign_trade = cfdiDTO.getCom_foreign_trade();
        if(com_foreign_trade != null && com_foreign_trade.getVersion() != null) {
            Com_addressee com_addressee = cfdiDTO.getCom_addressee();
            com_addressee = com_addresseeService.save(com_addressee);

            com_foreign_trade.setCfdi(result);
            com_foreign_trade.setCom_addressee(com_addressee);
            com_foreign_trade = com_foreign_tradeService.save(com_foreign_trade);

            List<Com_commodityDTO> commodities = cfdiDTO.getCommodities();
            for(Com_commodityDTO commodity: commodities){
                Com_commodity com_commodity = commodity.getCom_commodity();
                com_commodity.setCom_foreign_trade(com_foreign_trade);
                com_commodity = com_commodityService.save(com_commodity);

                List<Com_specific_descriptions> specific_descriptions = commodity.getSpecific_descriptions();
                for(Com_specific_descriptions com_specific_descriptions: specific_descriptions){
                    com_specific_descriptions.setCom_commodity(com_commodity);
                    com_specific_descriptionsService.save(com_specific_descriptions);
                }
            }
        }

        //public_notaries
        Com_public_notaries com_public_notaries = cfdiDTO.getCom_public_notaries();
        if(com_public_notaries != null && com_public_notaries.getVersion() != null){
            com_public_notaries.setCfdi(result);
            com_public_notaries = com_public_notariesService.save(com_public_notaries);

            List<Com_desc_estate> desc_estates = cfdiDTO.getCom_desc_estates();
            for(Com_desc_estate com_desc_estate: desc_estates){
                com_desc_estate.setCom_public_notaries(com_public_notaries);
                com_desc_estateService.save(com_desc_estate);
            }

            Com_data_operation com_data_operation = cfdiDTO.getCom_data_operation();
            com_data_operation.setCom_public_notaries(com_public_notaries);
            com_data_operationService.save(com_data_operation);

            Com_notary_data com_notary_data = cfdiDTO.getCom_notary_data();
            com_notary_data.setCom_public_notaries(com_public_notaries);
            com_notary_dataService.save(com_notary_data);

            Com_data_enajenante com_data_enajenante = cfdiDTO.getCom_data_enajenante();
            com_data_enajenante.setCom_public_notaries(com_public_notaries);
            com_data_enajenante = com_data_enajenanteService.save(com_data_enajenante);

            Com_dataunenajenante com_dataunenajenante = cfdiDTO.getCom_dataunenajenante();
            com_dataunenajenante.setCom_data_enajenante(com_data_enajenante);
            com_dataunenajenanteService.save(com_dataunenajenante);

            List<Com_dataenajenantecopsc> com_dataenajenantecopscs = cfdiDTO.getCom_dataenajenantecopscs();
            for(Com_dataenajenantecopsc com_dataenajenantecopsc: com_dataenajenantecopscs){
                com_dataenajenantecopsc.setCom_data_enajenante(com_data_enajenante);
                com_dataenajenantecopscService.save(com_dataenajenantecopsc);
            }

            Com_acquiring_data com_acquiring_data = cfdiDTO.getCom_acquiring_data();
            com_acquiring_data.setCom_public_notaries(com_public_notaries);
            com_acquiring_data = com_acquiring_dataService.save(com_acquiring_data);

            Com_dataunacquiring com_dataunacquiring = cfdiDTO.getCom_dataunacquiring();
            com_dataunacquiring.setCom_acquiring_data(com_acquiring_data);
            com_dataunacquiringService.save(com_dataunacquiring);

            List<Com_dataacquiringcopsc> com_dataacquiringcopscs = cfdiDTO.getCom_dataacquiringcopscs();
            for(Com_dataacquiringcopsc com_dataacquiringcopsc: com_dataacquiringcopscs){
                com_dataacquiringcopsc.setCom_acquiring_data(com_acquiring_data);
                com_dataacquiringcopscService.save(com_dataacquiringcopsc);
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
