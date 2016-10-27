(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiNewController', CfdiNewController);

    CfdiNewController.$inject = ['$state', '$timeout', 'entity', 'taxpayer_account_entity', 'Cfdi', 'Payment_method', 'Cfdi_types', 'Cfdi_type_doc', 'C_money', 'Taxpayer_account', 'Taxpayer_transactions', 'AlertService', 'Taxpayer_series_folio', '$uibModal', 'Way_payment', 'Tax_types'];

    function CfdiNewController($state, $timeout, entity, taxpayer_account_entity, Cfdi, Payment_method, Cfdi_types, Cfdi_type_doc, C_money, Taxpayer_account, Taxpayer_transactions, AlertService, Taxpayer_series_folio, $uibModal, Way_payment, Tax_types) {
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
                        vm.cfdi.folio = vm.taxpayer_series_folio.folio_current;
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
            vm.cfdi.taxpayer_client = vm.taxpayer_client;

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
                cfdi: vm.cfdi,
                taxpayer_series_folio: vm.taxpayer_series_folio,
                conceptDTOs: vm.concepts,
                concepts: concepts,
                taxTransfereds: tax_transfereds,
                taxRetentions: tax_retentions
            };

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

            vm.isSaving = false;


        };

        vm.current_complement = null;
    }
})();
