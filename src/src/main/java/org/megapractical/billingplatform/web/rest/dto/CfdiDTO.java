package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.*;

import java.util.List;

public class CfdiDTO {

    private Taxpayer_client taxpayer_client;

    private Cfdi cfdi;

    private Taxpayer_series_folio taxpayer_series_folio;

    private List<ConceptDTO> conceptDTOs;

    private List<Concept> concepts;

    private List<Tax_transfered> taxTransfereds;

    private List<Tax_retentions> taxRetentions;


    //COMPLEMENTS...

    private Com_taxregistration com_taxregistration;

    private Com_pfic com_pfic;

    private Com_accreditation_ieps com_accreditation_ieps;


    private Com_taxlegends com_taxlegends;

    private List<Com_legends> com_legends;


    private Com_airline com_airline;

    private List<Com_charge> com_charges;


    private Com_apaw com_apaw;

    private Com_donees com_donees;

    private Com_educational_institutions com_educational_institutions;


    private Com_ine com_ine;

    private List<Com_ine_entityDTO> com_ine_entities;


    private Com_kind_payment com_kind_payment;

    private Com_foreign_tourist_passenger com_foreign_tourist_passenger;

    private Com_partial_construction_services com_partial_construction_services;

    private Com_foreign_exchange com_foreign_exchange;


    private Com_local_taxes com_local_taxes;

    private List<Com_ret_transfersDTO> com_ret_transfs;


    private Com_used_vehicle com_used_vehicle;

    private List<Com_vehicle_customs_information> vehicle_customs_informations;


    private Com_destruction_certificate com_destruction_certificate;

    private Com_info_customs_destruction com_info_customs_destruction;


    private Com_fuel_consumption com_fuel_consumption;

    private List<Com_concept_fuelDTO> com_concept_fuels;


    private Com_storeroom_paybill com_storeroom_paybill;

    private List<Com_paybill_concept> com_paybill_concepts;


    private Com_ecc_11 com_ecc_11;

    private List<Com_ecc_11_conceptDTO> com_ecc_11_concepts;


    private List<Com_spei_thirdDTO> com_spei_thirds;


    private Com_foreign_trade com_foreign_trade;

    private Com_addressee com_addressee;

    private List<Com_commodityDTO> commodities;


    private Com_public_notaries com_public_notaries;

    private List<Com_desc_estate> com_desc_estates;

    private Com_data_operation com_data_operation;

    private Com_notary_data com_notary_data;

    private Com_data_enajenante com_data_enajenante;

    private Com_dataunenajenante com_dataunenajenante;

    private List<Com_dataenajenantecopsc> com_dataenajenantecopscs;

    private Com_acquiring_data com_acquiring_data;

    private Com_dataunacquiring com_dataunacquiring;

    private List<Com_dataacquiringcopsc> com_dataacquiringcopscs;


    public Taxpayer_client getTaxpayer_client() {
        return taxpayer_client;
    }

    public void setTaxpayer_client(Taxpayer_client taxpayer_client) {
        this.taxpayer_client = taxpayer_client;
    }

    public Cfdi getCfdi() {
        return cfdi;
    }

    public void setCfdi(Cfdi cfdi) {
        this.cfdi = cfdi;
    }

    public List<ConceptDTO> getConceptDTOs() {
        return conceptDTOs;
    }

    public void setConceptDTOs(List<ConceptDTO> conceptDTOs) {
        this.conceptDTOs = conceptDTOs;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public List<Tax_transfered> getTaxTransfereds() {
        return taxTransfereds;
    }

    public void setTaxTransfereds(List<Tax_transfered> taxTransfereds) {
        this.taxTransfereds = taxTransfereds;
    }

    public List<Tax_retentions> getTaxRetentions() {
        return taxRetentions;
    }

    public void setTaxRetentions(List<Tax_retentions> taxRetentions) {
        this.taxRetentions = taxRetentions;
    }

    public Taxpayer_series_folio getTaxpayer_series_folio() {
        return taxpayer_series_folio;
    }

    public void setTaxpayer_series_folio(Taxpayer_series_folio taxpayer_series_folio) {
        this.taxpayer_series_folio = taxpayer_series_folio;
    }

    public Com_taxregistration getCom_taxregistration() {
        return com_taxregistration;
    }

    public void setCom_taxregistration(Com_taxregistration com_taxregistration) {
        this.com_taxregistration = com_taxregistration;
    }

    public Com_pfic getCom_pfic() {
        return com_pfic;
    }

    public void setCom_pfic(Com_pfic com_pfic) {
        this.com_pfic = com_pfic;
    }

    public Com_accreditation_ieps getCom_accreditation_ieps() {
        return com_accreditation_ieps;
    }

    public void setCom_accreditation_ieps(Com_accreditation_ieps com_accreditation_ieps) {
        this.com_accreditation_ieps = com_accreditation_ieps;
    }

    public Com_taxlegends getCom_taxlegends() {
        return com_taxlegends;
    }

    public void setCom_taxlegends(Com_taxlegends com_taxlegends) {
        this.com_taxlegends = com_taxlegends;
    }

    public List<Com_legends> getCom_legends() {
        return com_legends;
    }

    public void setCom_legends(List<Com_legends> com_legends) {
        this.com_legends = com_legends;
    }

    public Com_airline getCom_airline() {
        return com_airline;
    }

    public void setCom_airline(Com_airline com_airline) {
        this.com_airline = com_airline;
    }

    public List<Com_charge> getCom_charges() {
        return com_charges;
    }

    public void setCom_charges(List<Com_charge> com_charges) {
        this.com_charges = com_charges;
    }

    public Com_apaw getCom_apaw() {
        return com_apaw;
    }

    public void setCom_apaw(Com_apaw com_apaw) {
        this.com_apaw = com_apaw;
    }

    public Com_donees getCom_donees() {
        return com_donees;
    }

    public void setCom_donees(Com_donees com_donees) {
        this.com_donees = com_donees;
    }

    public Com_educational_institutions getCom_educational_institutions() {
        return com_educational_institutions;
    }

    public void setCom_educational_institutions(Com_educational_institutions com_educational_institutions) {
        this.com_educational_institutions = com_educational_institutions;
    }

    public Com_ine getCom_ine() {
        return com_ine;
    }

    public void setCom_ine(Com_ine com_ine) {
        this.com_ine = com_ine;
    }

    public List<Com_ine_entityDTO> getCom_ine_entities() {
        return com_ine_entities;
    }

    public void setCom_ine_entities(List<Com_ine_entityDTO> com_ine_entities) {
        this.com_ine_entities = com_ine_entities;
    }

    public Com_kind_payment getCom_kind_payment() {
        return com_kind_payment;
    }

    public void setCom_kind_payment(Com_kind_payment com_kind_payment) {
        this.com_kind_payment = com_kind_payment;
    }

    public Com_foreign_tourist_passenger getCom_foreign_tourist_passenger() {
        return com_foreign_tourist_passenger;
    }

    public void setCom_foreign_tourist_passenger(Com_foreign_tourist_passenger com_foreign_tourist_passenger) {
        this.com_foreign_tourist_passenger = com_foreign_tourist_passenger;
    }

    public Com_partial_construction_services getCom_partial_construction_services() {
        return com_partial_construction_services;
    }

    public void setCom_partial_construction_services(Com_partial_construction_services com_partial_construction_services) {
        this.com_partial_construction_services = com_partial_construction_services;
    }

    public Com_foreign_exchange getCom_foreign_exchange() {
        return com_foreign_exchange;
    }

    public void setCom_foreign_exchange(Com_foreign_exchange com_foreign_exchange) {
        this.com_foreign_exchange = com_foreign_exchange;
    }

    public Com_local_taxes getCom_local_taxes() {
        return com_local_taxes;
    }

    public void setCom_local_taxes(Com_local_taxes com_local_taxes) {
        this.com_local_taxes = com_local_taxes;
    }

    public List<Com_ret_transfersDTO> getCom_ret_transfs() {
        return com_ret_transfs;
    }

    public void setCom_ret_transfs(List<Com_ret_transfersDTO> com_ret_transfs) {
        this.com_ret_transfs = com_ret_transfs;
    }

    public Com_used_vehicle getCom_used_vehicle() {
        return com_used_vehicle;
    }

    public void setCom_used_vehicle(Com_used_vehicle com_used_vehicle) {
        this.com_used_vehicle = com_used_vehicle;
    }

    public List<Com_vehicle_customs_information> getVehicle_customs_informations() {
        return vehicle_customs_informations;
    }

    public void setVehicle_customs_informations(List<Com_vehicle_customs_information> vehicle_customs_informations) {
        this.vehicle_customs_informations = vehicle_customs_informations;
    }

    public Com_destruction_certificate getCom_destruction_certificate() {
        return com_destruction_certificate;
    }

    public void setCom_destruction_certificate(Com_destruction_certificate com_destruction_certificate) {
        this.com_destruction_certificate = com_destruction_certificate;
    }

    public Com_info_customs_destruction getCom_info_customs_destruction() {
        return com_info_customs_destruction;
    }

    public void setCom_info_customs_destruction(Com_info_customs_destruction com_info_customs_destruction) {
        this.com_info_customs_destruction = com_info_customs_destruction;
    }

    public Com_fuel_consumption getCom_fuel_consumption() {
        return com_fuel_consumption;
    }

    public void setCom_fuel_consumption(Com_fuel_consumption com_fuel_consumption) {
        this.com_fuel_consumption = com_fuel_consumption;
    }

    public List<Com_concept_fuelDTO> getCom_concept_fuels() {
        return com_concept_fuels;
    }

    public void setCom_concept_fuels(List<Com_concept_fuelDTO> com_concept_fuels) {
        this.com_concept_fuels = com_concept_fuels;
    }

    public Com_storeroom_paybill getCom_storeroom_paybill() {
        return com_storeroom_paybill;
    }

    public void setCom_storeroom_paybill(Com_storeroom_paybill com_storeroom_paybill) {
        this.com_storeroom_paybill = com_storeroom_paybill;
    }

    public List<Com_paybill_concept> getCom_paybill_concepts() {
        return com_paybill_concepts;
    }

    public void setCom_paybill_concepts(List<Com_paybill_concept> com_paybill_concepts) {
        this.com_paybill_concepts = com_paybill_concepts;
    }

    public Com_ecc_11 getCom_ecc_11() {
        return com_ecc_11;
    }

    public void setCom_ecc_11(Com_ecc_11 com_ecc_11) {
        this.com_ecc_11 = com_ecc_11;
    }

    public List<Com_ecc_11_conceptDTO> getCom_ecc_11_concepts() {
        return com_ecc_11_concepts;
    }

    public void setCom_ecc_11_concepts(List<Com_ecc_11_conceptDTO> com_ecc_11_concepts) {
        this.com_ecc_11_concepts = com_ecc_11_concepts;
    }

    public List<Com_spei_thirdDTO> getCom_spei_thirds() {
        return com_spei_thirds;
    }

    public void setCom_spei_thirds(List<Com_spei_thirdDTO> com_spei_thirds) {
        this.com_spei_thirds = com_spei_thirds;
    }

    public Com_foreign_trade getCom_foreign_trade() {
        return com_foreign_trade;
    }

    public void setCom_foreign_trade(Com_foreign_trade com_foreign_trade) {
        this.com_foreign_trade = com_foreign_trade;
    }

    public Com_addressee getCom_addressee() {
        return com_addressee;
    }

    public void setCom_addressee(Com_addressee com_addressee) {
        this.com_addressee = com_addressee;
    }

    public List<Com_commodityDTO> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<Com_commodityDTO> commodities) {
        this.commodities = commodities;
    }

    public Com_public_notaries getCom_public_notaries() {
        return com_public_notaries;
    }

    public void setCom_public_notaries(Com_public_notaries com_public_notaries) {
        this.com_public_notaries = com_public_notaries;
    }

    public List<Com_desc_estate> getCom_desc_estates() {
        return com_desc_estates;
    }

    public void setCom_desc_estates(List<Com_desc_estate> com_desc_estates) {
        this.com_desc_estates = com_desc_estates;
    }

    public Com_data_operation getCom_data_operation() {
        return com_data_operation;
    }

    public void setCom_data_operation(Com_data_operation com_data_operation) {
        this.com_data_operation = com_data_operation;
    }

    public Com_notary_data getCom_notary_data() {
        return com_notary_data;
    }

    public void setCom_notary_data(Com_notary_data com_notary_data) {
        this.com_notary_data = com_notary_data;
    }

    public Com_data_enajenante getCom_data_enajenante() {
        return com_data_enajenante;
    }

    public void setCom_data_enajenante(Com_data_enajenante com_data_enajenante) {
        this.com_data_enajenante = com_data_enajenante;
    }

    public Com_dataunenajenante getCom_dataunenajenante() {
        return com_dataunenajenante;
    }

    public void setCom_dataunenajenante(Com_dataunenajenante com_dataunenajenante) {
        this.com_dataunenajenante = com_dataunenajenante;
    }

    public List<Com_dataenajenantecopsc> getCom_dataenajenantecopscs() {
        return com_dataenajenantecopscs;
    }

    public void setCom_dataenajenantecopscs(List<Com_dataenajenantecopsc> com_dataenajenantecopscs) {
        this.com_dataenajenantecopscs = com_dataenajenantecopscs;
    }

    public Com_acquiring_data getCom_acquiring_data() {
        return com_acquiring_data;
    }

    public void setCom_acquiring_data(Com_acquiring_data com_acquiring_data) {
        this.com_acquiring_data = com_acquiring_data;
    }

    public Com_dataunacquiring getCom_dataunacquiring() {
        return com_dataunacquiring;
    }

    public void setCom_dataunacquiring(Com_dataunacquiring com_dataunacquiring) {
        this.com_dataunacquiring = com_dataunacquiring;
    }

    public List<Com_dataacquiringcopsc> getCom_dataacquiringcopscs() {
        return com_dataacquiringcopscs;
    }

    public void setCom_dataacquiringcopscs(List<Com_dataacquiringcopsc> com_dataacquiringcopscs) {
        this.com_dataacquiringcopscs = com_dataacquiringcopscs;
    }
}
