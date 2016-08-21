(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_spei_thirdDialogController', Freecom_spei_thirdDialogController);

    Freecom_spei_thirdDialogController.$inject = ['$uibModalInstance', 'entity', 'payer_entity','beneficiary_entity'];

    function Freecom_spei_thirdDialogController ($uibModalInstance, entity, payer_entity, beneficiary_entity) {
        var vm = this;

        vm.freecom_spei_third = entity;
        vm.freecom_payer = payer_entity;
        vm.freecom_beneficiary = beneficiary_entity;

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                spei_third: vm.freecom_spei_third,
                payer: vm.freecom_payer,
                beneficiary: vm.freecom_beneficiary
            });
        }

        vm.datePickerOpenStatus.date_operation = false;
        vm.datePickerOpenStatus.hour = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
