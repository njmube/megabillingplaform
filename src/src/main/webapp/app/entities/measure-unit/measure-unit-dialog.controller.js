(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Measure_unitDialogController', Measure_unitDialogController);

    Measure_unitDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Measure_unit'];

    function Measure_unitDialogController ($scope, $stateParams, $uibModalInstance, entity, Measure_unit) {
        var vm = this;
        vm.measure_unit = entity;
        vm.load = function(id) {
            Measure_unit.get({id : id}, function(result) {
                vm.measure_unit = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:measure_unitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.measure_unit.id !== null) {
                Measure_unit.update(vm.measure_unit, onSaveSuccess, onSaveError);
            } else {
                Measure_unit.save(vm.measure_unit, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
