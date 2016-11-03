(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_vehicle_customs_informationDialogController', Com_vehicle_customs_informationDialogController);

    Com_vehicle_customs_informationDialogController.$inject = ['$uibModalInstance', 'entity'];

    function Com_vehicle_customs_informationDialogController ($uibModalInstance, entity) {
        var vm = this;
        vm.com_vehicle_customs_information = entity;

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_vehicle_customs_information);
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
