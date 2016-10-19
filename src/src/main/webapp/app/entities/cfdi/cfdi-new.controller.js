(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiNewController', CfdiNewController);

    CfdiNewController.$inject = ['$scope', 'entity', 'taxpayer_account_entity', 'Cfdi', 'Cfdi_states', 'Payment_method', 'Cfdi_types', 'Cfdi_type_doc', 'C_money', 'Com_tfd', 'Taxpayer_account', 'Tax_regime', '$uibModal', 'Way_payment'];

    function CfdiNewController($scope, entity, taxpayer_account_entity, Cfdi, Cfdi_states, Payment_method, Cfdi_types, Cfdi_type_doc, C_money, Com_tfd, Taxpayer_account, Tax_regime, $uibModal, Way_payment) {
        var vm = this;

        vm.cfdi = entity;
        vm.cfdi.cfdi_states = {id: 1, name: "Creado  ", description: "CFDI creado en el sistema"};
        vm.cfdi.c_money = {id: 100, name: "MXN", description: "Peso Mexicano"};

        vm.taxpayer_account = taxpayer_account_entity;
        vm.taxpayer_accounts = Taxpayer_account.query({page: 0, size: 10});

        vm.onChangeTaxPayerAccount = onChangeTaxPayerAccount;

        function onChangeTaxPayerAccount(){
            if(vm.taxpayer_account == null) {
                vm.taxpayer_client = null;
            }
        }

        vm.tax_regimes = Tax_regime.query({filtername:" "});

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
        vm.show_iva = (0).toFixed(2);
        vm.calc_iva = (0).toFixed(2);
        vm.ieps = (0).toFixed(2);
        vm.ret_iva = (0).toFixed(2);
        vm.ret_isr = (0).toFixed(2);
        vm.subtotal_discount = (0).toFixed(2);

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

            vm.updateCFDITotals();
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

        function save () {
            vm.isSaving = true;
            if (vm.cfdi.id !== null) {
                Cfdi.update(vm.cfdi, onSaveSuccess, onSaveError);
            } else {
                Cfdi.save(vm.cfdi, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:cfdiUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.current_complement = null;
    }
})();
