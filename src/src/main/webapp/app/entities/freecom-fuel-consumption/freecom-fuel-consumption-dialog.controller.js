(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_fuel_consumptionDialogController', Freecom_fuel_consumptionDialogController);

    Freecom_fuel_consumptionDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_fuel_consumption', 'Free_cfdi'];

    function Freecom_fuel_consumptionDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_fuel_consumption, Free_cfdi) {
        var vm = this;
        vm.freecom_fuel_consumption = entity;
        vm.free_cfdis = Free_cfdi.query();
        vm.load = function(id) {
            Freecom_fuel_consumption.get({id : id}, function(result) {
                vm.freecom_fuel_consumption = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_fuel_consumptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_fuel_consumption.id !== null) {
                Freecom_fuel_consumption.update(vm.freecom_fuel_consumption, onSaveSuccess, onSaveError);
            } else {
                Freecom_fuel_consumption.save(vm.freecom_fuel_consumption, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
