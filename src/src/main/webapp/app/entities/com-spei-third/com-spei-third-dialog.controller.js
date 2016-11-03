(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_spei_thirdDialogController', Com_spei_thirdDialogController);

    Com_spei_thirdDialogController.$inject = ['$uibModalInstance', 'entity', 'payer_entity','beneficiary_entity'];

    function Com_spei_thirdDialogController ($uibModalInstance, entity, payer_entity, beneficiary_entity) {
        var vm = this;

        vm.com_spei_third = entity;
        vm.com_payer = payer_entity;
        vm.com_beneficiary = beneficiary_entity;

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
                spei_third: vm.com_spei_third,
                payer: vm.com_payer,
                beneficiary: vm.com_beneficiary
            });
        }

        vm.datePickerOpenStatus.date_operation = false;
        vm.datePickerOpenStatus.hour = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
