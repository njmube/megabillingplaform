(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Customs_infoDialogController', Customs_infoDialogController);

    Customs_infoDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Customs_infoDialogController ($uibModalInstance, entity) {
        var vm = this;

        vm.customs_info = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.customs_info);
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
