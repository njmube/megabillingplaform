(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_determinedDialogController', Freecom_determinedDialogController);

    Freecom_determinedDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_determined', 'Freecom_concept_fuel', 'Freecom_tax_type'];

    function Freecom_determinedDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_determined, Freecom_concept_fuel, Freecom_tax_type) {
        var vm = this;
        vm.freecom_determined = entity;
        vm.freecom_concept_fuels = Freecom_concept_fuel.query();
        vm.freecom_tax_types = Freecom_tax_type.query();
        vm.load = function(id) {
            Freecom_determined.get({id : id}, function(result) {
                vm.freecom_determined = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_determinedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_determined.id !== null) {
                Freecom_determined.update(vm.freecom_determined, onSaveSuccess, onSaveError);
            } else {
                Freecom_determined.save(vm.freecom_determined, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
