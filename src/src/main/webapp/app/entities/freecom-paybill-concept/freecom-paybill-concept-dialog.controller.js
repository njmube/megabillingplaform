(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_paybill_conceptDialogController', Freecom_paybill_conceptDialogController);

    Freecom_paybill_conceptDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_paybill_conceptDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.freecom_paybill_concept = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close({
                identification_number: vm.freecom_paybill_concept.identification_number,
                date_expedition: vm.freecom_paybill_concept.date_expedition,
                rfc: vm.freecom_paybill_concept.rfc,
                curp: vm.freecom_paybill_concept.curp,
                name: vm.freecom_paybill_concept.name,
                social_security_number: vm.freecom_paybill_concept.social_security_number,
                amount: vm.freecom_paybill_concept.amount,
                id: null
            });
            vm.isSaving = true;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_expedition = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
