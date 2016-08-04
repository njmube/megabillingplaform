(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_vehicle_customs_informationDialogController', Freecom_vehicle_customs_informationDialogController);

    Freecom_vehicle_customs_informationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_vehicle_customs_information', 'Freecom_used_vehicle'];

    function Freecom_vehicle_customs_informationDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_vehicle_customs_information, Freecom_used_vehicle) {
        var vm = this;
        vm.freecom_vehicle_customs_information = entity;
        vm.freecom_used_vehicles = Freecom_used_vehicle.query();
        vm.load = function(id) {
            Freecom_vehicle_customs_information.get({id : id}, function(result) {
                vm.freecom_vehicle_customs_information = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_vehicle_customs_informationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_vehicle_customs_information.id !== null) {
                Freecom_vehicle_customs_information.update(vm.freecom_vehicle_customs_information, onSaveSuccess, onSaveError);
            } else {
                Freecom_vehicle_customs_information.save(vm.freecom_vehicle_customs_information, onSaveSuccess, onSaveError);
            }
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
