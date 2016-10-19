(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Measure_unit_conceptDialogController', Measure_unit_conceptDialogController);

    Measure_unit_conceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Measure_unit_concept', 'Measure_unit', 'Taxpayer_concept'];

    function Measure_unit_conceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Measure_unit_concept, Measure_unit, Taxpayer_concept) {
        var vm = this;
        vm.measure_unit_concept = entity;
        vm.measure_units = Measure_unit.query();
        vm.taxpayer_concepts = Taxpayer_concept.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:measure_unit_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.measure_unit_concept.id !== null) {
                Measure_unit_concept.update(vm.measure_unit_concept, onSaveSuccess, onSaveError);
            } else {
                Measure_unit_concept.save(vm.measure_unit_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
