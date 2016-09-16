(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Part_conceptDialogController', Part_conceptDialogController);

    Part_conceptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Part_concept', 'Concept', 'Measure_unit'];

    function Part_conceptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Part_concept, Concept, Measure_unit) {
        var vm = this;

        vm.part_concept = entity;
        vm.clear = clear;
        vm.save = save;
        vm.concepts = Concept.query();
        vm.measure_units = Measure_unit.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.part_concept.id !== null) {
                Part_concept.update(vm.part_concept, onSaveSuccess, onSaveError);
            } else {
                Part_concept.save(vm.part_concept, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:part_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
