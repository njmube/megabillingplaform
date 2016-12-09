(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiNewController', CfdiNewController);

    CfdiNewController.$inject = ['$state', '$timeout', 'entity', 'taxpayer_account_entity', 'Cfdi', 'Payment_method', 'Cfdi_types', 'Cfdi_type_doc', 'C_money', 'Taxpayer_account', 'Taxpayer_transactions', 'AlertService', 'Taxpayer_series_folio', '$uibModal', 'Way_payment', 'Tax_types', 'C_tar', 'C_well_type',  'C_acquired_title', 'C_features_work_piece', 'C_school_level', 'C_process_type', 'C_committee_type', 'C_transit_type', 'C_type_road', 'C_municipality', 'C_colony', 'C_zip_code', 'C_federal_entity', 'C_type_operation', 'C_type_series', 'C_type_operation_ce', 'C_key_pediment', 'Com_incoterm', 'C_country', 'C_state', 'Public_notaries_federal_entity'];

    function CfdiNewController($state, $timeout, entity, taxpayer_account_entity, Cfdi, Payment_method, Cfdi_types, Cfdi_type_doc, C_money, Taxpayer_account, Taxpayer_transactions, AlertService, Taxpayer_series_folio, $uibModal, Way_payment, Tax_types, C_tar, C_well_type,  C_acquired_title, C_features_work_piece, C_school_level, C_process_type, C_committee_type, C_transit_type, C_type_road, C_municipality, C_colony, C_zip_code, C_federal_entity, C_type_operation, C_type_series, C_type_operation_ce, C_key_pediment, Com_incoterm, C_country, C_state, Public_notaries_federal_entity) {
        var vm = this;

        vm.cfdi = entity;
        vm.cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
        vm.cfdi.c_money = {id: 100, name: "MXN", description: "Peso Mexicano"};
        vm.taxpayer_series_folio = null;

        vm.taxpayer_account = taxpayer_account_entity;
        vm.taxpayer_accounts = Taxpayer_account.query({page: 0, size: 10});

        vm.onChangeTaxPayerAccount = onChangeTaxPayerAccount;

        function onChangeTaxPayerAccount(){
            if(vm.taxpayer_account == null) {
                vm.taxpayer_client = null;
            }
        }

        checkAvailableRings();

        checkAccountCertificate();

        function checkAvailableRings(){
            Taxpayer_transactions.query({
                page: 0,
                size: 20,
                idaccount: vm.taxpayer_account.id
            }, onSuccess, onError);

            function onSuccess(data) {
                vm.taxpayer_transactions = data;
                vm.taxpayer_transaction = vm.taxpayer_transactions[0];

                if(vm.taxpayer_transaction.transactions_available == 0){
                    AlertService.error("No tiene timbres disponibles. Debe comprar timbres para poder generar una factura. El sistema lo redigirá en en unos segundos...");
                    $timeout(function() {
                        $state.go('taxpayer-account-transaction',{id: vm.taxpayer_account.id});
                    }, 5000);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function checkAccountCertificate(){
            if(vm.taxpayer_account.taxpayer_certificate.id == null){
                AlertService.error('Debe de validar su cuenta para poder generar facturas. El sistema lo redigirá en en unos segundos...');
                $timeout(function() {
                    $state.go('taxpayer-account-general',{id: vm.taxpayer_account.id});
                }, 5000);
            }
        }

        chooseSerieFolio();

        function chooseSerieFolio() {
            Taxpayer_series_folio.query({
                page: 0,
                size: 30,
                taxpayeraccount: vm.taxpayer_account.id
            }, onSuccess, onError);
            function onSuccess(data) {
                var taxpayer_series_folios = data;
                var i;
                for(i = 0; i< taxpayer_series_folios.length; i++){
                    if(taxpayer_series_folios[i].enable){
                        vm.taxpayer_series_folio = taxpayer_series_folios[i];
                        vm.cfdi.serial = vm.taxpayer_series_folio.serie;
                        if(vm.taxpayer_series_folio.folio_current == null) {
                            vm.cfdi.folio = vm.taxpayer_series_folio.folio_start;
                        }
                        else {
                            vm.cfdi.folio = vm.taxpayer_series_folio.folio_current + 1;
                        }
                        break;
                    }
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.taxpayer_client = null;
        vm.chooseClient = chooseClient;

        vm.cfdi_type_docs = Cfdi_type_doc.query({filtername:" "});
        vm.onChangeCFDITypeDoc = onChangeCFDITypeDoc;
        vm.cfdi_typess = Cfdi_types.query({filtername:" "});
        vm.payment_methods = Payment_method.query({filtername: " ", filtercode: " "});
        vm.way_payments = Way_payment.query({filtername:" "});
        vm.enableWithByPartiality = enableWithByPartiality;
        vm.way_payment = null;
        vm.way_payment_x = 0;
        vm.way_payment_y = 0;
        vm.enableWayPayment = enableWayPayment;
        vm.partialityChange = partialityChange;
        vm.wayPaymentXYChange = wayPaymentXYChange;
        vm.failWayPaymentXYValidation = failWayPaymentXYValidation;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.c_monies = C_money.query({pg: -1});
        vm.checkMoneyType = checkMoneyType;
        vm.enableAccountNumber = enableAccountNumber;
        vm.tax_typess = Tax_types.query({filtername: " "});

        vm.show_iva = (0).toFixed(2);
        vm.calc_iva = (0).toFixed(2);
        vm.ieps = (0).toFixed(2);
        vm.ret_iva = (0).toFixed(2);
        vm.ret_isr = (0).toFixed(2);
        vm.subtotal_discount = (0).toFixed(2);

        vm.concepts = [];
        vm.addConcept = addConcept;
        vm.addConceptInfo = addConceptInfo;
        vm.removeConcept = removeConcept;
        vm.save = save;

        function chooseClient(){
            $uibModal.open({
                templateUrl: 'app/entities/cfdi/cfdi-choose-client-dialog.html',
                controller: 'CfdiChooseClientDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    taxpayer_account_entity: function () {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function(data) {
                vm.taxpayer_client = data;
                updateCFDITotals();
            });
        }

        function onChangeCFDITypeDoc(){
            if(vm.cfdi.cfdi_type_doc != undefined && vm.cfdi.cfdi_type_doc.id >= 1 && vm.cfdi.cfdi_type_doc.id <= 7){
                vm.cfdi.cfdi_types = vm.cfdi_typess[0];

                if(vm.current_complement != null && vm.current_complement.id == "ecc11"){
                    vm.show_ecc11 = true;
                    vm.show_ecc11_invalid = false;
                }
            }
            else if(vm.cfdi.cfdi_type_doc != undefined && (vm.cfdi.cfdi_type_doc.id == 8 || vm.cfdi.cfdi_type_doc.id == 9)){
                vm.cfdi.cfdi_types = vm.cfdi_typess[1];

                if(vm.current_complement != null && vm.current_complement.id == "ecc11"){
                    vm.show_ecc11 = false;
                    vm.show_ecc11_invalid = true;
                }
            }
            else if(vm.cfdi.cfdi_type_doc != undefined && vm.cfdi.cfdi_type_doc.id == 10 ){
                vm.cfdi.cfdi_types = vm.cfdi_typess[2];

                if(vm.current_complement != null && vm.current_complement.id == "ecc11"){
                    vm.show_ecc11 = false;
                    vm.show_ecc11_invalid = true;
                }
            }
            else {
                vm.cfdi.cfdi_types = null;
            }

            updateCFDITotals();
        }

        function enableWithByPartiality(){
            if(vm.way_payment != undefined && vm.way_payment.id == 2){
                return false;
            }
            return true;
        }

        function enableWayPayment(){
            if(vm.cfdi.payment_method != undefined && vm.cfdi.payment_method.id == 17){
                return true;
            }
            return false;
        }

        function partialityChange(){
            vm.cfdi.way_payment =  vm.way_payment.name;
        }

        function wayPaymentXYChange(){
            vm.cfdi.way_payment = vm.way_payment.name + " " + vm.way_payment_x + " de " + vm.way_payment_y;
        }

        function failWayPaymentXYValidation(){
            if(vm.way_payment != undefined && vm.way_payment.id == 2 && (vm.way_payment_x > vm.way_payment_y || vm.way_payment_x == 0 || vm.way_payment_y == 0)){
                return true;
            }
            return false;
        }

        vm.datePickerOpenStatus.date_folio_fiscal_orig = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function checkMoneyType(){
            if(vm.cfdi.c_money != undefined && vm.cfdi.c_money.id == 100){
                vm.cfdi.change_type = (1).toFixed(2);
            }
            else
                vm.cfdi.change_type = null;
        }

        function enableAccountNumber(){
            if(vm.cfdi.payment_method != undefined && vm.cfdi.payment_method.id >= 2 && vm.cfdi.payment_method.id <= 17){
                return false;
            }
            return true;
        }

        function addConcept(){
            $uibModal.open({
                templateUrl: 'app/entities/cfdi/cfdi-add-concept-dialog.html',
                controller: 'CfdiAddConceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            no_identification: null,
                            quantity: null,
                            description: null,
                            unit_value: null,
                            predial_number: null,
                            discount: null,
                            amount: null,
                            id: null
                        };
                    },
                    taxpayer_account_entity: vm.taxpayer_account
                }
            }).result.then(function(result) {
                var result_concat = vm.concepts.concat(result);
                vm.concepts = result_concat;
                updateCFDITotals();
            });
        }

        function addConceptInfo(index){
            $uibModal.open({
                templateUrl: 'app/entities/cfdi/cfdi-add-concept-info-dialog.html',
                controller: 'CfdiAddConceptInfoDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    customs_infos: function () {
                        if(vm.concepts[index].customs_infos != null && vm.concepts[index].customs_infos != undefined){
                            return vm.concepts[index].customs_infos;
                        }
                        else {
                            return [];
                        }
                    },
                    part_concepts: function () {
                        if(vm.concepts[index].part_concepts != null && vm.concepts[index].part_concepts != undefined){
                            return vm.concepts[index].part_concepts;
                        }
                        else {
                            return [];
                        }
                    },
                    taxpayer_account_entity: vm.taxpayer_account
                }
            }).result.then(function(result) {
                var data = result;
                vm.concepts[index].customs_infos = data.customs_infos;
                vm.concepts[index].part_concepts = data.part_concepts;
            });
        }

        function removeConcept(index){
            vm.concepts.splice(index, 1);
            updateCFDITotals();
        }

        function updateCFDITotals(){
            var subtotal = 0;

            var show_iva = 0;
            var show_iva_val16 = 16.00;
            var show_iva_val15 = 15.00;
            var calc_iva = 0;

            var ieps = 0;
            var ret_iva = 0;
            var ret_isr = 0;
            var discount = 0;
            var subtotal_discount = 0;
            var total = 0;

            var disabled_iva_value = -1;

            var total_tax_transfered = 0;
            var total_tax_retention = 0;

            var bln = new BigLargeNumberOperations();
            var i;
            for(i=0; i < vm.concepts.length; i++){
                //calculating free cfdi subtotal...
                //subtotal = subtotal + vm.free_concepts[i].free_concept.quantity * vm.free_concepts[i].free_concept.unit_value;
                subtotal = bln.add(subtotal, bln.multiply(vm.concepts[i].concept.quantity, vm.concepts[i].concept.unit_value, 6), 6);

                //total_tax_transfered = parseFloat(total_tax_transfered) + parseFloat(vm.free_concepts[i].free_concept_iva.amount) + parseFloat(vm.free_concepts[i].free_concept_ieps.amount);
                total_tax_transfered = bln.add(total_tax_transfered, bln.add(vm.concepts[i].concept_iva.amount, vm.concepts[i].concept_ieps.amount, 6), 6);

                //getting iva to show to user...
                if(vm.concepts[i].concept_iva.rate ==  show_iva_val16 || vm.concepts[i].concept_iva.rate == show_iva_val15){
                    show_iva = vm.concepts[i].concept_iva.rate;
                }

                //calculating free cfdi iva...
                if(vm.cfdi.cfdi_type_doc != undefined && vm.cfdi.cfdi_type_doc.id == 6){
                    calc_iva = 0;
                }
                else if(vm.cfdi.cfdi_type_doc != undefined && vm.cfdi.cfdi_type_doc.id == 1){
                    var iva_calc_val = 0;
                    if(vm.concepts[i].concept_iva.rate == show_iva_val16){
                        iva_calc_val = 16/100;
                    }
                    if(vm.concepts[i].concept_iva.rate == show_iva_val15){
                        iva_calc_val = 15/100;
                    }

                    //calc_iva = calc_iva + iva_calc_val * vm.free_concepts[i].free_concept.amount * (1 + vm.free_concepts[i].free_concept_ieps.rate/100);
                    calc_iva = bln.add(calc_iva, bln.multiply(bln.multiply(iva_calc_val, vm.concepts[i].concept.amount, 6), (1 + vm.concepts[i].concept_ieps.rate/100), 6), 6);
                }
                else {
                    var iva_calc_val = 0;
                    if(vm.concepts[i].concept_iva.rate == show_iva_val16){
                        iva_calc_val = 16/100;
                    }
                    if(vm.concepts[i].concept_iva.rate == show_iva_val15){
                        iva_calc_val = 15/100;
                    }

                    //calc_iva = calc_iva + vm.free_concepts[i].free_concept.amount * iva_calc_val;
                    calc_iva = bln.add(calc_iva, bln.multiply(vm.concepts[i].concept.amount, iva_calc_val, 6), 6);
                }

                //calculating free cfdi ieps...
                if(vm.cfdi.cfdi_type_doc != undefined && (vm.cfdi.cfdi_type_doc.id == 1 || vm.cfdi.cfdi_type_doc.id == 8)){
                    //ieps = ieps + vm.free_concepts[i].free_concept_ieps.rate/100 * vm.free_concepts[i].free_concept.amount;
                    ieps = bln.add(ieps, bln.multiply(vm.concepts[i].concept_ieps.rate/100, vm.concepts[i].concept.amount, 6), 6);
                }

                //calculationg subtotal - discount
                //subtotal_discount = parseFloat(subtotal_discount) + parseFloat(vm.free_concepts[i].free_concept.amount);
                subtotal_discount = bln.add(subtotal_discount, vm.concepts[i].concept.amount, 6);
            }

            //calculating free cfdi ret iva and ret isr...
            if(vm.taxpayer_account.rfc != undefined && vm.taxpayer_account.rfc.length == 13 && vm.taxpayer_client != null && vm.taxpayer_client.rfc.length == 12 && vm.cfdi.cfdi_type_doc != undefined) {

                if (vm.cfdi.cfdi_type_doc.id == 2 || vm.cfdi.cfdi_type_doc.id == 5) {
                    //ret_iva = 2/3 * calc_iva;
                    ret_iva = bln.multiply(2 / 3, calc_iva, 6);

                    //ret_isr = 1/10 * subtotal_discount;
                    ret_isr = bln.multiply(1 / 10, subtotal_discount, 6);
                }
                else if (vm.cfdi.cfdi_type_doc.id == 3) {
                    //ret_iva = 2/3 * calc_iva;
                    ret_iva = bln.multiply(2 / 3, calc_iva, 6);
                }

            }
            else if(vm.cfdi.cfdi_type_doc != undefined && vm.cfdi.cfdi_type_doc.id == 4){
                //ret_iva = 0.04 * subtotal_discount;
                ret_iva = bln.multiply(0.04, subtotal_discount, 6);
            }

            //showing all...
            vm.cfdi.subtotal =  bln.add(subtotal, 0, vm.taxpayer_account.accuracy);

            if(show_iva != 0){
                vm.show_iva = show_iva;
            }

            vm.calc_iva = bln.add(calc_iva, 0, vm.taxpayer_account.accuracy);

            vm.ieps = bln.add(ieps, 0, vm.taxpayer_account.accuracy);

            vm.ret_iva = bln.add(ret_iva, 0, vm.taxpayer_account.accuracy);

            vm.ret_isr = bln.add(ret_isr, 0, vm.taxpayer_account.accuracy);

            //discount = subtotal - subtotal_discount;
            discount = bln.minus(subtotal, subtotal_discount, vm.taxpayer_account.accuracy);
            vm.cfdi.discount = discount;

            vm.subtotal_discount = bln.add(subtotal_discount, 0, vm.taxpayer_account.accuracy);

            //total = (subtotal_discount + calc_iva) - (ret_iva +  ret_isr) + ieps;
            total = bln.add(bln.minus(bln.add(subtotal_discount, calc_iva, 6), bln.add(ret_iva, ret_isr, 6), 6), ieps, vm.taxpayer_account.accuracy);
            vm.cfdi.total = total;

            vm.disabled_iva_value = disabled_iva_value;

            vm.cfdi.total_tax_transfered = bln.add(total_tax_transfered, 0, vm.taxpayer_account.accuracy);

            total_tax_retention = bln.add(ret_iva, ret_isr, vm.accuracy);
            vm.cfdi.total_tax_retention = total_tax_retention;
        }

        function save () {
            var bln = new BigLargeNumberOperations();

            vm.cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
            vm.cfdi.taxpayer_account = vm.taxpayer_account;

            if(vm.cfdi.mont_folio_fiscal_orig != null && vm.cfdi.mont_folio_fiscal_orig > 0){
                vm.cfdi.mont_folio_fiscal_orig = bln.add(vm.cfdi.mont_folio_fiscal_orig, 0, vm.taxpayer_account.accuracy);
            }

            var concept;
            var concepts = [];

            var tax_transfereds = [];
            var tax_transfered_iva;
            var tax_transfered_ieps;

            var tax_retentions = [];
            var amount_iva_retentions;
            var tax_retentions_iva;
            var amount_isr_retentions;
            var tax_retentions_isr;

            var i;
            for(i=0; i < vm.concepts.length; i++){
                concept = vm.concepts[i].concept;
                concepts.push(concept);

                //getting IVA for tax_transferred...
                tax_transfered_iva = vm.concepts[i].concept_iva;
                if(tax_transfered_iva.amount > 0){
                    tax_transfereds.push(tax_transfered_iva);
                }

                //getting IEPS for tax_transferred...
                tax_transfered_ieps = vm.concepts[i].concept_ieps;
                if(tax_transfered_ieps.amount > 0){
                    tax_transfereds.push(tax_transfered_ieps);
                }

                //getting IVA for tax_retentions
                amount_iva_retentions = 0;
                //calculating free cfdi ret iva...
                if((vm.taxpayer_account.rfc != undefined && vm.taxpayer_account.rfc.length == 13 && vm.taxpayer_client != null && vm.taxpayer_client.rfc.length == 12) && (vm.cfdi.cfdi_type_doc != undefined && (vm.cfdi.cfdi_type_doc.id == 2 || vm.cfdi.cfdi_type_doc.id == 3 || vm.cfdi.cfdi_type_doc.id == 5))){
                    //amount_iva_retentions = 2/3 * free_concept.quantity * free_concept.unit_value;
                    amount_iva_retentions = bln.multiply(bln.multiply(2/3, concept.quantity, 6), concept.unit_value, vm.taxpayer_account.accuracy);
                }
                if(vm.cfdi.cfdi_type_doc != undefined && vm.cfdi.cfdi_type_doc.id == 4){
                    //amount_iva_retentions = 0.04 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
                    amount_iva_retentions = bln.multiply(bln.multiply(bln.multiply(0.04, concept.quantity, 6), concept.unit_value, 6), (1 - concept.discount/100), vm.taxpayer_account.accuracy);
                }

                if(amount_iva_retentions > 0){
                    tax_retentions_iva = {
                        amount: amount_iva_retentions,
                        tax_types: vm.tax_typess[0],
                        id: null
                    };

                    tax_retentions.push(tax_retentions_iva);

                    vm.concepts[i].tax_retentions_iva = tax_retentions_iva;
                }
                else{
                    vm.concepts[i].tax_retentions_iva = null;
                }

                //getting ISR for tax_retentions
                amount_isr_retentions = 0;
                //calculating free cfdi ret isr...
                if((vm.taxpayer_account.rfc != undefined && vm.taxpayer_account.rfc.length == 13 && vm.taxpayer_client != null && vm.taxpayer_client.rfc.length == 12) || (vm.cfdi.cfdi_type_doc != undefined && (vm.cfdi.cfdi_type_doc.id == 2 || vm.cfdi.cfdi_type_doc.id == 5))){
                    //amount_isr_retentions = 1/10 * free_concept.quantity * free_concept.unit_value * (1 - free_concept.discount/100);
                    amount_isr_retentions = bln.multiply(bln.multiply(bln.multiply(1/10, concept.quantity, 6), concept.unit_value, 6), (1 - concept.discount/100), vm.taxpayer_account.accuracy);
                }

                if(amount_isr_retentions > 0){
                    tax_retentions_isr = {
                        amount: amount_isr_retentions,
                        tax_types: vm.tax_typess[1],
                        id: null
                    };

                    tax_retentions.push(tax_retentions_isr);

                    vm.concepts[i].tax_retentions_isr = tax_retentions_isr;
                }
                else {
                    vm.concepts[i].tax_retentions_isr = null;
                }
            }

            var cfdiDTO = {
                taxpayer_client: vm.taxpayer_client,
                cfdi: vm.cfdi,
                taxpayer_series_folio: vm.taxpayer_series_folio,
                conceptDTOs: vm.concepts,
                concepts: concepts,
                taxTransfereds: tax_transfereds,
                taxRetentions: tax_retentions
            };

            if(vm.current_complement != null) {

                switch (vm.current_complement.id) {
                    case "taxregistration":
                        vm.com_taxregistration.version = "1.0";
                        cfdiDTO.com_taxregistration = vm.com_taxregistration;
                        break;
                    case "pfic":
                        vm.com_pfic.version = "1.0";
                        cfdiDTO.com_pfic = vm.com_pfic;
                        break;
                    case "accreditation_ieps":
                        vm.com_accreditation_ieps.version = "1.0";
                        cfdiDTO.com_accreditation_ieps = vm.com_accreditation_ieps;
                        break;
                    case "taxlegends":
                        vm.com_taxlegends.version = "1.0";
                        cfdiDTO.com_taxlegends = vm.com_taxlegends;
                        cfdiDTO.com_legends = vm.legends;
                        break;
                    case "airline":
                        vm.com_airline.version = "1.0";
                        cfdiDTO.com_airline = vm.com_airline;
                        cfdiDTO.com_charges = vm.charges;
                        break;
                    case "apaw":
                        vm.com_apaw.version = "1.0";
                        cfdiDTO.com_apaw = vm.com_apaw;
                        break;
                    case "donees":
                        vm.com_donees.version = "1.0";
                        cfdiDTO.com_donees = vm.com_donees;
                        break;
                    case "educational_institutions":
                        vm.com_educational_institutions.version = "1.0";
                        cfdiDTO.com_educational_institutions = vm.com_educational_institutions;
                        break;
                    case "ine":
                        vm.com_ine.version = "1.0";
                        cfdiDTO.com_ine = vm.com_ine;
                        cfdiDTO.com_ine_entities = vm.com_ine_entities;
                        break;
                    case "kind_payment":
                        vm.com_kind_payment.version = "1.0";
                        cfdiDTO.com_kind_payment = vm.com_kind_payment;
                        break;
                    case "foreign_tourist_passenger":
                        vm.com_foreign_tourist_passenger.version = "1.0";
                        cfdiDTO.com_foreign_tourist_passenger = vm.com_foreign_tourist_passenger;
                        break;
                    case "partial_construction_services":
                        vm.com_partial_construction_services.version = "1.0";
                        cfdiDTO.com_partial_construction_services = vm.com_partial_construction_services;
                        break;
                    case "foreign_exchange":
                        cfdiDTO.com_foreign_exchange = vm.com_foreign_exchange;
                        break;
                    case "local_taxes":
                        vm.com_local_taxes.version = "1.0";
                        cfdiDTO.com_local_taxes = vm.com_local_taxes;
                        cfdiDTO.com_ret_transfs = vm.com_ret_transfs;
                        break;
                    case "used_vehicle":
                        vm.com_used_vehicle.version = "1.0";
                        cfdiDTO.com_used_vehicle = vm.com_used_vehicle;
                        cfdiDTO.vehicle_customs_informations = vm.vehicle_customs_informations;
                        break;
                    case "destruction_certificate":
                        vm.com_destruction_certificate.version = "1.0";
                        cfdiDTO.com_destruction_certificate = vm.com_destruction_certificate;
                        if(vm.use_info_customs_destruction) {
                            cfdiDTO.com_info_customs_destruction = vm.com_info_customs_destruction;
                        }
                        break;
                    case "fuel_consumption":
                        vm.com_fuel_consumption.version = "1.0";
                        cfdiDTO.com_fuel_consumption = vm.com_fuel_consumption;
                        cfdiDTO.com_concept_fuels = vm.com_concept_fuels;
                        break;
                    case "storeroom_paybill":
                        vm.com_storeroom_paybill.version = "1.0";
                        cfdiDTO.com_storeroom_paybill = vm.com_storeroom_paybill;
                        cfdiDTO.com_paybill_concepts = vm.com_paybill_concepts;
                        break;
                    case "ecc11":
                        vm.com_ecc_11.version = "1.0";
                        cfdiDTO.com_ecc_11 = vm.com_ecc_11;
                        cfdiDTO.com_ecc_11_concepts = vm.com_ecc_11_concepts;
                        break;
                    case "spei":
                        cfdiDTO.com_spei_thirds = vm.com_spei_thirds;
                        break;
                    case "foreign_trade":
                        vm.com_foreign_trade.version = "1.0";
                        cfdiDTO.com_foreign_trade = vm.com_foreign_trade;
                        cfdiDTO.com_addressee = vm.com_addressee;
                        cfdiDTO.commodities = vm.c_add_commodities;
                        break;
                    case "public_notaries":
                        vm.com_public_notaries.version = "1.0";
                        cfdiDTO.com_public_notaries = vm.com_public_notaries;
                        cfdiDTO.com_desc_states = vm.com_desc_states;
                        cfdiDTO.com_data_operation = vm.com_data_operation;
                        cfdiDTO.com_notary_data = vm.com_notary_data;
                        cfdiDTO.com_data_enajenante = vm.com_data_enajenante;
                        cfdiDTO.com_dataunenajenante = vm.com_dataunenajenante;
                        if(vm.use_dataenajenantecopsc) {
                            cfdiDTO.com_dataenajenantecopscs = vm.com_dataenajenantecopscs;
                        }
                        else {
                            cfdiDTO.com_dataenajenantecopscs = [];
                        }
                        cfdiDTO.com_acquiring_data = vm.com_acquiring_data;
                        cfdiDTO.com_dataunacquiring = vm.com_dataunacquiring;
                        if(vm.use_dataacquiringcopsc) {
                            cfdiDTO.com_dataacquiringcopscs = vm.com_dataacquiringcopscs;
                        }
                        else {
                            cfdiDTO.com_dataacquiringcopscs = [];
                        }
                        break;
                }
            }

            vm.isSaving = true;

            if (vm.cfdi.id !== null) {
                Cfdi.update(cfdiDTO, onSaveSuccess, onSaveError);
            } else {
                Cfdi.save(cfdiDTO, onSaveSuccess, onSaveError);
            }
        }

        var onSaveError = function () {
            vm.isSaving = false;
        };

        var onSaveSuccess = function () {
            vm.taxpayer_client = null;
            vm.cfdi = {version: null, serial: null, folio: null, date_expedition: null, payment_conditions: null, change_type: (1).toFixed(2), place_expedition: null, account_number: null, folio_fiscal_orig: null, serial_folio_fiscal_orig: null, date_folio_fiscal_orig: null, mont_folio_fiscal_orig: null, total_tax_retention: null, total_tax_transfered: null, discount: (0).toFixed(2), discount_reason: null, subtotal: (0).toFixed(2), total: (0).toFixed(2), addenda: null, number_certificate: null, certificate: null, id: null};
            vm.cfdi.tax_regime = null;
            vm.cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
            vm.cfdi.c_money = {id: 100, name: "MXN", description: "Peso Mexicano"};
            chooseSerieFolio();
            vm.show_iva = (0).toFixed(2);
            vm.calc_iva = (0).toFixed(2);
            vm.ieps = (0).toFixed(2);
            vm.ret_iva = (0).toFixed(2);
            vm.ret_isr = (0).toFixed(2);
            vm.subtotal_discount = (0).toFixed(2);
            vm.concepts = [];

            checkAvailableRings();

            vm.current_complement = null;
            resetComplements();

            vm.isSaving = false;


        };

        //Complements
        vm.current_complement = null;

        vm.complements = [
            {id:"taxregistration", name:"CFDI Registro Fiscal"},
            {id:"pfic", name: "Persona Física Integrante de Coordinado"},
            {id:"accreditation_ieps", name: "Concepto - Acreditación del IEPS"},
            {id:"taxlegends", name: "Leyendas Fiscales"},
            {id:"airline", name: "Aerolíneas"},
            {id:"apaw", name: "Obras de Artes Plásticas y Antigüedades"},
            {id:"donees", name: "Donatarias"},
            {id:"educational_institutions", name: "Concepto - Instituciones Educativas Privadas"},
            {id:"ine", name: "INE"},
            {id:"kind_payment", name: "Pago en Especie"},
            {id:"foreign_tourist_passenger", name: "Turista Pasajero Extranjero"},
            {id:"partial_construction_services", name: "Servicios Parciales de Construcción"},
            {id:"foreign_exchange", name: "Divisas"},
            {id:"local_taxes", name: "Otros Derechos e Impuestos"},
            {id:"used_vehicle", name: "Vehículo Usado"},
            {id:"destruction_certificate", name: "Certificado de Destrucción"},
            {id:"fuel_consumption", name: "Consumo de Combustibles"},
            {id:"storeroom_paybill", name: "Vales de Despensa"},
            {id:"ecc11", name: "Estado de Cuenta de Combustibles de Monederos Electrónicos Autorizados"},
            {id:"spei", name: "SPEI Tercero a Tercero"},
            {id:"foreign_trade", name: "Comercio Exterior"},
            {id:"public_notaries", name: "Notarios Públicos"}
        ];

        vm.onChangeComplemennt = function(){
            resetComplements();
            if(vm.current_complement != null && vm.current_complement.id != null){
                showComplemnt();
            }
        };

        function showComplemnt(){
            switch(vm.current_complement.id){
                case "taxregistration":
                    vm.show_taxregistration = true;
                    break;
                case "pfic":
                    vm.show_pfic = true;
                    break;
                case "accreditation_ieps":
                    vm.show_accreditation_ieps = true;
                    break;
                case "taxlegends":
                    vm.show_taxlegends = true;
                    break;
                case "airline":
                    vm.show_airline = true;
                    break;
                case "apaw":
                    vm.show_apaw = true;
                    break;
                case "donees":
                    vm.show_donees = true;
                    break;
                case "educational_institutions":
                    vm.show_educational_institutions = true;
                    break;
                case "ine":
                    vm.show_ine = true;
                    break;
                case "kind_payment":
                    vm.show_kind_payment = true;
                    break;
                case "foreign_tourist_passenger":
                    vm.show_foreign_tourist_passenger = true;
                    break;
                case "partial_construction_services":
                    vm.show_partial_construction_services = true;
                    break;
                case "foreign_exchange":
                    vm.show_foreign_exchange = true;
                    break;
                case "local_taxes":
                    vm.show_local_taxes = true;
                    break;
                case "used_vehicle":
                    vm.show_used_vehicle = true;
                    break;
                case "destruction_certificate":
                    vm.show_destruction_certificate = true;
                    break;
                case "fuel_consumption":
                    vm.show_fuel_consumption = true;
                    break;
                case "storeroom_paybill":
                    vm.show_storeroom_paybill = true;
                    break;
                case "ecc11":
                    if(vm.cfdi.cfdi_types != null && vm.cfdi.cfdi_types.id == 1) {
                        vm.show_ecc11 = true;
                        vm.show_ecc11_invalid = false;
                    }
                    else{
                        vm.show_ecc11 = false;
                        vm.show_ecc11_invalid = true;
                    }
                    break;
                case "spei":
                    vm.show_spei = true;
                    break;
                case "foreign_trade":
                    vm.show_foreign_trade = true;
                    break;

                case "public_notaries":
                    vm.show_public_notaries = true;
                    break;
            }
        }

        function resetComplements(){

            vm.show_taxregistration = false;
            vm.com_taxregistration = { version: null, folio: null, id: null };

            vm.show_pfic = false;
            vm.com_pfic = { version: null, key_vehicle: null, license_plate: null, rfcpf: null, id: null };

            vm.show_accreditation_ieps = false;
            vm.com_accreditation_ieps = { version: null, id: null };

            vm.show_taxlegends = false;
            vm.com_taxlegends = { version: null, id: null };
            vm.legends = [];

            vm.show_airline = false;
            vm.com_airline = {version: null, tua: null, total_charge: null, id: null};
            vm.charges = [];

            vm.show_apaw = false;
            vm.com_apaw = { version: null, others_well_type: null, others_acquired_title: null, subtotal: (0).toFixed(2), iva: (0).toFixed(2), date_acquisition: new Date(), c_well_type: null, c_acquired_title: null, c_features_work_piece: null, id: null };
            vm.disable_others_well_type = true;
            vm.disable_others_acquired_title = true;

            vm.show_donees = false;
            vm.com_donees = { version: null, no_authorization: null, date_authorization: new Date(), legend: null,  id: null };

            vm.show_educational_institutions = false;
            vm.com_educational_institutions = { version: null, name_student: null,  curp: null, autrvoe: null, rfcpayment: null, c_school_level: null, id: null };

            vm.show_ine = false;
            vm.com_ine = { version: null, ident: null, c_committee_type: null, c_process_type: null, id: null };
            vm.com_ine_entities = [];
            vm.c_committee_type_required = false;
            vm.c_committee_type_disabled = false;
            vm.ident_disabled = false;
            vm.add_button_ine_entity_disabled = true;
            vm.ine_entity_scope_required = false;
            vm.ine_entity_scope_disabled = false;

            vm.show_kind_payment = false;
            vm.com_kind_payment = { version: null, cvepic: null, foliosoldon: null, art_piece_name: null, technical_art_piece: null, year_art_piece: null, dimensional_art_piece: null, id: null };

            vm.show_foreign_tourist_passenger = false;
            vm.com_foreign_tourist_passenger = { version: null, date_traffic: new Date(), typeid: null, numerid: null, nationality: null, transportcompany: null, idtransport: null, c_transit_type: null, c_type_road: null, id: null };

            vm.show_partial_construction_services = false;
            vm.com_partial_construction_services = { version: null, street: null, noext: null, noint: null, colony: null, location: null, reference: null, municipality: null, zipcode: null, numperlicoaut: null, c_federal_entity: null, id: null};

            vm.show_foreign_exchange = false;
            vm.com_foreign_exchange = { c_type_operation: null, id: null};

            vm.show_local_taxes = false;
            vm.com_local_taxes = { version: null, total_local_retentions: null, total_local_transfered: null, id: null };
            vm.com_ret_transfs = [];

            vm.show_used_vehicle = false;
            vm.com_used_vehicle = { version: null, acquisition_amount: null, monto_enajenacion: null, key_vehicle: null, brand: null, type: null, model: null, number_engine: null, no_serie: null, niv: null, value: null, id: null };
            vm.vehicle_customs_informations = [];

            vm.show_destruction_certificate = false;
            vm.com_destruction_certificate = { version: null, numfoldesveh: null, brand: null, class_dc: null, year: null, model: null, niv: null, no_serie: null, number_plates: null, number_engine: null, numfoltarjcir: null, id: null };
            vm.com_info_customs_destruction = { numpedimp: null, date_expedition: null, customs: null, com_destruction_certificate: null, id: null};
            vm.use_info_customs_destruction = false;

            vm.show_fuel_consumption = false;
            vm.com_fuel_consumption = { version: null, type_operation: "Monedero Electrónico", account_number: null, subtotal: (0).toFixed(2), total: (0).toFixed(2), id: null };
            vm.com_concept_fuels = [];

            vm.show_storeroom_paybill = false;
            vm.com_storeroom_paybill = { version: null, type_operation: "Monedero Electrónico", employer_registration: null, account_number: null, total: null, id: null};
            vm.com_paybill_concepts = [];

            vm.show_ecc11 = false;
            vm.show_ecc11_invalid = false;
            vm.com_ecc_11 = {version: null, type_operation: "Tarjeta", number_account: null, subtotal: null, total: null, id: null };
            vm.com_ecc_11_concepts = [];

            vm.show_spei = false;
            vm.com_spei = { id: null };
            vm.com_spei_thirds = [];

            vm.show_foreign_trade = false;
            vm.com_foreign_trade = { version: null, emitter_curp: null, receiver_curp: null, receiver_numregidtrib: null, origin_certificate: null, number_origin_certificate: null, number_reliable_exporter: null, subdivision: null, observations: null, typechangeusd: null, totalusd: null, id: null };
            vm.com_addressee = { street: null, no_ext: null, no_int: null, locate: null, reference: null, numregidtrib: null, rfc: null, curp: null, name: null, id: null };
            vm.c_add_commodities = [];

            vm.show_public_notaries = false;
            vm.com_public_notaries = { version: null, id: null };
            vm.com_desc_states = [];
            vm.com_data_operation = { notarialinstrument: null, dateinstnotarial: null, amountofoperation: null, subtotal: null, iva: null, id: null };
            vm.com_notary_data = { curp: null, notarynumber: null, ascription: null, id: null };
            vm.com_data_enajenante = { coprosocconyugaie: null, id: null };
            vm.com_dataunenajenante = { name: null, last_name: null, mother_last_name: null, rfc: null, curp: null, id: null };
            vm.use_dataenajenantecopsc = false;
            vm.com_dataenajenantecopscs = [];
            vm.com_acquiring_data = { coprosocconyugaie: null, id: null };
            vm.com_dataunacquiring = { name: null, last_name: null, mother_last_name: null, rfc: null, curp: null, id: null };
            vm.use_dataacquiringcopsc = false;
            vm.com_dataacquiringcopscs = [];
        }

        function floorFigure(figure, decimals){
            if (!decimals) decimals = 2;
            var d = Math.pow(10,decimals);
            return (parseInt(figure*d)/d).toFixed(decimals);
        }

        //Tax Registration
        vm.show_taxregistration = false;
        vm.com_taxregistration = null;

        //PIFC
        vm.show_pfic = false;
        vm.com_pfic = null;

        //Accreditation IEPS
        vm.show_accreditation_ieps = false;
        vm.com_accreditation_ieps = null;
        vm.c_tars = C_tar.query();

        //Tax Legends
        vm.show_taxlegends = false;
        vm.com_taxlegends = null;
        vm.legends = [];

        vm.addLegend = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-legends/com-legends-dialog.html',
                controller: 'Com_legendsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            tax_provision: null,
                            norm: null,
                            text_legend: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.legends.push(result);
            });
        };

        vm.removeLegend = function(index){
            vm.legends.splice(index,1);
        };

        //Airline
        vm.show_airline = false;
        vm.com_airline = null;
        vm.charges = [];

        vm.addCharge = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-charge/com-charge-dialog.html',
                controller: 'Com_chargeDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            codecharge: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.charges.push(result);
                updateAirLineTotalCharge();
            });
        };

        vm.removeCharge = function(index){
            vm.charges.splice(index,1);
            updateAirLineTotalCharge();
        };

        function updateAirLineTotalCharge(){
            var total_charge = 0;
            var i;
            for(i = 0; i < vm.charges.length; i++){
                total_charge = parseFloat(total_charge) + parseFloat(vm.charges[i].amount);
            }
            vm.com_airline.total_charge = floorFigure(total_charge, 2);
        }

        //Apaw
        vm.show_apaw = false;
        vm.com_apaw = null;
        vm.disable_others_well_type = true;
        vm.disable_others_acquired_title = true;
        vm.c_well_types = C_well_type.query();
        vm.c_acquired_titles = C_acquired_title.query();
        vm.c_features_work_pieces = C_features_work_piece.query();

        vm.onApawWellTypeChange = function(){
            if(vm.com_apaw.c_well_type != null && vm.com_apaw.c_well_type.id == 4){
                vm.disable_others_well_type = false;
            }
            else{
                vm.disable_others_well_type = true;
            }
        };

        vm.onApawAcquiredTitleChange = function(){
            if(vm.com_apaw.c_acquired_title != null && vm.com_apaw.c_acquired_title.id == 5){
                vm.disable_others_acquired_title = false;
            }
            else{
                vm.disable_others_acquired_title = true;
            }
        };

        vm.maxApawDate = new Date();
        vm.dateApawPickerOpenStatus = {};
        vm.dateApawPickerOpenStatus.date_acquisition = false;

        vm.openApawCalendar = function(date) {
            vm.dateApawPickerOpenStatus[date] = true;
        };

        //Donees
        vm.show_donees = false;
        vm.com_donees = null;

        vm.dateDoneesPickerOpenStatus = {};
        vm.dateDoneesPickerOpenStatus.date_authorization = false;

        vm.openDoneesCalendar = function(date) {
            vm.dateDoneesPickerOpenStatus[date] = true;
        };

        //Educational Institutions
        vm.show_educational_institutions = false;
        vm.com_educational_institutions = null;
        vm.c_school_levels = C_school_level.query();

        //Ine
        vm.show_ine = false;
        vm.com_ine = null;
        vm.c_process_types = C_process_type.query();
        vm.c_committee_types = C_committee_type.query();

        vm.c_committee_type_required = false;
        vm.c_committee_type_disabled = false;
        vm.ident_disabled = false;
        vm.add_button_ine_entity_disabled = true;
        vm.ine_entity_scope_required = false;
        vm.ine_entity_scope_disabled = false;

        vm.onChangeCprocessType = function(){
            if(vm.com_ine.c_process_type != null && vm.com_ine.c_process_type.id == 1) {
                vm.c_committee_type_required = true;
                vm.c_committee_type_disabled = false;
                vm.ident_disabled = false;

                vm.com_ine_entities = [];
                vm.add_button_ine_entity_disabled = true;

                vm.ine_entity_scope_required = false;
                vm.ine_entity_scope_disabled = false;
            }
            else if(vm.com_ine.c_process_type != null && vm.com_ine.c_process_type.id != 1){
                vm.c_committee_type_required = false;
                vm.c_committee_type_disabled = true;
                vm.com_ine.c_committee_type = null;

                vm.com_ine.ident = null;
                vm.ident_disabled = true;

                vm.com_ine_entities = [];
                vm.add_button_ine_entity_disabled = false;

                vm.ine_entity_scope_required = true;
                vm.ine_entity_scope_disabled = false;
            }
        };

        vm.onChangeCcommitteeType = function(){
            if(vm.com_ine.c_committee_type != null && vm.com_ine.c_committee_type.id == 1) {
                vm.ident_disabled = false;

                vm.com_ine_entities = [];
                vm.add_button_ine_entity_disabled = true;
            }
            else if(vm.com_ine.c_committee_type != null && vm.com_ine.c_committee_type.id == 2){
                vm.com_ine.ident = null;
                vm.ident_disabled = true;

                vm.com_ine_entities = [];
                vm.add_button_ine_entity_disabled = false;

                vm.ine_entity_scope_required = false;
                vm.ine_entity_scope_disabled = true;
            }
            else if(vm.com_ine.c_committee_type != null && vm.com_ine.c_committee_type.id == 3){
                vm.ident_disabled = false;

                vm.com_ine_entities = [];
                vm.add_button_ine_entity_disabled = false;

                vm.ine_entity_scope_required = false;
                vm.ine_entity_scope_disabled = true;
            }
        };

        vm.com_ine_entities = [];
        vm.key_scope_combinations = [];

        vm.addIneEntity = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-ine-entity/com-ine-entity-dialog.html',
                controller: 'Com_ine_entityDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            id: null
                        };
                    },
                    entity_req: function() {
                        return {
                            ine_entity_scope_required: vm.ine_entity_scope_required,
                            ine_entity_scope_disabled: vm.ine_entity_scope_disabled
                        };
                    },
                    key_scope_combinations: function(){
                        return vm.key_scope_combinations;
                    }
                }
            }).result.then(function(result) {
                vm.com_ine_entities.push(result);
                updateKeyScopeCombinations();
            });
        };

        vm.removeIneEntity = function(index){
            vm.com_ine_entities.splice(index,1);
            updateKeyScopeCombinations();
        };

        function updateKeyScopeCombinations(){
            vm.key_scope_combinations = [];
            var i;
            for(i = 0; i < vm.com_ine_entities.length; i++){
                vm.key_scope_combinations.push({
                    key: vm.com_ine_entities[i].com_ine_entity.key_entity,
                    scope: vm.com_ine_entities[i].com_ine_entity.c_scope_type
                });
            }
        }

        //Kind Payment
        vm.show_kind_payment = false;
        vm.com_kind_payment = null;

        //Foreign Tourist Passenger
        vm.show_foreign_tourist_passenger = false;
        vm.com_foreign_tourist_passenger = null;


        vm.dateForeignTouristPassengerPickerOpenStatus = {};
        vm.dateForeignTouristPassengerPickerOpenStatus.date_traffic = false;

        vm.openForeignTouristPassengerCalendar = function(date) {
            vm.dateForeignTouristPassengerPickerOpenStatus[date] = true;
        };

        vm.c_transit_types = C_transit_type.query();
        vm.c_type_roads = C_type_road.query();

        //Partial Construction Services
        vm.show_partial_construction_services = false;
        vm.com_partial_construction_services = null;

        vm.pcs_c_municipalities = C_municipality.query({stateId: 0, filtername:" "});
        vm.pcs_c_colonies = null;

        vm.onChangePCS_C_municipality = function() {
            var municipalityId = vm.com_partial_construction_services.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.pcs_c_colonies = result;
                vm.com_partial_construction_services.c_zip_code = null;
            });
        };

        vm.onChangePCS_C_colony = function() {
            var colonyId = vm.com_partial_construction_services.c_colony.id;
            C_zip_code.get({id: colonyId, filtername: " "}, function (result) {
                vm.com_partial_construction_services.c_zip_code = result;
            });
        };

        vm.c_federal_entitys = C_federal_entity.query();

        //Foreign Exchange
        vm.show_foreign_exchange = false;
        vm.com_foreign_exchange = null;

        vm.c_type_operations = C_type_operation.query();

        //Local Taxes
        vm.show_local_taxes = false;
        vm.com_local_taxes = null;

        vm.com_ret_transfs = [];

        vm.addFreecomRetTransf = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-local-retentions/com-local-retentions-dialog.html',
                controller: 'Com_local_retentionsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            implocretentions: null,
                            retentionrate: (0).toFixed(2),
                            amountretentions: (0).toFixed(2),
                            id: null
                        };
                    },
                    transfered_entity: function () {
                        return {
                            imploctransfered: null,
                            transferedrate: (0).toFixed(2),
                            amounttransfered: (0).toFixed(2),
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_ret_transfs.push(result);
                vm.updateFreecomRetTransfTotals();
            });
        };

        vm.removeFreecomRetTransf = function(index){
            vm.com_ret_transfs.splice(index,1);
            vm.updateFreecomRetTransfTotals();
        };

        vm.updateFreecomRetTransfTotals = function(){
            var total_retentions = 0;
            var total_transfered = 0;

            var i;
            for(i = 0; i < vm.com_ret_transfs.length; i++){
                if(vm.com_ret_transfs[i].retentions != null) {
                    total_retentions = parseFloat(total_retentions) + parseFloat(vm.com_ret_transfs[i].retentions.amountretentions);
                }
                if(vm.com_ret_transfs[i].transfered != null) {
                    total_transfered = parseFloat(total_transfered) + parseFloat(vm.com_ret_transfs[i].transfered.amounttransfered);
                }
            }

            vm.com_local_taxes.total_local_retentions = floorFigure(total_retentions, 2);
            vm.com_local_taxes.total_local_transfered = floorFigure(total_transfered, 2);
        };

        //Used Vehicle
        vm.show_used_vehicle = false;
        vm.com_used_vehicle = null;
        vm.vehicle_customs_informations = [];

        vm.addVehicleCustomsInformation = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-vehicle-customs-information/com-vehicle-customs-information-dialog.html',
                controller: 'Com_vehicle_customs_informationDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            number: null,
                            date_expedition: null,
                            customs: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.vehicle_customs_informations.push(result);
            });
        };

        vm.removeVehicleCustomsInformation = function(index){
            vm.vehicle_customs_informations.splice(index,1);
        };

        //Destruction Certificate
        vm.show_destruction_certificate = false;
        vm.com_destruction_certificate = null;
        vm.com_info_customs_destruction = null;
        vm.use_info_customs_destruction = false;

        vm.c_type_series = C_type_series.query();

        vm.dateInfoCustomsDestructionPickerOpenStatus = {};
        vm.dateInfoCustomsDestructionPickerOpenStatus.date_expedition = false;

        vm.openInfoCustomsDestructionCalendar = function(date) {
            vm.dateInfoCustomsDestructionPickerOpenStatus[date] = true;
        };

        //Fuel Consumption
        vm.show_fuel_consumption = false;
        vm.com_fuel_consumption = null;
        vm.com_concept_fuels = [];

        vm.addFuelConcept = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-concept-fuel/com-concept-fuel-dialog.html',
                controller: 'Com_concept_fuelDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            identifier: null,
                            date_expedition: new Date(),
                            rfc: null,
                            key_station: null,
                            quantity: (0).toFixed(2),
                            fuel_name: null,
                            folio_operation: null,
                            unit_value: (0).toFixed(2),
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_concept_fuels.push(result);
                calcFuelTotals();
            });
        };

        vm.removeFuelConcept = function(index){
            vm.com_concept_fuels.splice(index,1);
            calcFuelTotals();
        };

        function calcFuelTotals(){
            var fuel_subtotal = 0;
            var fuel_total = 0;

            var i;
            for(i=0; i < vm.com_concept_fuels.length; i++){
                var concept_fuel = vm.com_concept_fuels[i].concept_fuel;

                fuel_subtotal = parseFloat(fuel_subtotal) + parseFloat(concept_fuel.amount);
                fuel_total =  parseFloat(fuel_total) + parseFloat(concept_fuel.amount);

                var fuel_determinates = vm.com_concept_fuels[i].determinates;
                var j;
                for(j=0; j < fuel_determinates.length; j++){
                    fuel_total =  parseFloat(fuel_total) + parseFloat(fuel_determinates[j].amount);
                }
            }

            vm.com_fuel_consumption.subtotal = floorFigure(fuel_subtotal, 2);
            vm.com_fuel_consumption.total =  floorFigure(fuel_total, 2);
        }

        //Storeroom Paybill
        vm.show_storeroom_paybill = false;
        vm.com_storeroom_paybill = null;
        vm.com_paybill_concepts = [];

        vm.addPaybillConcept = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-paybill-concept/com-paybill-concept-dialog.html',
                controller: 'Com_paybill_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            identification_number: null,
                            date_expedition: new Date(),
                            rfc: null,
                            curp: null,
                            name: null,
                            social_security_number: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_paybill_concepts.push(result);
            });
        };

        vm.removePaybillConcept = function(index){
            vm.com_paybill_concepts.splice(index,1);
        };

        //Ecc11
        vm.show_ecc11 = false;
        vm.show_ecc11_invalid = false;
        vm.com_ecc_11 = null;
        vm.com_ecc_11_concepts = [];

        vm.addEcc11Concept = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-ecc-11-concept/com-ecc-11-concept-dialog.html',
                controller: 'Com_ecc_11_conceptDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            identifier: null,
                            date: new Date(),
                            rfc: null,
                            key_station: null,
                            quantity: "0.001",
                            unit: null,
                            fuel_name: null,
                            folio_operation: null,
                            unit_value: null,
                            amount: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_ecc_11_concepts.push(result);
                calcEcc11Totals();
            });
        };

        vm.removeEcc11Concept = function(index){
            vm.com_ecc_11_concepts.splice(index,1);
            calcEcc11Totals();
        };

        function calcEcc11Totals(){
            var subtotal = 0;
            var total = 0;

            var i;
            for(i=0; i < vm.com_ecc_11_concepts.length; i++){
                var com_ecc_11_concept = vm.com_ecc_11_concepts[i].concept;

                subtotal = parseFloat(subtotal) + parseFloat(com_ecc_11_concept.amount);
                total =  parseFloat(total) + parseFloat(com_ecc_11_concept.amount);

                var com_ecc_11_tranfers = vm.com_ecc_11_concepts[i].transfers;
                var j;
                for(j=0; j < com_ecc_11_tranfers.length; j++){
                    total =  parseFloat(total) + parseFloat(com_ecc_11_tranfers[j].amount);
                }
            }

            vm.com_ecc_11.total =  floorFigure(total, 2);
            vm.com_ecc_11.subtotal = floorFigure(subtotal, 2);
        }

        //Spei Third
        vm.show_spei = false;
        vm.com_spei = null;
        vm.com_spei_thirds = [];

        vm.addSpeiThird = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-spei-third/com-spei-third-dialog.html',
                controller: 'Com_spei_thirdDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            date_operation: null,
                            hour: "00:00:00",
                            key_spei: null,
                            stamp: null,
                            number_certificate: null,
                            cda: null,
                            id: null
                        };
                    },
                    payer_entity: function () {
                        return {
                            emitter_bank: null,
                            name: null,
                            type_account: null,
                            account: null,
                            rfc: null,
                            id: null
                        };
                    },
                    beneficiary_entity: function () {
                        return {
                            receiver_bank: null,
                            name: null,
                            type_account: null,
                            account: null,
                            rfc: null,
                            concept: null,
                            iva: (1).toFixed(2),
                            payment_amount: (1).toFixed(2),
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_spei_thirds.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeSpeiThird = function(index){
            vm.com_spei_thirds.splice(index,1);
        };

        //Foreign Trade
        vm.show_foreign_trade = false;
        vm.com_foreign_trade = null;
        vm.com_addressee = null;
        vm.c_add_commodities = [];

        vm.c_type_operation_ces = C_type_operation_ce.query();
        vm.c_key_pediments = C_key_pediment.query();
        vm.com_incoterms = Com_incoterm.query();

        vm.f_add_c_countries = C_country.query({pg:1, filtername:" "});
        vm.f_add_c_states = null;
        vm.f_add_c_municipalities = null;
        vm.f_add_c_colonies = null;

        vm.onFreecomAddresseeChangeC_country = function() {
            var countryId = vm.com_addressee.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.f_add_c_states = result;
            });
        };

        vm.onFreecomAddresseeChangeC_state = function () {
            var stateId = vm.com_addressee.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.f_add_c_municipalities = result;
            });
        };

        vm.onFreecomAddresseeChangeC_municipality = function () {
            var municipalityId = vm.com_addressee.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.f_add_c_colonies = result;
            });
        };

        vm.onFreecomAddresseeChangeC_colony = function(){
            C_zip_code.get({id : vm.com_addressee.c_colony.c_zip_code.id, filtername:" "}, function(result) {
                vm.com_addressee.c_zip_code = result;
            });
        };

        vm.addComCommodity = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-commodity/com-commodity-dialog.html',
                controller: 'Com_commodityDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            noidentification: null,
                            customs_quantity: (0).toFixed(2),
                            customs_unit_value: (0).toFixed(2),
                            dollar_value: (0).toFixed(2),
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.c_add_commodities.push(result);
            });
        };

        vm.removeComCommodity = function(index){
            vm.c_add_commodities.splice(index,1);
        };

        //Public Notaries
        vm.show_public_notaries = false;
        vm.com_public_notaries = null;
        vm.com_desc_states = [];
        vm.com_data_operation = null;
        vm.com_notary_data = null;
        vm.public_notaries_federal_entitys = Public_notaries_federal_entity.query();
        vm.com_data_enajenante = null;
        vm.com_dataunenajenante = null;
        vm.use_dataenajenantecopsc = false;
        vm.com_dataenajenantecopscs = [];
        vm.com_acquiring_data = null;
        vm.com_dataunacquiring = null;
        vm.use_dataacquiringcopsc = false;
        vm.com_dataacquiringcopscs = [];

        vm.addComDescState = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-desc-state/com-desc-state-dialog.html',
                controller: 'Com_desc_stateDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            street: null,
                            noext: null,
                            noint: null,
                            locale: null,
                            reference: null,
                            c_country: {id: 151, name: "México", abrev: "MEX"},
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_desc_states.push(result)
            });
        };

        vm.removeComDescState= function(index){
            vm.com_desc_states.splice(index,1);
        };

        vm.comDataOperationDatePickerOpenStatus = {};
        vm.comDataOperationDatePickerOpenStatus.dateinstnotarial = false;
        vm.comDataOperationOpenCalendar = function(date) {
            vm.comDataOperationDatePickerOpenStatus[date] = true;
        };

        vm.addComDataEnajenantecoPsc = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-dataenajenantecopsc/com-dataenajenantecopsc-dialog.html',
                controller: 'Com_dataenajenantecopscDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            last_name: null,
                            mother_last_name: null,
                            rfc: null,
                            curp: null,
                            percentage: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_dataenajenantecopscs.push(result);
            });
        };

        vm.removeComDataEnajenantecoPsc = function(index){
            vm.com_dataenajenantecopscs.splice(index,1);
        };

        vm.addComDataAcquiringcoPsc = function(){
            $uibModal.open({
                templateUrl: 'app/entities/com-dataacquiringcopsc/com-dataacquiringcopsc-dialog.html',
                controller: 'Com_dataacquiringcopscDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            last_name: null,
                            mother_last_name: null,
                            rfc: null,
                            curp: null,
                            percentage: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.com_dataacquiringcopscs.push(result);
            });
        };

        vm.removeComDataAcquiringcoPsc = function(index){
            vm.com_dataacquiringcopscs.splice(index,1);
        };
    }
})();
