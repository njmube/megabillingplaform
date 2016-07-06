(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_conceptDialogController', Free_conceptDialogController);

    Free_conceptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_concept', 'Free_cfdi', 'Measure_unit'];

    function Free_conceptDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_concept, Free_cfdi, Measure_unit) {
        var vm = this;
        vm.free_concept = entity;
        vm.free_cfdis = Free_cfdi.query();
        vm.measure_units = Measure_unit.query();
        vm.load = function(id) {
            Free_concept.get({id : id}, function(result) {
                vm.free_concept = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_conceptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_concept.id !== null) {
                Free_concept.update(vm.free_concept, onSaveSuccess, onSaveError);
            } else {
                Free_concept.save(vm.free_concept, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
