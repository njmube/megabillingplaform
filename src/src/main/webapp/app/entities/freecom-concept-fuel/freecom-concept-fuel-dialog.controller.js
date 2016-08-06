(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_concept_fuelDialogController', Freecom_concept_fuelDialogController);

    Freecom_concept_fuelDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_concept_fuel', 'Freecom_fuel_consumption'];

    function Freecom_concept_fuelDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_concept_fuel, Freecom_fuel_consumption) {
        var vm = this;
        vm.freecom_concept_fuel = entity;
        vm.freecom_fuel_consumptions = Freecom_fuel_consumption.query();
        vm.load = function(id) {
            Freecom_concept_fuel.get({id : id}, function(result) {
                vm.freecom_concept_fuel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_concept_fuelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_concept_fuel.id !== null) {
                Freecom_concept_fuel.update(vm.freecom_concept_fuel, onSaveSuccess, onSaveError);
            } else {
                Freecom_concept_fuel.save(vm.freecom_concept_fuel, onSaveSuccess, onSaveError);
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
