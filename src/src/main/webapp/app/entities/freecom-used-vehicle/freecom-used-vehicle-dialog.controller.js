(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_used_vehicleDialogController', Freecom_used_vehicleDialogController);

    Freecom_used_vehicleDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_used_vehicle', 'Free_cfdi'];

    function Freecom_used_vehicleDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_used_vehicle, Free_cfdi) {
        var vm = this;
        vm.freecom_used_vehicle = entity;
        vm.free_cfdis = Free_cfdi.query();
        vm.load = function(id) {
            Freecom_used_vehicle.get({id : id}, function(result) {
                vm.freecom_used_vehicle = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_used_vehicleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_used_vehicle.id !== null) {
                Freecom_used_vehicle.update(vm.freecom_used_vehicle, onSaveSuccess, onSaveError);
            } else {
                Freecom_used_vehicle.save(vm.freecom_used_vehicle, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
