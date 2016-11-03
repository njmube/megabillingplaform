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
            $uibModalInstance.close(vm.freecom_paybill_concept);
            vm.isSaving = false;
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
