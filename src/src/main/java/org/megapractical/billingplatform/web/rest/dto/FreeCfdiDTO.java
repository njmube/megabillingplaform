package org.megapractical.billingplatform.web.rest.dto;

import org.megapractical.billingplatform.domain.*;

import java.util.List;

public class FreeCfdiDTO {

    private Free_receiver  freeReceiver;

    private Free_cfdi freeCFDI;

    private List<Free_conceptDTO> conceptDTOs;

    private List<Free_concept> concepts;

    private List<Free_tax_transfered> freeTaxTransfereds;

    private List<Free_tax_retentions> freeTaxRetentions;

    //COMPLEMENTS...

    private Freecom_taxregistration freecom_taxregistration;

    private Freecom_pfic freecom_pfic;

    private Freecom_accreditation_ieps freecom_accreditation_ieps;


    private Freecom_taxlegends freecom_taxlegends;

    private  List<Freecom_legend> freecom_legends;


    private Freecom_airline freecom_airline;

    private List<Freecom_charge> freecom_charges;


    private Freecom_apaw freecom_apaw;

    private Freecom_donees freecom_donees;

    private Freecom_educational_institutions freecom_educational_institutions;


    private Freecom_ine freecom_ine;

    private List<Freecom_ine_entityDTO> freecom_ine_entities;


    private Freecom_kind_payment freecom_kind_payment;

    private Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger;

    private Freecom_partial_construction_services freecom_partial_construction_services;

    private Freecom_foreign_exchange freecom_foreign_exchange;


    private Freecom_local_taxes freecom_local_taxes;

    private List<Freecom_ret_transfsDTO> freecom_ret_transfs;


    private Freecom_used_vehicle freecom_used_vehicle;

    private List<Freecom_vehicle_customs_information> vehicle_customs_informations;


    private Freecom_destruction_certificate freecom_destruction_certificate;

    private Freecom_info_customs_destruction freecom_info_customs_destruction;


    private Freecom_fuel_consumption freecom_fuel_consumption;

    private List<Freecom_concept_fuelDTO> freecom_concept_fuels;


    private Freecom_storeroom_paybill freecom_storeroom_paybill;

    private List<Freecom_paybill_concept> freecom_paybill_concepts;


    private Freecom_ecc11 freecom_ecc11;

    private List<Freecom_ecc11_conceptDTO> freecom_ecc11_concepts;


    private List<Freecom_spei_thirdDTO> freecom_spei_thirds;


    private Freecom_foreign_trade freecom_foreign_trade;

    private Freecom_addressee freecom_addressee;

    private List<Freecom_CommodityDTO> commodities;


    public Free_receiver getFreeReceiver() {
        return freeReceiver;
    }

    public void setFreeReceiver(Free_receiver freeReceiver) {
        this.freeReceiver = freeReceiver;
    }

    public Free_cfdi getFreeCFDI() {
        return freeCFDI;
    }

    public void setFreeCFDI(Free_cfdi freeCFDI) {
        this.freeCFDI = freeCFDI;
    }

    public List<Free_conceptDTO> getConceptDTOs() {
        return conceptDTOs;
    }

    public void setConceptDTOs(List<Free_conceptDTO> conceptDTOs) {
        this.conceptDTOs = conceptDTOs;
    }

    public List<Free_concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Free_concept> concepts) {
        this.concepts = concepts;
    }

    public List<Free_tax_transfered> getFreeTaxTransfereds() {
        return freeTaxTransfereds;
    }

    public void setFreeTaxTransfereds(List<Free_tax_transfered> freeTaxTransfereds) {
        this.freeTaxTransfereds = freeTaxTransfereds;
    }

    public List<Free_tax_retentions> getFreeTaxRetentions() {
        return freeTaxRetentions;
    }

    public void setFreeTaxRetentions(List<Free_tax_retentions> freeTaxRetentions) {
        this.freeTaxRetentions = freeTaxRetentions;
    }

    public Freecom_taxregistration getFreecom_taxregistration() {
        return freecom_taxregistration;
    }

    public void setFreecom_taxregistration(Freecom_taxregistration freecom_taxregistration) {
        this.freecom_taxregistration = freecom_taxregistration;
    }

    public Freecom_pfic getFreecom_pfic() {
        return freecom_pfic;
    }

    public void setFreecom_pfic(Freecom_pfic freecom_pfic) {
        this.freecom_pfic = freecom_pfic;
    }

    public Freecom_accreditation_ieps getFreecom_accreditation_ieps() {
        return freecom_accreditation_ieps;
    }

    public void setFreecom_accreditation_ieps(Freecom_accreditation_ieps freecom_accreditation_ieps) {
        this.freecom_accreditation_ieps = freecom_accreditation_ieps;
    }

    public Freecom_taxlegends getFreecom_taxlegends() {
        return freecom_taxlegends;
    }

    public void setFreecom_taxlegends(Freecom_taxlegends freecom_taxlegends) {
        this.freecom_taxlegends = freecom_taxlegends;
    }

    public List<Freecom_legend> getFreecom_legends() {
        return freecom_legends;
    }

    public void setFreecom_legends(List<Freecom_legend> freecom_legends) {
        this.freecom_legends = freecom_legends;
    }

    public Freecom_airline getFreecom_airline() {
        return freecom_airline;
    }

    public void setFreecom_airline(Freecom_airline freecom_airline) {
        this.freecom_airline = freecom_airline;
    }

    public List<Freecom_charge> getFreecom_charges() {
        return freecom_charges;
    }

    public void setFreecom_charges(List<Freecom_charge> freecom_charges) {
        this.freecom_charges = freecom_charges;
    }

    public Freecom_apaw getFreecom_apaw() {
        return freecom_apaw;
    }

    public void setFreecom_apaw(Freecom_apaw freecom_apaw) {
        this.freecom_apaw = freecom_apaw;
    }

    public Freecom_donees getFreecom_donees() {
        return freecom_donees;
    }

    public void setFreecom_donees(Freecom_donees freecom_donees) {
        this.freecom_donees = freecom_donees;
    }

    public Freecom_educational_institutions getFreecom_educational_institutions() {
        return freecom_educational_institutions;
    }

    public void setFreecom_educational_institutions(Freecom_educational_institutions freecom_educational_institutions) {
        this.freecom_educational_institutions = freecom_educational_institutions;
    }

    public Freecom_ine getFreecom_ine() {
        return freecom_ine;
    }

    public void setFreecom_ine(Freecom_ine freecom_ine) {
        this.freecom_ine = freecom_ine;
    }

    public List<Freecom_ine_entityDTO> getFreecom_ine_entities() {
        return freecom_ine_entities;
    }

    public void setFreecom_ine_entities(List<Freecom_ine_entityDTO> freecom_ine_entities) {
        this.freecom_ine_entities = freecom_ine_entities;
    }

    public Freecom_kind_payment getFreecom_kind_payment() {
        return freecom_kind_payment;
    }

    public void setFreecom_kind_payment(Freecom_kind_payment freecom_kind_payment) {
        this.freecom_kind_payment = freecom_kind_payment;
    }

    public Freecom_foreign_tourist_passenger getFreecom_foreign_tourist_passenger() {
        return freecom_foreign_tourist_passenger;
    }

    public void setFreecom_foreign_tourist_passenger(Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger) {
        this.freecom_foreign_tourist_passenger = freecom_foreign_tourist_passenger;
    }

    public Freecom_partial_construction_services getFreecom_partial_construction_services() {
        return freecom_partial_construction_services;
    }

    public void setFreecom_partial_construction_services(Freecom_partial_construction_services freecom_partial_construction_services) {
        this.freecom_partial_construction_services = freecom_partial_construction_services;
    }

    public Freecom_foreign_exchange getFreecom_foreign_exchange() {
        return freecom_foreign_exchange;
    }

    public void setFreecom_foreign_exchange(Freecom_foreign_exchange freecom_foreign_exchange) {
        this.freecom_foreign_exchange = freecom_foreign_exchange;
    }


    public Freecom_local_taxes getFreecom_local_taxes() {
        return freecom_local_taxes;
    }

    public void setFreecom_local_taxes(Freecom_local_taxes freecom_local_taxes) {
        this.freecom_local_taxes = freecom_local_taxes;
    }

    public List<Freecom_ret_transfsDTO> getFreecom_ret_transfs() {
        return  freecom_ret_transfs;
    }

    public void setFreecom_ret_transfs(List<Freecom_ret_transfsDTO> freecom_ret_transfs) {
        this.freecom_ret_transfs = freecom_ret_transfs;
    }

    public Freecom_used_vehicle getFreecom_used_vehicle() {
        return freecom_used_vehicle;
    }

    public void setFreecom_used_vehicle(Freecom_used_vehicle freecom_used_vehicle) {
        this.freecom_used_vehicle = freecom_used_vehicle;
    }

    public List<Freecom_vehicle_customs_information> getVehicle_customs_informations() {
        return vehicle_customs_informations;
    }

    public void setVehicle_customs_informations(List<Freecom_vehicle_customs_information> vehicle_customs_informations) {
        this.vehicle_customs_informations = vehicle_customs_informations;
    }

    public Freecom_destruction_certificate getFreecom_destruction_certificate() {
        return freecom_destruction_certificate;
    }

    public void setFreecom_destruction_certificate(Freecom_destruction_certificate freecom_destruction_certificate) {
        this.freecom_destruction_certificate = freecom_destruction_certificate;
    }

    public Freecom_info_customs_destruction getFreecom_info_customs_destruction() {
        return freecom_info_customs_destruction;
    }

    public void setFreecom_info_customs_destruction(Freecom_info_customs_destruction freecom_info_customs_destruction) {
        this.freecom_info_customs_destruction = freecom_info_customs_destruction;
    }

    public Freecom_fuel_consumption getFreecom_fuel_consumption() {
        return freecom_fuel_consumption;
    }

    public void setFreecom_fuel_consumption(Freecom_fuel_consumption freecom_fuel_consumption) {
        this.freecom_fuel_consumption = freecom_fuel_consumption;
    }

    public List<Freecom_concept_fuelDTO> getFreecom_concept_fuels() {
        return freecom_concept_fuels;
    }

    public void setFreecom_concept_fuels(List<Freecom_concept_fuelDTO> freecom_concept_fuels) {
        this.freecom_concept_fuels = freecom_concept_fuels;
    }

    public Freecom_storeroom_paybill getFreecom_storeroom_paybill() {
        return freecom_storeroom_paybill;
    }

    public void setFreecom_storeroom_paybill(Freecom_storeroom_paybill freecom_storeroom_paybill) {
        this.freecom_storeroom_paybill = freecom_storeroom_paybill;
    }

    public List<Freecom_paybill_concept> getFreecom_paybill_concepts() {
        return freecom_paybill_concepts;
    }

    public void setFreecom_paybill_concepts(List<Freecom_paybill_concept> freecom_paybill_concepts) {
        this.freecom_paybill_concepts = freecom_paybill_concepts;
    }

    public Freecom_ecc11 getFreecom_ecc11() {
        return freecom_ecc11;
    }

    public void setFreecom_ecc11(Freecom_ecc11 freecom_ecc11) {
        this.freecom_ecc11 = freecom_ecc11;
    }

    public List<Freecom_ecc11_conceptDTO> getFreecom_ecc11_concepts() {
        return freecom_ecc11_concepts;
    }

    public void setFreecom_ecc11_concepts(List<Freecom_ecc11_conceptDTO> freecom_ecc11_concepts) {
        this.freecom_ecc11_concepts = freecom_ecc11_concepts;
    }

    public List<Freecom_spei_thirdDTO> getFreecom_spei_thirds() {
        return freecom_spei_thirds;
    }

    public void setFreecom_spei_thirds(List<Freecom_spei_thirdDTO> freecom_spei_thirds) {
        this.freecom_spei_thirds = freecom_spei_thirds;
    }

    public Freecom_foreign_trade getFreecom_foreign_trade() {
        return freecom_foreign_trade;
    }

    public void setFreecom_foreign_trade(Freecom_foreign_trade freecom_foreign_trade) {
        this.freecom_foreign_trade = freecom_foreign_trade;
    }

    public Freecom_addressee getFreecom_addressee() {
        return freecom_addressee;
    }

    public void setFreecom_addressee(Freecom_addressee freecom_addressee) {
        this.freecom_addressee = freecom_addressee;
    }

    public List<Freecom_CommodityDTO> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<Freecom_CommodityDTO> commodities) {
        this.commodities = commodities;
    }
}
