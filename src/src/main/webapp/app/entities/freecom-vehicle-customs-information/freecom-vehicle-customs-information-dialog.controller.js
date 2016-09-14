(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_vehicle_customs_informationDialogController', Freecom_vehicle_customs_informationDialogController);

    Freecom_vehicle_customs_informationDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Freecom_vehicle_customs_informationDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.freecom_vehicle_customs_information = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_vehicle_customs_information);
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
