(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_part_conceptDialogController', Free_part_conceptDialogController);

    Free_part_conceptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_part_concept', 'Free_concept', 'Measure_unit'];

    function Free_part_conceptDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_part_concept, Free_concept, Measure_unit) {
        var vm = this;
        vm.free_part_concept = entity;
        vm.free_concepts = Free_concept.query();
        vm.measure_units = Measure_unit.query();
        vm.load = function(id) {
            Free_part_concept.get({id : id}, function(result) {
                vm.free_part_concept = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_part_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_part_concept.id !== null) {
                Free_part_concept.update(vm.free_part_concept, onSaveSuccess, onSaveError);
            } else {
                Free_part_concept.save(vm.free_part_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
