(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_fuel_consumptionDialogController', Com_fuel_consumptionDialogController);

    Com_fuel_consumptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_fuel_consumption', 'Cfdi'];

    function Com_fuel_consumptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_fuel_consumption, Cfdi) {
        var vm = this;
        vm.com_fuel_consumption = entity;
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_fuel_consumptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_fuel_consumption.id !== null) {
                Com_fuel_consumption.update(vm.com_fuel_consumption, onSaveSuccess, onSaveError);
            } else {
                Com_fuel_consumption.save(vm.com_fuel_consumption, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
