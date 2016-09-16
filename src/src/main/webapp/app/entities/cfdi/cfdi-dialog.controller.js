(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiDialogController', CfdiDialogController);

    CfdiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Cfdi', 'Cfdi_states', 'Payment_method', 'Cfdi_types', 'Cfdi_type_doc', 'C_money', 'Com_tfd', 'Taxpayer_account', 'Tax_regime', 'Taxpayer_client'];

    function CfdiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Cfdi, Cfdi_states, Payment_method, Cfdi_types, Cfdi_type_doc, C_money, Com_tfd, Taxpayer_account, Tax_regime, Taxpayer_client) {
        var vm = this;

        vm.cfdi = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cfdi_states = Cfdi_states.query();
        vm.payment_methods = Payment_method.query();
        vm.cfdi_types = Cfdi_types.query();
        vm.cfdi_type_docs = Cfdi_type_doc.query();
        vm.c_monies = C_money.query();
        vm.com_tfds = Com_tfd.query();
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.tax_regimes = Tax_regime.query();
        vm.taxpayer_clients = Taxpayer_client.query({filter: 'cfdi-is-null'});
        $q.all([vm.cfdi.$promise, vm.taxpayer_clients.$promise]).then(function() {
            if (!vm.cfdi.taxpayer_client || !vm.cfdi.taxpayer_client.id) {
                return $q.reject();
            }
            return Taxpayer_client.get({id : vm.cfdi.taxpayer_client.id}).$promise;
        }).then(function(taxpayer_client) {
            vm.taxpayer_clients.push(taxpayer_client);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
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
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_expedition = false;
        vm.datePickerOpenStatus.date_folio_fiscal_orig = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
