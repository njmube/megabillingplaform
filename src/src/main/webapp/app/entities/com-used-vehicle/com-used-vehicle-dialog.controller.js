(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_used_vehicleDialogController', Com_used_vehicleDialogController);

    Com_used_vehicleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_used_vehicle', 'Cfdi'];

    function Com_used_vehicleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_used_vehicle, Cfdi) {
        var vm = this;
        vm.com_used_vehicle = entity;
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_used_vehicleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_used_vehicle.id !== null) {
                Com_used_vehicle.update(vm.com_used_vehicle, onSaveSuccess, onSaveError);
            } else {
                Com_used_vehicle.save(vm.com_used_vehicle, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
