(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_paybill_conceptDialogController', Com_paybill_conceptDialogController);

    Com_paybill_conceptDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_paybill_conceptDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.com_paybill_concept = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_paybill_concept);
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
