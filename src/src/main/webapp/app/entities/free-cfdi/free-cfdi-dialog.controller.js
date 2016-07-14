(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiDialogController', Free_cfdiDialogController);

    Free_cfdiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_cfdi', 'Cfdi_types', 'Cfdi_states', 'Free_emitter', 'Payment_method', 'C_money', 'Cfdi_type_doc', 'Tax_regime', 'Free_receiver'];

    function Free_cfdiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Free_cfdi, Cfdi_types, Cfdi_states, Free_emitter, Payment_method, C_money, Cfdi_type_doc, Tax_regime, Free_receiver) {
        var vm = this;
        vm.free_cfdi = entity;
        vm.cfdi_types = Cfdi_types.query();
        vm.cfdi_states = Cfdi_states.query();
        vm.free_emitters = Free_emitter.query();
        vm.payment_methods = Payment_method.query();
        vm.c_monies = C_money.query();
        vm.cfdi_type_docs = Cfdi_type_doc.query();
        vm.tax_regimes = Tax_regime.query();
        vm.free_receivers = Free_receiver.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_cfdiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_cfdi.id !== null) {
                Free_cfdi.update(vm.free_cfdi, onSaveSuccess, onSaveError);
            } else {
                Free_cfdi.save(vm.free_cfdi, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_expedition = false;
        vm.datePickerOpenStatus.date_folio_fiscal_orig = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
